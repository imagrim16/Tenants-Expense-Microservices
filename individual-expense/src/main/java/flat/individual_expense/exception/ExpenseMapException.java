package flat.individual_expense.exception;

public class ExpenseMapException extends Exception{
    public ExpenseMapException(String message) {
        super(message);
    }

    public ExpenseMapException(String message, Throwable cause) {
        super(message, cause);
    }
}
