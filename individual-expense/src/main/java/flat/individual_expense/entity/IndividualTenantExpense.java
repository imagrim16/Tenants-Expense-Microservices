package flat.individual_expense.entity;

import lombok.Data;

import java.util.List;

@Data
public class IndividualTenantExpense {

    private Long cook = 0L;
    private Long washingMachine = 0L;
    private Long rent = 0L;
    private Long groceries = 0L;
    private Long broadband = 0L;
    private Long electricity = 0L;
    private List<MiscellaneousDTO> misc; // List of MiscellaneousDTO
    private Long total = 0L;

    public void calculateTotal() {
        // Calculate total based on all fields, including the misc list
        Long miscTotal = misc != null ? misc.stream()
                .map(MiscellaneousDTO::getValue) // Assuming getValue returns Long
                .reduce(0L, Long::sum) : 0L;

        this.total = cook + washingMachine + rent + groceries + broadband + electricity + miscTotal;
    }
}
