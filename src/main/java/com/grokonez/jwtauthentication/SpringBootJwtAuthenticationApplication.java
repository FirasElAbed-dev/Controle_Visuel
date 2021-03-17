package com.grokonez.jwtauthentication;

import java.util.Arrays;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;





@SpringBootApplication
public class SpringBootJwtAuthenticationApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtAuthenticationApplication.class, args);
	}
	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(SpringBootJwtAuthenticationApplication.class);
	  }
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				connector.setProperty("relaxedQueryChars", "|{}[]");
			}
		});
		return factory; 
	}
	/*
	 * @Bean CommandLineRunner init(IRepertoireRepository repertoireRepository) {
	 * return args -> { List<String> repertoires =
	 * Arrays.asList("default directory");
	 * List<Repertoire>list=repertoireRepository.findAll(); if (list.isEmpty()) {
	 * repertoires.forEach(name -> repertoireRepository.save(new Repertoire(1L,
	 * "D:/rep/repIn/","D:/rep/repOut/","D:/rep/repArch/"))); } }; }
	 */
}
