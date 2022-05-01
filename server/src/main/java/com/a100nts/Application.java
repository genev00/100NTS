package com.a100nts;

import com.a100nts.repositories.SiteRepository;
import com.a100nts.services.impl.DbSeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
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
