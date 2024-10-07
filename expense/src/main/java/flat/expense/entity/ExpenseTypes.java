package flat.expense.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
public class ExpenseTypes {

    @Id
    private LocalDate dateOfExpense;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long rent = 0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long groceries = 0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long cook = 0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long washingMachine = 0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long electricity = 0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long broadband = 0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long totalExpense = 0L;

    // Method to calculate total expenses
    public void calculateTotalExpense() {
        this.totalExpense = rent + groceries + cook + washingMachine + electricity + broadband;
    }

    @PrePersist
    @PreUpdate
    private void updateTotalExpenseBeforeSave() {
        calculateTotalExpense();
    }
}
