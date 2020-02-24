package com.example.demo.domain.enums;

public enum PaymentStatus {
	
	PENDING(1, "Pending"),
	SETTLED(2, "Settled"),
	CANCELED(3, "Canceled");
	
	
	private int code;
	private String description;
	
	
	private PaymentStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public static PaymentStatus toEnum(Integer code) {
		
		if (code == null) {
			return null;
		}
		
		for (PaymentStatus ps : PaymentStatus.values()) {
			
			if (code.equals(ps.getCode())) {
				
				return ps;
			}
		}
		
		throw new IllegalArgumentException("Invalid id: " + code );
		
	}
	
	
}
