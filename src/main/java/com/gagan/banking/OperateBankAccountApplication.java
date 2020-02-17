package com.gagan.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OperateBankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperateBankAccountApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner setupDefaultUser(UserService service) {
		return args -> {
			service.save(new User("super.admin", "password", "First Name",Arrays.asList(new Role("USER"), new Role("ACTUATOR")), true));
		};
	}*/

}
