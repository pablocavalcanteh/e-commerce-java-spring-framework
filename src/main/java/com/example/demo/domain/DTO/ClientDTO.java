package com.example.demo.domain.DTO;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.example.demo.domain.Client;
import com.example.demo.service.validation.ClientUpdate;

@ClientUpdate
public class ClientDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotEmpty(message="Mandatory filling")
	@Length(min=5, max=80, message="The length must be between 5 and 80 characters")
	private String name;
	@NotEmpty(message="Invalid email")
	@Email
	private String email;
	
	public ClientDTO() {
	}
	
	public ClientDTO(Client client) {
		
		this.id = client.getId();
		this.name = client.getName();
		this.email = client.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
