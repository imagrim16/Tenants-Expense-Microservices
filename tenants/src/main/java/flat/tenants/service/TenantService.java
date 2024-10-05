package flat.tenants.service;

import flat.tenants.entity.Tenant;
import flat.tenants.exceptions.GlobalExceptionHandler;
import flat.tenants.exceptions.TenantNotFoundException;
import flat.tenants.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TenantService {

    @Autowired
    private TenantRepository repository;

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public Tenant addNewTenant(Tenant tenant) {
        log.info("Adding the Tenant");
        return this.repository.save(tenant);
    }

    public Tenant getTenantById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + id));
    }

    public Tenant deleteTenantById(Integer id) {
         Tenant deleted=this.getTenantById(id);
         this.repository.deleteById(id);
         log.info("Deleting Tenant By ID");
         return deleted;
    }
}
