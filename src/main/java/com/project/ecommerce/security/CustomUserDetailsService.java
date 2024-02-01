package com.project.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.ecommerce.exception.UserNotFoundWithEmailException;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepository.findByEmail(username).orElseThrow(()->new UserNotFoundWithEmailException("User", " User ", username));
		return user;
	}

}
