package flat.individual_expense.service;

import flat.individual_expense.entity.IndividualTenantExpense;
import flat.individual_expense.entity.MiscellaneousDTO;
import flat.individual_expense.exception.ExpenseMapException;
import flat.individual_expense.exception.MicroserviceNotAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndividualExpensiveService {

    @Value("${expense.service.url.base}")
    private String EXPENSE_SERVICE_BASE_URL;

    @Value("${expense.service.url.getByDate}")
    private String EXPENSE_GET_BY_DATE_URL;

    @Value("${tenant.service.url.base}")
    private String TENANT_SERVICE_BASE_URL;

    @Value("${tenant.service.url.count}")
    private String TENANT_GET_COUNT;

    @Value("${tenant.service.url.get.by.date}")
    private String TENANT_GET_MISC_BY_DATE;

    private final WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(IndividualExpensiveService.class);

    public IndividualExpensiveService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build(); // Set the base URL for expense service
    }

    public Mono<IndividualTenantExpense> getMonthlyExpense(Integer id, LocalDate date) {
        String tenantServiceUrl = EXPENSE_SERVICE_BASE_URL + EXPENSE_GET_BY_DATE_URL + date; // Construct the URL based on the date
        IndividualTenantExpense individualTenantExpense = new IndividualTenantExpense();

        return getNumberOfTenant()
                .flatMap(numberOfTenants -> {
                    if (numberOfTenants == 0L) {
                        return Mono.error(new ArithmeticException("No Tenants were Found"));
                    }

                    return webClient.get()
                            .uri(tenantServiceUrl) // Append tenant ID to URL
                            .retrieve()
                            .onStatus(
                                    status -> !status.is2xxSuccessful(),
                                    clientResponse -> clientResponse.bodyToMono(String.class)
                                            .flatMap(errorBody -> {
                                                String errorMessage = String.format("Error fetching tenant data: %s", errorBody);
                                                log.error(errorMessage); // Log the error
                                                return Mono.error(new ExpenseMapException(errorMessage));
                                            })
                            )
                            .bodyToMono(Map.class)
                            .flatMap(tenantMap -> {
                                // Merge tenant data into the individualTenantExpense entity
                                individualTenantExpense.setCook(convertToLong(tenantMap.get("cook"), numberOfTenants));
                                individualTenantExpense.setWashingMachine(convertToLong(tenantMap.get("washingMachine"), numberOfTenants));
                                individualTenantExpense.setRent(convertToLong(tenantMap.get("rent"), numberOfTenants));
                                individualTenantExpense.setGroceries(convertToLong(tenantMap.get("groceries"), numberOfTenants));
                                individualTenantExpense.setBroadband(convertToLong(tenantMap.get("broadband"), numberOfTenants));
                                individualTenantExpense.setElectricity(convertToLong(tenantMap.get("electricity"), numberOfTenants));

                                // Get miscellaneous data reactively
                                return getMiscData(id, LocalDate.now().getMonthValue())
                                        .flatMap(miscDataList -> {
                                            // Set miscellaneous data
                                            individualTenantExpense.setMisc(miscDataList);

                                            // Calculate total expense after merging
                                            individualTenantExpense.calculateTotal(); // Now calls calculateTotal without parameters
                                            log.info("Tenant data merged successfully: {}", tenantMap);
                                            return Mono.just(individualTenantExpense); // Return the populated object
                                        });
                            })
                            .onErrorMap(error -> new ExpenseMapException("Failed to fetch tenant EXPENSE", error))
                            .onErrorMap(
                                    WebClientRequestException.class, // Catch network-related errors
                                    ex -> new MicroserviceNotAvailableException("Microservice Expense is not available !!!")
                            );
                });
    }


    private Long convertToLong(Object value, Long numberOfTenants) {
        return value instanceof Number ? ((Number) value).longValue() / numberOfTenants : 0L; // Default to 0L if not a number
    }

    private Mono<Long> getNumberOfTenant() {
        String tenantCountUrl = TENANT_SERVICE_BASE_URL + TENANT_GET_COUNT;

        return webClient.get()
                .uri(tenantCountUrl)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(
                        WebClientRequestException.class, // Catch network-related errors
                        ex -> new MicroserviceNotAvailableException("Microservice Tenant is not available!!!")
                )
                .doOnNext(response -> log.info("Raw tenant count response: {}", response))
                .map(countString -> {
                    try {
                        return Long.parseLong(countString.trim());
                    } catch (NumberFormatException e) {
                        log.error("Failed to parse tenant count: {}", countString, e);
                        return 0L; // Default to 0L if parsing fails
                    }
                })
                .defaultIfEmpty(0L); // Return 0L if nothing is found
    }





    public void addMiscIndividualExpense(Long misc) {
        // Implement logic to add miscellaneous expense
    }

    public Mono<List<MiscellaneousDTO>> getMiscData(Integer id, Integer month) {
        String miscServiceUrl = TENANT_SERVICE_BASE_URL + TENANT_GET_MISC_BY_DATE + id; // Construct the URL

        return webClient.get()
                .uri(miscServiceUrl)
                .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    String errorMessage = String.format("Error fetching miscellaneous data for ID %d: %s", id, errorBody);
                                    return Mono.error(new RuntimeException(errorMessage)); // Log the error or use a custom exception
                                })
                )
                .bodyToMono(new ParameterizedTypeReference<List<MiscellaneousDTO>>() {}) // Convert the response body to a List of MiscellaneousDTO
                .onErrorMap(
                        WebClientRequestException.class, // Catch network-related errors
                        ex -> new RuntimeException("Microservice for Miscellaneous data is not available!", ex) // Handle the exception
                )
                .map(miscData -> filterByMonth(miscData, month)); // Filter the data by month
    }

    private List<MiscellaneousDTO> filterByMonth(List<MiscellaneousDTO> miscData, Integer month) {
        return miscData.stream()
                .filter(item -> {
                    // Assuming MiscellaneousDTO has a LocalDate field named 'dateField' to compare with
                    LocalDate dateField = item.getExpenseDate(); // Replace with actual method to get the date
                    return dateField != null && dateField.getMonthValue() == month;
                })
                .collect(Collectors.toList());
    }
}

