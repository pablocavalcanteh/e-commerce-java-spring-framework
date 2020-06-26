package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Client;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClientRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Client cli = repo.findByEmail(email);
		if( cli == null) {
			throw new UsernameNotFoundException(email);
		}

		return new UserSpringSecurity(cli.getId(), cli.getEmail(), cli.getPassword(), cli.getProfile());
	}

}
