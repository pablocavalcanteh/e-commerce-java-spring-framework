package com.example.demo.domain.enums;

public enum Profile {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENT(2, "ROLE_CLIENT");
		
	private int code;
	private String description;
	
	
	private Profile(int code, String description) {
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
	
	
	public static Profile toEnum(Integer code) {
		
		if (code == null) {
			return null;
		}
		
		for (Profile ps : Profile.values()) {
			
			if (code.equals(ps.getCode())) {
				
				return ps;
			}
		}
		
		throw new IllegalArgumentException("Invalid id: " + code );
		
	}
	
	
}
