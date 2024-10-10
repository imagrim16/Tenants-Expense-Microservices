package flat.expense.controller;


import flat.expense.entity.ExpenseTypes;
import flat.expense.exception.ExpenseMandatoryDateException;
import flat.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    // add expense
    @PostMapping("/add")
    public String addExpense(@RequestBody ExpenseTypes expenseTypes) throws ExpenseMandatoryDateException {
        this.service.addExpenseTypes(expenseTypes);
        return "Expense Added Successfully";
    }

    //Get Expenses By date
    @GetMapping("/getByDate/{date}")
    public ExpenseTypes getExpense(@PathVariable LocalDate date)
    {
       return this.service.getExpenseByDate(date);
    }

    //Get expense By month
    @GetMapping("/getByMonth/{month}")
    public List<ExpenseTypes> getExpenseByMonth(@PathVariable Integer month)
    {
        return this.service.getExpenseByMonth(month);
    }

    //Modifying the expense
    @PatchMapping("/update/{date}")
    public ExpenseTypes modifyExpenses(@RequestBody ExpenseTypes updatedExpenses, @PathVariable LocalDate date){
        return this.service.updateExpense(updatedExpenses,date);
    }

    //Get Total Expenses of the Month
    @GetMapping("/total/{month}")
    public Long getTotal(@PathVariable Integer month) {
       return this.service.getTotal(month);
    }

    // Delete Expense By date
    @DeleteMapping("/delete/{date}")
    public String deleteExpenseByDate(@PathVariable LocalDate date){
        this.service.deleteExpense(date);
        return "Entry deleted successfully for date : "+date;
    }


}
