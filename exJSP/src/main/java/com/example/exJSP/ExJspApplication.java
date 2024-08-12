package com.example.exJSP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing //BasicEntity를 사용하기 위함.
@EnableJpaRepositories(basePackages = "com.example.exJSP.repository")
public class ExJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExJspApplication.class, args);
		System.out.println("http://localhost:8080/exJSP");
	}

}
