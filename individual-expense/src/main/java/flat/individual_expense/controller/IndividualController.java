package flat.individual_expense.controller;


import flat.individual_expense.entity.IndividualTenantExpense;
import flat.individual_expense.entity.MiscellaneousDTO;
import flat.individual_expense.service.IndividualExpensiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("individual/expense")
public class IndividualController {


    @Autowired
    IndividualExpensiveService service;

    @GetMapping("/{id}/{date}")
    public IndividualTenantExpense getMonthlyExpense(@PathVariable Integer id,@PathVariable LocalDate date){
       return this.service.getMonthlyExpense(id,date);

    }
    @GetMapping("/misc/{id}")
    public List<MiscellaneousDTO> getMisc(@PathVariable Integer id){
       return this.service.getMiscData(id);
    }

}
