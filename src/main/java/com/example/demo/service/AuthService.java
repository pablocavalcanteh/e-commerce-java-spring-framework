package com.example.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Client;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.services.exceptions.ObjectNotFounException;

@Service
public class AuthService {
	
	@Autowired
	private ClientRepository repo;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService service;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Client client = repo.findByEmail(email);
		
		if ( client == null) {
			throw new ObjectNotFounException("Email not found!");
		}
		
		String newPass = newPassword();
		client.setPassword(pe.encode(newPass));
		
		repo.save(client);
		service.sendNewPasswordEmail(client, newPass);
		
	}

	private String newPassword() {
		
		char[] vet = new char[10];
		
		for (int i = 0; i < 10; i++) {
			
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt	= rand.nextInt(3);
		
		if (opt == 0) {
			
			return (char) (rand.nextInt(10) + 48);
			
		} else if (opt == 1) {
			
			return (char) (rand.nextInt(26) + 65);
			
		} else {
			return (char) (rand.nextInt(26) + 97);
		}
		
	}

}
