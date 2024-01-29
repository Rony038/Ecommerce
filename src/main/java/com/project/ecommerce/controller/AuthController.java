package com.project.ecommerce.controller;

import java.net.http.HttpRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.security.JwtTokenHelper;
import com.project.ecommerce.service.UserService;
import com.project.ecommerce.exception.ApiException;
import com.project.ecommerce.dto.JwtAuthRequest;
import com.project.ecommerce.dto.JwtAuthResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService service;

	// create users
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		return new ResponseEntity<>(this.service.createUser(userDto), HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUserName(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());

		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String userName, String password) throws Exception {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password);
		try {
			this.authenticationManager.authenticate(authToken);
		} catch (BadCredentialsException e) {
			System.out.println("error...");
			throw new ApiException("Invalid User name or Password");
		}

	}

}
