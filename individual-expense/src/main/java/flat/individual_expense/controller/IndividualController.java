package flat.individual_expense.controller;


import flat.individual_expense.entity.IndividualTenantExpense;
import flat.individual_expense.service.IndividualExpensiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("individual/expense")
public class IndividualController {


    @Autowired
    IndividualExpensiveService service;

    @GetMapping("/{date}")
    public IndividualTenantExpense getMonthlyExpense(@PathVariable LocalDate date){
       return this.service.getMonthlyExpense(date);

    }
    @PostMapping("/add-misc/{misc}")
    public String addMisc(@PathVariable Long misc)
    {
        this.service.addMiscIndividualExpense(misc);

        return "Misc Expense added successfully";
    }

}
