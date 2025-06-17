package com.project.pk48.inventoryms_springboot_api;

import com.project.pk48.inventoryms_springboot_api.security.RsaKeyProperties;
import com.project.pk48.inventoryms_springboot_api.security.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class InventorymsSpringbootApiApplication {
	@Bean
	public AuditorAware<String> auditorAware(){
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(InventorymsSpringbootApiApplication.class, args);
	}

}
