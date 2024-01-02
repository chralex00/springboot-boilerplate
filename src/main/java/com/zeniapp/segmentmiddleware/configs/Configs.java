package com.zeniapp.segmentmiddleware.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.zeniapp.segmentmiddleware.guards.AdminAuthGuard;
import com.zeniapp.segmentmiddleware.guards.UserAuthGuard;
import com.zeniapp.segmentmiddleware.services.SessionService;
import lombok.Getter;

@Configuration
@EnableWebSecurity
public class Configs implements WebMvcConfigurer {
    @Value("${server.port:0}")
    @Getter
    private Integer serverPort;

    @Value("${service.version:}")
    @Getter
    private String serviceVersion;

    @Value("${service.name:}")
    @Getter
    private String serviceName;

    @Value("${security.api-key-http-header-name:}")
    @Getter
    private String securityApiKeyHttpHeaderName;

    @Value("${security.api-key-http-header-value:}")
    @Getter
    private String securityApiKeyHttpHeaderValue;

    @Value("${security.password-hashing.secret:}")
    @Getter
    private String securityPasswordHashingSecret;

    @Value("${security.password-hashing.iterations:0}")
    @Getter
    private Integer securityPasswordHashingIterations;

    @Value("${security.password-hashing.hash-width:0}")
    @Getter
    private Integer securityPasswordHashingHashWidth;

    @Value("${security.password-hashing.salt-width:0}")
    @Getter
    private Integer securityPasswordHashingSaltWidth;

    @Value("${security.password-hashing.memory-costs:0}")
    @Getter
    private Integer securityPasswordHashingMemoryCosts;

    @Value("${security.jwt-generation.secret:}")
    @Getter
    private String securityJwtGenerationSecret;

    @Value("${security.jwt-generation.duration:0}")
    @Getter
    private Long securityJwtGenerationDuration;

    @Value("${spring.jpa.hibernate.ddl-auto:}")
    @Getter
    private String springJpaHibernateDdlAuto;

    @Value("${spring.datasource.url:}")
    @Getter
    private String springDatasourceUrl;

    @Value("${spring.datasource.username:}")
    @Getter
    private String springDatasourceUsername;

    @Value("${spring.datasource.password:}")
    @Getter
    private String springDatasourcePassword;

    @Value("${spring.datasource.driver-class-name:}")
    @Getter
    private String springDatasourceDriverClassName;

    @Value("${spring.jpa.database-platform:}")
    @Getter
    private String springJpaDatabasePlatform;
    
