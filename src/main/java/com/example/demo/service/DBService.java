package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Address;
import com.example.demo.domain.Category;
import com.example.demo.domain.City;
import com.example.demo.domain.Client;
import com.example.demo.domain.ItemOrder;
import com.example.demo.domain.Payment;
import com.example.demo.domain.PaymentWithBillet;
import com.example.demo.domain.PaymentWithCard;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Product;
import com.example.demo.domain.State;
import com.example.demo.domain.enums.ClientType;
import com.example.demo.domain.enums.PaymentStatus;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ItemOrderRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.StateRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ItemOrderRepository itemOrderRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Category cat1 = new Category(null, "Computing");
		Category cat2 = new Category(null, "Office");
		Category cat3 = new Category(null,"Bed Table and Bath");
		Category cat4 = new Category(null,"Eletronics");
		Category cat5 = new Category(null,"Gardening");
		Category cat6 = new Category(null,"Decoration");
		Category cat7 = new Category(null,"Perfumery");
		
 
		Product p1 = new Product(null, "Computing", 2000.00);
		Product p2 = new Product(null, "Printer", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Office Desk", 300.00);
		Product p5 = new Product(null, "Towel", 50.00);
		Product p6 = new Product(null, "Quilt", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Brush Cutter", 800.00);
		Product p9 = new Product(null, "Bedside Lamp", 100.00);
		Product p10 = new Product(null, "Microwave", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);
		

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));
		

		categoryRepository.saveAll((Iterable<Category>) Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");

		City city1 = new City(null, "Uberlândia", state1);
		City city2 = new City(null, "São Paulo", state2);
		City city3 = new City(null, "Campinas", state2);

		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PHYSICALPERSON);

		cli1.getPhones().addAll(Arrays.asList("27363323", "9383839"));

		Address address1 = new Address(null, "Rua das Flores", "300", "Apto 303", "Jardim", "38220834", cli1, city1);
		Address address2 = new Address(null, "Av. Matos", "105", "Sala 800", "Centro", "387770012", cli1, city2);

		cli1.getAdresses().addAll(Arrays.asList(address1, address2));

		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(address1, address2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		
		Pedido order1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, address1);
		Pedido order2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, address2);
		
		Payment payment1 = new PaymentWithCard(null, PaymentStatus.SETTLED, order1, 6);
		order1.setPayment(payment1);
		Payment payment2 = new PaymentWithBillet(null, PaymentStatus.PENDING, order2, sdf.parse("20/10/2017 00:00"), null);
		order2.setPayment(payment2);
		
		cli1.getOrders().addAll(Arrays.asList(order1, order2));
		
		
		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(payment1, payment2));
		
		ItemOrder itemOrder = new ItemOrder(order1, p1, 0.00, 1, 2000.00);
		ItemOrder itemOrder2 = new ItemOrder(order1, p3, 0.00, 2, 80.00);
		ItemOrder itemOrder3 = new ItemOrder(order2, p2, 100.00, 1, 800.00);
		
		order1.getItens().addAll(Arrays.asList(itemOrder, itemOrder2, itemOrder3));
		order2.getItens().addAll(Arrays.asList(itemOrder2));
		
		p1.getItens().addAll(Arrays.asList(itemOrder));
		p2.getItens().addAll(Arrays.asList(itemOrder3));
		p3.getItens().addAll(Arrays.asList(itemOrder2));
		
		itemOrderRepository.saveAll((Iterable<ItemOrder>) Arrays.asList(itemOrder, itemOrder2, itemOrder3));
		
	}

}
