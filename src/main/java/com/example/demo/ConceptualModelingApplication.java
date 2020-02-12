package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.domain.Category;
import com.example.demo.repositories.CategoryRepository;

@SpringBootApplication
public class ConceptualModelingApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(ConceptualModelingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null,"Computing");
		
		Category cat2 = new Category(null, "Office");
		
		categoryRepository.save(cat1);
		categoryRepository.save(cat2);

		
	}

}
