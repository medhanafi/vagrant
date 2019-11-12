package net.siinergy.dsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DscApplication {

	public static void main(String[] args) {
		SpringApplication.run(DscApplication.class, args);
	}

}
