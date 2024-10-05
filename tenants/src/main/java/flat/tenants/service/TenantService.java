package flat.tenants.service;

import flat.tenants.entity.Tenant;
import flat.tenants.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    @Autowired
    private TenantRepository repository;

    public Tenant addNewTenant(Tenant tenant) {
         return this.repository.save(tenant);
    }

    public Tenant getTenantById(Integer id) {
        return this.repository.getReferenceById(id);
    }

    public Tenant deleteTenantById(Integer id) {
         Tenant deleted=this.getTenantById(id);
         this.repository.deleteById(id);
         return deleted;
    }
}
