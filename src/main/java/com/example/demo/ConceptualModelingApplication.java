package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.service.S3Service;

@SpringBootApplication
public class ConceptualModelingApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(ConceptualModelingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		s3Service.uploadFile("C:\\temp\\fotos\\aurora.jpg");
		
		
	}

}
