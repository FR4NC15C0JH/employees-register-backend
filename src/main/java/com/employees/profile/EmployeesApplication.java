package com.employees.profile;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employees.profile.enumerator.Profiles;
import com.employees.profile.model.User;
import com.employees.profile.repository.UserRepository;

@SpringBootApplication
public class EmployeesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUser(userRepository, passwordEncoder);
		};
	}
	
	private void initUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User admin = new User();
		admin.setEmail("admin@employees.com");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfiles(Profiles.ROLE_ADMIN);
		
		User find = userRepository.findByEmail("admin@employees.com");
		if(find == null) {
			userRepository.save(admin);
		}
	}
}
