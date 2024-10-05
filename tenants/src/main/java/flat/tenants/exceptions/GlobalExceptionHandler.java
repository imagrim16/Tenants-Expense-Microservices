package flat.tenants.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle TenantNotFoundException
    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<String> handleTenantNotFoundException(TenantNotFoundException ex) {
        // Log the exception message without the stack trace
        log.warn(ex.getMessage());

        // Return the custom error message with NOT_FOUND status
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle other general exceptions if necessary
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        // Log the full stack trace for debugging other exceptions
        log.error("An error occurred: ", ex);

        // Return a generic error message with INTERNAL_SERVER_ERROR status
        return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

