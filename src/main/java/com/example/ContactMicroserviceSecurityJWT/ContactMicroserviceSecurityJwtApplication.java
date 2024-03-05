package com.example.ContactMicroserviceSecurityJWT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class ContactMicroserviceSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactMicroserviceSecurityJwtApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

}
