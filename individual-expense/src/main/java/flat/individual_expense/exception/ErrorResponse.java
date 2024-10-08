package flat.individual_expense.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;
}

