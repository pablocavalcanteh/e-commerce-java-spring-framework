package com.example.demo.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date instant;
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL, mappedBy= "pedido")
	private Payment payment;
	@ManyToOne
	@JoinColumn(name= "client_id")
	private Client client;
	@ManyToOne
	@JoinColumn(name= "delivery_address_id")
	private Address deliveryAddress;
	@OneToMany(mappedBy= "id.order")
	private Set<ItemOrder> itens = new HashSet<>();
	
	
	public Pedido() {
	}

	public Pedido(Integer id, Date instant, Client client, Address deliveryAddress) {
		this.id = id;
		this.instant = instant;
		this.client = client;
		this.deliveryAddress = deliveryAddress;
	}
	
	public double getValueTotal() {
		double sum = 0.0;
		
		for(ItemOrder ip: itens) {
			
			sum += ip.getSubTotal();
		}
		
		return sum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstant() {
		return instant;
	}

	public void setInstant(Date instant) {
		this.instant = instant;
	}

	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}


	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public Set<ItemOrder> getItens() {
		return itens;
	}

	public void setItens(Set<ItemOrder> itens) {
		this.itens = itens;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(sdf.format(getInstant()));
		builder.append(", Cliente: ");
		builder.append(getClient().getName());
		builder.append(", Situação do pagamento: ");
		builder.append(getPayment().getStatus().getDescription());
		builder.append("\nDetalhes:\n");
		for(ItemOrder io: getItens()) {
			builder.append(io.toString());
		}
		builder.append("Valor Total: ");
		builder.append(nf.format(getValueTotal()));
		return builder.toString();
	}
	
	

	
}
