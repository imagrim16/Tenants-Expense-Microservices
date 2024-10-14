package flat.tenants.service;

import flat.tenants.entity.Miscellaneous;
import flat.tenants.entity.Tenant;
import flat.tenants.exceptions.TenantNotFoundException;
import flat.tenants.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TenantService {

    private static final Logger log = LoggerFactory.getLogger(TenantService.class);
    @Autowired
    private TenantRepository repository;

    public Tenant addNewTenant(Tenant tenant) {
        log.info("Adding the Tenant : " + tenant);
        return this.repository.save(tenant);
    }

    public Tenant getTenantById(Integer id) {
        Optional<Tenant> tenant = repository.findById(id);
        log.info("Get tenant by Id" + tenant);
        return repository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + id));
    }

    public Tenant deleteTenantById(Integer id) {
        Tenant deleted = this.getTenantById(id);
        this.repository.deleteById(id);
        log.info("Deleted Tenant is :" + deleted);
        return deleted;
    }

    public String getTotalTenants() {
        log.info("Total number of Tenants are " + this.repository.count());
        return String.valueOf(this.repository.count());
    }

    public boolean addMiscData(Integer id, Miscellaneous misc) {
        Tenant tenant = this.getTenantById(id);
        tenant.getMisc().add(misc);
        this.repository.save(tenant);
        log.info("Misc data had been merged for {} \n {}", tenant.getName(), misc);
        return true;
    }

    public boolean deleteMiscData(Integer id, Miscellaneous misc) {
        Tenant tenant = this.getTenantById(id);
        Optional<Miscellaneous> matchedMiscOpt = tenant.getMisc().stream().
                filter(t -> t.getDescription().equals(misc.getDescription()))
                .findFirst();
        if (matchedMiscOpt.isPresent()) {
            Miscellaneous matchedMisc = matchedMiscOpt.get();
            if (tenant.getMisc().remove(matchedMisc)) {
                this.repository.save(tenant);
                log.info("Misc data removed successfully for {} \n {}", tenant.getName(), misc);
                return true;
            }
        }

        log.info("No Misc data found with the given input !!!");
        return false;
    }

    public List<Miscellaneous> getMiscDataList(Integer id) {
        log.info("Fetching the Misc Data from DB ...");
        return this.getTenantById(id).getMisc();
    }
}
