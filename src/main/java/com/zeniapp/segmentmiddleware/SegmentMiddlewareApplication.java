package com.zeniapp.segmentmiddleware;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import com.zeniapp.segmentmiddleware.configs.Configs;
import org.springframework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
public class SegmentMiddlewareApplication implements CommandLineRunner, ApplicationContextAware {
	@Autowired
	private Configs configs;
	
	private ApplicationContext context;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Argon2PasswordEncoder Argon2PasswordEncoder() {
		return new Argon2PasswordEncoder(
			this.configs.getSecurityPasswordHashingSaltWidth(), 
			this.configs.getSecurityPasswordHashingHashWidth(),
			1,
			this.configs.getSecurityPasswordHashingMemoryCosts(),
			this.configs.getSecurityPasswordHashingIterations()
		);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void run(String... args) {
		List<String> errorMessages = this.configs.checkAppVars();
		
		if (errorMessages.size() > 0) {
			SegmentMiddlewareApplication.log.error("----------------------------------------------------------------------");
			SegmentMiddlewareApplication.log.error("PRINTING ERROR MESSAGES");
			errorMessages.forEach(errorMessage -> SegmentMiddlewareApplication.log.error(errorMessage));
			SegmentMiddlewareApplication.log.error("SEGMENT MIDDLEWARE Stopped due to incorrect configuration");
			SegmentMiddlewareApplication.log.info("----------------------------------------------------------------------");

			((ConfigurableApplicationContext) context).close();
			return;
		}

		SegmentMiddlewareApplication.log.info("----------------------------------------------------------------------");
		SegmentMiddlewareApplication.log.info("PRINTING CONFIGURATIONS");
		SegmentMiddlewareApplication.log.info("server.port = {}", this.configs.getServerPort());
		SegmentMiddlewareApplication.log.info("service.version = {}", this.configs.getServiceVersion());
		SegmentMiddlewareApplication.log.info("service.name = {}", this.configs.getServiceName());
		SegmentMiddlewareApplication.log.info("security.api-key-http-header-name = {}", this.configs.getSecurityApiKeyHttpHeaderName());
		SegmentMiddlewareApplication.log.info("security.api-key-http-header-value = {}", this.configs.getSecurityApiKeyHttpHeaderValue());
		SegmentMiddlewareApplication.log.info("security.password-hashing.secret = {}", this.configs.getSecurityPasswordHashingSecret());
		SegmentMiddlewareApplication.log.info("security.password-hashing.iterations = {}", this.configs.getSecurityPasswordHashingIterations());
		SegmentMiddlewareApplication.log.info("security.password-hashing.hash-width = {}", this.configs.getSecurityPasswordHashingHashWidth());
		SegmentMiddlewareApplication.log.info("security.password-hashing.salt-width = {}", this.configs.getSecurityPasswordHashingSaltWidth());
		SegmentMiddlewareApplication.log.info("security.password-hashing.memory-costs = {}", this.configs.getSecurityPasswordHashingMemoryCosts());
		SegmentMiddlewareApplication.log.info("security.jwt-generation.secret = {}", this.configs.getSecurityJwtGenerationSecret());
		SegmentMiddlewareApplication.log.info("security.jwt-generation.duration = {}", this.configs.getSecurityJwtGenerationDuration());
		SegmentMiddlewareApplication.log.info("spring.jpa.hibernate.ddl-auto = {}", this.configs.getSpringJpaHibernateDdlAuto());
		SegmentMiddlewareApplication.log.info("spring.datasource.url = {}", this.configs.getSpringDatasourceUrl());
		SegmentMiddlewareApplication.log.info("spring.datasource.username = {}", this.configs.getSpringDatasourceUsername());
		SegmentMiddlewareApplication.log.info("spring.datasource.password = {}", this.configs.getSpringDatasourcePassword());
		SegmentMiddlewareApplication.log.info("spring.datasource.driver-class-name = {}", this.configs.getSpringDatasourceDriverClassName());
		SegmentMiddlewareApplication.log.info("spring.jpa.database-platform = {}", this.configs.getSpringJpaDatabasePlatform());
		SegmentMiddlewareApplication.log.info("SEGMENT MIDDLEWARE Started");
		SegmentMiddlewareApplication.log.info("----------------------------------------------------------------------");
	}

	public static void main(String[] args) {
		SpringApplication.run(SegmentMiddlewareApplication.class, args);
	}
}
