package com.example.demo.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.security.UserSpringSecurity;

public class UserService {
	
	public static UserSpringSecurity authenticated() {
		try {
		return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		} catch(Exception e) {
			return null;
		}
	}

}
