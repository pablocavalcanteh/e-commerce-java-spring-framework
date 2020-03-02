package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Address;
import com.example.demo.domain.City;
import com.example.demo.domain.Client;
import com.example.demo.domain.DTO.ClientDTO;
import com.example.demo.domain.DTO.ClientNewDTO;
import com.example.demo.domain.enums.ClientType;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.services.exceptions.DataIntegrityException;
import com.example.demo.services.exceptions.ObjectNotFounException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	@Autowired
	private AddressRepository addressRepository;
	
	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getAddresses());
		return obj;
	}

	public Client find(Integer id) {
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFounException(
				"Object not found Id: " + id + ", Tipo: " + Client.class.getName(), null));
	}

	public Client update(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Cannot delete because there are related entities.");
		}
	}

	public List<Client> findAll() {

		return repo.findAll();
	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

	public Client conversionFromDtoToClient(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);

	}
	
	public Client conversionFromDtoToClient(ClientNewDTO objDto) {
		Client client1 = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(), ClientType.toEnum(objDto.getClientType()));
		City city1 = new City(objDto.getCityId(), null, null);
		Address address = new Address(null, objDto.getPlace(), objDto.getNumber(), objDto.getComplement(), objDto.getNeighborhood(), objDto.getCep(), client1, city1);
		client1.getAddresses().add(address);
		client1.getPhones().add(objDto.getPhone1());
		
		if (objDto.getPhone2() != null) {
			client1.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			client1.getPhones().add(objDto.getPhone3());
		}
		
		return client1;

	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
}