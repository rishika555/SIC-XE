package com.assembler.sicxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SicxeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicxeApplication.class, args);
	}

}
