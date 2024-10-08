package flat.tenants.service;

import flat.tenants.entity.Tenant;
import flat.tenants.exceptions.GlobalExceptionHandler;
import flat.tenants.exceptions.TenantNotFoundException;
import flat.tenants.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TenantService {

    @Autowired
    private TenantRepository repository;

    private static final Logger log = LoggerFactory.getLogger(TenantService.class);

    public Tenant addNewTenant(Tenant tenant) {
        log.info("Adding the Tenant : "+tenant);
        return this.repository.save(tenant);
    }

    public Tenant getTenantById(Integer id) {
        Optional<Tenant> tenant=repository.findById(id);
        log.info("Get tenant by Id"+tenant);
        return repository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + id));
    }

    public Tenant deleteTenantById(Integer id) {
         Tenant deleted=this.getTenantById(id);
         this.repository.deleteById(id);
         log.info("Deleted Tenant is :"+deleted);
         return deleted;
    }

    public String getTotaltenants() {
        log.info(String.valueOf("Total number of Tenats are "+this.repository.count()));
        return String.valueOf(this.repository.count());
    }
}
