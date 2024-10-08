package flat.individual_expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class IndividualExpenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndividualExpenseApplication.class, args);
	}

}
