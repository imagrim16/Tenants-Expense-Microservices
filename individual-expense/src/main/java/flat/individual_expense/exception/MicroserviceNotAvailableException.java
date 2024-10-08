package flat.individual_expense.exception;

public class MicroserviceNotAvailableException extends RuntimeException {
    public MicroserviceNotAvailableException(String message) {
        super(message);
    }
}

