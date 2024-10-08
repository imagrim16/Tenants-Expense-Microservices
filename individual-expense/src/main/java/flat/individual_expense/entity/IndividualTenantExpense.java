package flat.individual_expense.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
public class IndividualTenantExpense {

    private Long cook = 0L;
    private Long washingMachine = 0L;
    private Long rent = 0L;
    private Long groceries = 0L;
    private Long broadband = 0L;
    private Long electricity = 0L;
    private Long misc = 0L;
    private Long total = 0L;

    public void calculateTotal() {
        this.total = cook + washingMachine + rent + groceries + broadband + electricity + misc;
    }

}
