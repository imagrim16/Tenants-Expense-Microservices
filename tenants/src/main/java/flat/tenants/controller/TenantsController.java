package flat.tenants.controller;

import flat.tenants.entity.Tenant;
import flat.tenants.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tenant/")
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
        return this.service.getTotaltenants();
    }
}
