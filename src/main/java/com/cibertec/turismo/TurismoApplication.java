package com.cibertec.turismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cibertec.turismo")
public class TurismoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurismoApplication.class, args);
	}

}
