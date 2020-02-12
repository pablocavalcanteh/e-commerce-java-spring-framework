package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;

@SpringBootApplication
public class ConceptualModelingApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ConceptualModelingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null,"Computing");
		Category cat2 = new Category(null, "Office");
		
		Product p1 = new Product(null, "Computing", 2000.00);
		Product p2 = new Product(null, "Printer", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		
		categoryRepository.save(cat1);
		categoryRepository.save(cat2);
		productRepository.save(p1);
		productRepository.save(p2);
		productRepository.save(p3);
		
	}

}
