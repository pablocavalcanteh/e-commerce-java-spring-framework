package com.example.demo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.demo.domain.enums.ClientType;
import com.example.demo.domain.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@Column(unique=true)
	private String email;
	private String cpfOrCnpj;
	private Integer clientType;
	
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy= "client", cascade=CascadeType.ALL)
	private List<Address> addresses = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name= "Phone")
	private Set<String> phones = new HashSet<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PROFILE")
	private Set<Integer> profiles = new HashSet<>();
	@JsonIgnore
	@OneToMany(mappedBy="client")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Client() {
		addProfile(Profile.CLIENT);
	}

	public Client(Integer id, String name, String email, String cpfOrCnpj, ClientType clientType, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOrCnpj = cpfOrCnpj;
		this.clientType = (clientType == null) ? null : clientType.getCod();
		this.password = password;
		addProfile(Profile.CLIENT);
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

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

	public ClientType getClientType() {
		return ClientType.toEnum(clientType);
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType.getCod();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Profile> getProfile() {
		
		return profiles.stream().map( x -> Profile.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addProfile(Profile profile) {
		
		profiles.add(profile.getCode());
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> adresses) {
		this.addresses = adresses;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
			
}
