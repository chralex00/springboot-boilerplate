package com.zeniapp.springbootboilerplate;

import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import com.zeniapp.springbootboilerplate.configs.Configs;
import org.springframework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication()
public class SpringBootBoilerplateApplication implements CommandLineRunner, ApplicationContextAware {
	@Autowired
	private Configs configs;
	
	private ApplicationContext context;

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void run(String... args) {
		List<String> errorMessages = this.configs.checkAppVars();
		
		if (errorMessages.size() > 0) {
			SpringBootBoilerplateApplication.log.error("----------------------------------------------------------------------");
			SpringBootBoilerplateApplication.log.error("PRINTING ERROR MESSAGES");
			errorMessages.forEach(errorMessage -> SpringBootBoilerplateApplication.log.error(errorMessage));
			SpringBootBoilerplateApplication.log.error("SPRING BOOT BOILERPLATE Stopped due to incorrect configuration");
			SpringBootBoilerplateApplication.log.info("----------------------------------------------------------------------");

			((ConfigurableApplicationContext) context).close();
			return;
		}

		SpringBootBoilerplateApplication.log.info("----------------------------------------------------------------------");
		SpringBootBoilerplateApplication.log.info("PRINTING CONFIGURATIONS");
		SpringBootBoilerplateApplication.log.info("server.port = {}", this.configs.getServerPort());
		SpringBootBoilerplateApplication.log.info("service.version = {}", this.configs.getServiceVersion());
		SpringBootBoilerplateApplication.log.info("service.name = {}", this.configs.getServiceName());
		SpringBootBoilerplateApplication.log.info("SPRING BOOT BOILERPLATE Started");
		SpringBootBoilerplateApplication.log.info("----------------------------------------------------------------------");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBoilerplateApplication.class, args);
	}
}
