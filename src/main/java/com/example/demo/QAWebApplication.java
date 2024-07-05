package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SpringBootApplication
public class QAWebApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(QAWebApplication.class, args);
	}
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // Autorisez toutes les URL
	                .allowedOrigins("http://localhost:4200") // Autorisez uniquement les requêtes provenant de localhost:4200
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes autorisées
	                .allowedHeaders("Content-Type", "Authorization") // En-têtes autorisés
	                .allowCredentials(true); // Autoriser les informations d'identification telles que les cookies
	    }

}
