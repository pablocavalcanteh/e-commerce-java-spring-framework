package com.example.demo.domain.DTO;

import java.io.Serializable;

import com.example.demo.domain.Product;

public class ProductDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Double price;
	
	
	public ProductDTO(Product obj) {
		
		this.id = obj.getId();
		this.name = obj.getName();
		this.price = obj.getPrice();
		
	}
	
public ProductDTO() {	
		
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}	
}
