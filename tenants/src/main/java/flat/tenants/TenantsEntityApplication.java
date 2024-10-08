package flat.tenants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TenantsEntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantsEntityApplication.class, args);
	}

}
