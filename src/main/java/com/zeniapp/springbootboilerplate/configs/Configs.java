package com.zeniapp.springbootboilerplate.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.Getter;

@Configuration
@EnableWebMvc
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
            errorMessages.add("service.name is a required environment variable");
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
}
