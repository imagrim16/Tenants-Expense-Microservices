package flat.tenants.controller;

import flat.tenants.entity.Miscellaneous;
import flat.tenants.entity.Tenant;
import flat.tenants.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tenant")
public class TenantsController {

    @Autowired
    private TenantService service;

    //add Tenant
    @PostMapping("/")
    public Tenant addTenant(@RequestBody Tenant tenant) {
        return this.service.addNewTenant(tenant);
    }

    //Get Tenant
    @GetMapping("/{id}")
    public Tenant getTenantById(@PathVariable Integer id){
        return this.service.getTenantById(id);
    }

    //Delete Tenant
    @DeleteMapping("/{id}")
    public Tenant deleteById(@PathVariable Integer id)
    {
        return this.service.deleteTenantById(id);
    }

    //get Count
    @GetMapping("/count")
    public String getCount()
    {
        return this.service.getTotalTenants();
    }

    //Set Misc for an Entity
    @PostMapping("/misc/{id}")
    public String addMisc(@PathVariable Integer id, @RequestBody Miscellaneous misc){
        return this.service.addMiscData(id,misc)? "Misc data added successfully"
                :"There is some error, please check the logs !!! ";
    }

    //deleting the misc data
    @DeleteMapping("/misc/{id}")
    public String deleteMisc(@PathVariable Integer id, @RequestBody Miscellaneous misc){
        return this.service.deleteMiscData(id,misc)? "Misc data deleted successfully"
                :"No Misc data found with the given input !!! ";
    }

    //Getting misc data
    @GetMapping("/misc/{id}")
    public List<Miscellaneous> getMisc(@PathVariable Integer id){
        log.info("Fetching the Misc data from db...");
        return this.service.getMiscDataList(id);
    }

}
