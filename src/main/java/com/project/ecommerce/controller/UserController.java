package com.project.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<UserDto>(this.service.registerNewUser(userDto), HttpStatus.CREATED);
	}
	// create
		@PostMapping("/save")
		public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
			return new ResponseEntity<UserDto>(this.service.createUser(userDto), HttpStatus.CREATED);
		}
		
		// retrieve
		@GetMapping("/{id}")
		public ResponseEntity<UserDto> getUser(@PathVariable("id") int id){
			return new ResponseEntity<UserDto>(this.service.getUser(id), HttpStatus.OK);
		}
		@GetMapping("/")
		public ResponseEntity<List<UserDto>> getAllUser(){
			return ResponseEntity.ok(this.service.getAllUser());
		}
		// update
		@PutMapping("/update/{id}")
		public ResponseEntity<UserDto>  updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") int id){
			return new ResponseEntity<UserDto>(this.service.updateUser(userDto, id), HttpStatus.CREATED);
		}

		// delete
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
			this.service.deleteUser(id);
			return ResponseEntity.ok("User Deleted!");
		}
		
}
