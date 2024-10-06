package flat.expense.repository;

import flat.expense.entity.ExpenseTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseTypes, LocalDate> {
}
