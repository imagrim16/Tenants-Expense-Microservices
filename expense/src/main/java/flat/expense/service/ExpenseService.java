package flat.expense.service;

import flat.expense.entity.ExpenseTypes;
import flat.expense.exception.ExpenseMandatoryDateException;
import flat.expense.exception.ExpenseNotFoundException;
import flat.expense.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    public void addExpenseTypes(ExpenseTypes expenseTypes) throws ExpenseMandatoryDateException {
        if (expenseTypes.getDateOfExpense() == null) {
            logger.info("Exception !!! date is null ");
            throw new ExpenseMandatoryDateException("Date is mandatory for adding an expense!");
        }

        this.repository.save(expenseTypes); // Save the expense types
    }

    public ExpenseTypes getExpenseByDate(LocalDate date) {
        logger.info("Hiiting the Get Expense Service URL");
        logger.info(this.repository.findById(date).toString());
        return this.repository.findById(date)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found for date: " + date));
    }

    public List<ExpenseTypes> getExpenseByMonth(Integer month) {
        List<ExpenseTypes> result= this.repository.findAll().stream().filter(expenseTypes ->
                Integer.valueOf(expenseTypes.getDateOfExpense().toString().substring(5,7))
                        .equals(month)).collect(Collectors.toList());
        ;
        logger.info("Get all Expenses in a Month :");
        for(ExpenseTypes types:result) logger.info(String.valueOf(types));
        return result;
    }

    public ExpenseTypes updateExpense(ExpenseTypes updatedExpense, LocalDate date) {
        ExpenseTypes existingExpense = repository.findById(date)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found for date: " + date));

        // Update the fields only if they are provided, otherwise default to 0
        existingExpense.setRent(defaultIfNull(updatedExpense.getRent(), existingExpense.getRent()));
        existingExpense.setGroceries(defaultIfNull(updatedExpense.getGroceries(), existingExpense.getGroceries()));
        existingExpense.setCook(defaultIfNull(updatedExpense.getCook(), existingExpense.getCook()));
        existingExpense.setWashingMachine(defaultIfNull(updatedExpense.getWashingMachine(), existingExpense.getWashingMachine()));

        logger.info("Updating the Expense for date : "+date);
        return repository.save(existingExpense);
    }

    // Helper method to handle null values
    private Long defaultIfNull(Long newValue, Long existingValue) {
        return (newValue != 0) ? newValue : existingValue;
    }


   public Long getTotal(Integer month) {
       // Stream through the list of expenses for the given month and sum up the total expenses using sum()

        Long total =this.getExpenseByMonth(month).stream()
               .mapToLong(expense -> expense.getTotalExpense()) // Convert to LongStream
               .sum(); // Use sum() directly to get the total
       logger.info("Total for month {} is {}", month, total);
       return total;
   }

    public void deleteExpense(LocalDate date) {
        this.repository.delete(getExpenseByDate(date));

        logger.info("Expense Deleted for date : {}",date);
    }

    // with reduce function of stream
  /*  public Long getTotal(Integer month) {
        // Using lambda instead of method reference
        // Using lambda for summing
        return this.getExpenseByMonth(month).stream()
                .map(ExpenseTypes::getTotalExpense) // Using lambda instead of method reference
                .reduce(0L, Long::sum);
    }*/

}
