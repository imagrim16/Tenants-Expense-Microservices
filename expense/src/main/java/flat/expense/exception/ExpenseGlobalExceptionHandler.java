package flat.expense.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpenseGlobalExceptionHandler {

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<String> handleExpenseNotFound(ExpenseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(ExpenseMandatoryDateException.class)
    public ResponseEntity<String> handleMandatoryDateNotFound(ExpenseMandatoryDateException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    // You can add more exception handlers for different exceptions here

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}
