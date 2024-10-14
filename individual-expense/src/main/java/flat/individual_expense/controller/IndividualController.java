package flat.individual_expense.controller;


import flat.individual_expense.entity.IndividualTenantExpense;
import flat.individual_expense.entity.MiscellaneousDTO;
import flat.individual_expense.service.IndividualExpensiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("individual/expense")
public class IndividualController {


    @Autowired
    IndividualExpensiveService service;

    @GetMapping("/{id}/{date}")
    public Mono<IndividualTenantExpense> getMonthlyExpense(@PathVariable Integer id, @PathVariable LocalDate date){
       return this.service.getMonthlyExpense(id,date);

    }
    @GetMapping("/misc/{id}/{month}")
    public Mono<List<MiscellaneousDTO>> getMisc(@PathVariable Integer id,@PathVariable(name = "month")Integer month){
       return this.service.getMiscData(id,month);
    }

}
