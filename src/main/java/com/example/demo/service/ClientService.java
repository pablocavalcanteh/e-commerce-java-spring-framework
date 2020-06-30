package com.example.demo.service;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Address;
import com.example.demo.domain.City;
import com.example.demo.domain.Client;
import com.example.demo.domain.DTO.ClientDTO;
import com.example.demo.domain.DTO.ClientNewDTO;
import com.example.demo.domain.enums.ClientType;
import com.example.demo.domain.enums.Profile;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.security.UserSpringSecurity;
import com.example.demo.services.exceptions.AuthorizationException;
import com.example.demo.services.exceptions.DataIntegrityException;
import com.example.demo.services.exceptions.ObjectNotFounException;

@Service
public class ClientService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private ClientRepository repo;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private S3Service s3Service;
	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getAddresses());
		return obj;
	}

	public Client find(Integer id) {

		UserSpringSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {

			throw new AuthorizationException("Danied access!");
		}

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

	public Client findByEmail(String email) {

		UserSpringSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {

			throw new AuthorizationException("Danied Access!");
		}

		Client cli = repo.findByEmail(email);

		if (cli == null) {

			throw new ObjectNotFounException(
					"Object no found! Id: " + user.getId() + ", Tipo: " + Client.class.getName());
		}

		return cli;
	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

	public Client conversionFromDtoToClient(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);

	}

	public Client conversionFromDtoToClient(ClientNewDTO objDto) {
		Client client1 = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(),
				ClientType.toEnum(objDto.getClientType()), bCryptPasswordEncoder.encode(objDto.getPassword()));
		City city1 = new City(objDto.getCityId(), null, null);
		Address address = new Address(null, objDto.getPlace(), objDto.getNumber(), objDto.getComplement(),
				objDto.getNeighborhood(), objDto.getCep(), client1, city1);
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

	public URI uploadProfilePicture(MultipartFile multipartFile) {

		UserSpringSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Danied Access!");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}