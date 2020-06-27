package com.example.demo.service;

import org.springframework.mail.SimpleMailMessage;

import com.example.demo.domain.Client;
import com.example.demo.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Client client, String newPass);

}
