package com.ectoum.colosso.security.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ectoum.colosso.administration.user.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.ectoum.colosso.administration.user.User user = userRepository.findByEmail(email);
		
		if(user != null) {
			return new User(user.getEmail(), bcryptEncoder.encode(user.getPassword()), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("");
		}
	}

}