    public List<String> checkAppVars() {
        List<String> errorMessages = new ArrayList<String>();

		if (this.serverPort == null ||
            this.serverPort < 1024 ||
            this.serverPort > 65535) {
            errorMessages.add("server.port is a required environment variable and it must be a number between 1024 and 65535");
        }

        if (this.serviceVersion == null ||
            this.serviceVersion.length() == 0) {
            errorMessages.add("service.version is a required environment variable and it must be a valid semver string");
        }

        if (this.serviceName == null ||
            this.serviceName.length() == 0) {
            errorMessages.add("service.name is a required environment");
        }

        if (this.securityApiKeyHttpHeaderName == null ||
            this.securityApiKeyHttpHeaderName.length() == 0) {
            errorMessages.add("security.api-key-http-header-name is a required environment variable");
        }

        if (this.securityApiKeyHttpHeaderValue == null ||
            this.securityApiKeyHttpHeaderValue.length() == 0) {
            errorMessages.add("security.api-key-http-header-value is a required environment variable");
        }

        if (this.securityPasswordHashingSecret == null ||
            this.securityPasswordHashingSecret.length() < 16 ||
            this.securityPasswordHashingSecret.length() > 128) {
            errorMessages.add("security.password-hashing.secret is a required environment variable and it must be a string between 16 and 128 characters");
        }

        if (this.securityPasswordHashingIterations == null ||
            this.securityPasswordHashingIterations < 512 ||
            this.securityPasswordHashingIterations > 8192) {
            errorMessages.add("security.password-hashing.iterations is a required environment variable and it must be a number between 512 and 8192");
        }

        if (this.securityPasswordHashingHashWidth == null ||
            this.securityPasswordHashingHashWidth < 64 ||
            this.securityPasswordHashingHashWidth > 1024) {
            errorMessages.add("security.password-hashing.hash-width is a required environment variable and it must be a number between 64 and 1024");
        }

        if (this.securityPasswordHashingSaltWidth == null ||
            this.securityPasswordHashingSaltWidth < 32 ||
            this.securityPasswordHashingSaltWidth > 512) {
            errorMessages.add("security.password-hashing.salt-width is a required environment variable and it must be a number between 32 and 512");
        }

        if (this.securityPasswordHashingMemoryCosts == null ||
            this.securityPasswordHashingMemoryCosts < 1024 ||
            this.securityPasswordHashingMemoryCosts > 65536) {
            errorMessages.add("security.password-hashing.memory-costs is a required environment variable and it must be a number between 1024 and 65536");
        }

        if (this.securityJwtGenerationSecret == null ||
            this.securityJwtGenerationSecret.length() < 16 ||
            this.securityJwtGenerationSecret.length() > 128) {
            errorMessages.add("security.jwt-generation.secret is a required environment variable and it must be a string between 16 and 128 characters");
        }

        if (this.securityJwtGenerationDuration == null ||
            this.securityJwtGenerationDuration < 15_000l || // 15 seconds
            this.securityJwtGenerationDuration > 2_678_400_000l) { // 31 days, or 1 month
            errorMessages.add("security.jwt-generation.duration is a required environment variable and it must be a number between 15 seconds and 31 days (or 1 month)");
        }

        if (this.springJpaHibernateDdlAuto == null ||
            this.springJpaHibernateDdlAuto.length() == 0 ||
            !Arrays.asList(new String[] { "none", "create", "update", "validate", "create-drop" }).contains(this.springJpaHibernateDdlAuto)) {
            errorMessages.add("spring.jpa.hibernate.ddl-auto is a required environment variable and it must be none, create, update, validate, or create-drop");
        }
        
        if (this.springDatasourceUrl == null ||
            this.springDatasourceUrl.length() == 0) {
            errorMessages.add("spring.datasource.url is a required environment variable");
        }
	    
        if (this.springDatasourceUsername == null ||
            this.springDatasourceUsername.length() == 0) {
            errorMessages.add("spring.datasource.username is a required environment variable");
        }

		if (this.springDatasourcePassword == null ||
            this.springDatasourcePassword.length() == 0) {
            errorMessages.add("spring.datasource.password is a required environment variable");
        }
        
        if (this.springDatasourceDriverClassName == null ||
            this.springDatasourceDriverClassName.length() == 0) {
            errorMessages.add("spring.datasource.driver-class-name is a required environment variable (preferably com.mysql.cj.jdbc.Driver)");
        }

        if (this.springJpaDatabasePlatform == null ||
            this.springJpaDatabasePlatform.length() == 0) {
            errorMessages.add("spring.jpa.database-platform is a required environment variable (preferably org.hibernate.dialect.MySQLDialect)");
        }

        return errorMessages;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Autowired
    private SessionService sessionService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .cors((cors) -> cors.configurationSource(this.corsConfigurationSource()))
            .authorizeHttpRequests(
                (requests) -> requests
                    .requestMatchers("/api/v1/admin/accounts/**").authenticated()
                    .requestMatchers("/api/v1/admin/sessions/**").authenticated()
                    .requestMatchers("/api/v1/admin/activities/**").authenticated()
                    .requestMatchers("/api/v1/admin/exercises/**").authenticated()
                    .requestMatchers("/api/v1/admin/foods/**").authenticated()
                    .requestMatchers("/api/v1/admin/muscles/**").authenticated()
                    .requestMatchers("/api/v1/admin/trainings/**").authenticated()
                    .requestMatchers("/api/v1/admin/diets/**").authenticated()
                    .requestMatchers("/api/v1/admin/tdees/**").authenticated()
                    .requestMatchers("/api/v1/user/accounts/**").authenticated()
                    .requestMatchers("/api/v1/user/activities/**").authenticated()
                    .requestMatchers("/api/v1/user/exercises/**").authenticated()
                    .requestMatchers("/api/v1/user/foods/**").authenticated()
                    .requestMatchers("/api/v1/user/muscles/**").authenticated()
                    .requestMatchers("/api/v1/user/trainings/**").authenticated()
                    .requestMatchers("/api/v1/user/diets/**").authenticated()
                    .requestMatchers("/api/v1/user/tdees/**").authenticated()
                    .requestMatchers("/api/v1/access/**").permitAll()
                    .requestMatchers("/api/v1/healthcheck/**").permitAll()
            )
            .addFilterBefore(
                new AdminAuthGuard(this.securityApiKeyHttpHeaderName, this.securityApiKeyHttpHeaderValue, this.securityJwtGenerationSecret, this.securityJwtGenerationDuration, this.sessionService),
                BasicAuthenticationFilter.class
            )
            .addFilterBefore(
                new UserAuthGuard(this.securityJwtGenerationSecret, this.securityJwtGenerationDuration, this.sessionService),
                BasicAuthenticationFilter.class
            );

        return http.build();
    }
}
