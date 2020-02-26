package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Category;
import com.example.demo.domain.Pedido;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.services.exceptions.ObjectNotFounException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFounException(
				"Object not found Id: " + id + ", Tipo: " + Pedido.class.getName(), null));
	}

}
