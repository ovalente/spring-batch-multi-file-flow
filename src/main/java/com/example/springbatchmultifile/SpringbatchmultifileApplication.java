package com.example.springbatchmultifile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringbatchmultifileApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchmultifileApplication.class, args);
	}

}
