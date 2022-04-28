package com.a100nts;

import com.a100nts.repositories.SiteRepository;
import com.a100nts.services.DbSeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootApplication
public class Application {

	@Autowired
	private static PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

//		boolean test = passwordEncoder.matches("123456", "$2a$10$Y31ZFEnyBUJNVfQZzEfpV.LKB0ze4cimtQuv9qmKQKsYWQYyVS7QO");

		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private DbSeedService dbSeedService;

	@Autowired
	private SiteRepository siteRepository;

	@EventListener
	void seedDatabase(ContextRefreshedEvent event) {
		if(siteRepository.count() == 0) {
			dbSeedService.fillDb();
		}

	}

}
