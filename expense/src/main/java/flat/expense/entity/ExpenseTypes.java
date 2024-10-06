package flat.expense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
public class ExpenseTypes {
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfExpense;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long rent;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long groceries;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long cook;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long washingMachine;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long totalExpense;

    //setting the default values
    public ExpenseTypes() {
        this.rent = 0L;
        this.groceries = 0L;
        this.cook = 0L;
        this.washingMachine = 0L;
        this.totalExpense = 0L;  // Optional, will be recalculated
    }

    public void setTotalExpense(){
        this.totalExpense=cook+rent+groceries+washingMachine;
    }
    @PrePersist
    @PreUpdate
    private void updateTotalExpenseBeforeSave() {
        setTotalExpense();
    }
}
