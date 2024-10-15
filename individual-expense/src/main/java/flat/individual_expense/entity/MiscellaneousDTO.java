package flat.individual_expense.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MiscellaneousDTO {
    private String description;
    private Long value;
    private LocalDate expenseDate;
}
