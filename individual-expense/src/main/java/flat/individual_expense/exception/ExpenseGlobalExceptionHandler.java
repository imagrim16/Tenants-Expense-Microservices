package flat.individual_expense.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpenseGlobalExceptionHandler {

    @ExceptionHandler(ExpenseMapException.class)
    public ResponseEntity<String> handleExpenseMapException(ExpenseMapException ex) {
        // Log the exception message if needed
        // log.error("ExpenseMapException occurred: {}", ex.getMessage());

        // Create a custom response
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> noTenantFoundException(ArithmeticException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Tenants were found!!!");
    }

    @ExceptionHandler(MicroserviceNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleMicroserviceANotAvailable(MicroserviceNotAvailableException ex) {
        ErrorResponse error = new ErrorResponse("MICROSERVICE_NOT_AVAILABLE", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE); // Return 503 status
    }

    // You can add more exception handlers for other exceptions as needed
}
