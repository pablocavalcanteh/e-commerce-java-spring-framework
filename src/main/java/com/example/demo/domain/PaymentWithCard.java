package com.example.demo.domain;

import javax.persistence.Entity;

import com.example.demo.domain.enums.PaymentStatus;

@Entity
public class PaymentWithCard extends Payment {

	private static final long serialVersionUID = 1L;
	
	private Integer numberOfinstallments;

	public PaymentWithCard() {
		super();
	}

	public PaymentWithCard(Integer id, PaymentStatus status, Order order, Integer numberOfOfinstallments) {
		super(id, status, order);
		this.numberOfinstallments = numberOfOfinstallments;
	}

	public Integer getNumberOfinstallments() {
		return numberOfinstallments;
	}

	public void setNumberOfinstallments(Integer numberOfinstallments) {
		this.numberOfinstallments = numberOfinstallments;
	}
	
}
