package com.example.demo.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Client;
import com.example.demo.domain.ItemOrder;
import com.example.demo.domain.PaymentWithBillet;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.enums.PaymentStatus;
import com.example.demo.repositories.ItemOrderRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.security.UserSpringSecurity;
import com.example.demo.services.exceptions.AuthorizationException;
import com.example.demo.services.exceptions.ObjectNotFounException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repo;
	
	@Autowired 
	private BilletService billetService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ItemOrderRepository itemOrderRepository;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFounException(
				"Object not found Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setClient(clientService.find(obj.getClient().getId()));
		obj.getPayment().setStatus(PaymentStatus.PENDING);
		obj.getPayment().setOrder(obj);
		
		if(obj.getPayment() instanceof PaymentWithBillet) {
			
			PaymentWithBillet pagto = (PaymentWithBillet) obj.getPayment();
			billetService.fillPaymentWithBillet(pagto, obj.getInstant());
		}
		
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());
		
		for(ItemOrder io: obj.getItens()) {
			
			io.setDiscount(0.0);
			io.setProduct(productRepository.findById(io.getProduct().getId()).get());
			io.setPrice(productRepository.findById(io.getProduct().getId()).get().getPrice());
			io.setOrder(obj);
			
		}
		
		itemOrderRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		UserSpringSecurity user = UserService.authenticated();
		if( user == null) {
			
			throw new AuthorizationException("Danied access!");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf( direction), orderBy);
		Client cli = clientService.find(user.getId());
		
		return repo.findByClient(cli, pageRequest);
		
		
	}

}
