package flat.tenants.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class Miscellaneous {
    private String description;
    private Long value;
    private LocalDate expenseDate;
}
