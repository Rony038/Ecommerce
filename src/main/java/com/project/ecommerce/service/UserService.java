package com.project.ecommerce.service;

import java.util.List;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.model.User;

import jakarta.validation.Valid;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);

	UserDto createUser(UserDto userDto);

	UserDto getUser(int id);

	List<UserDto> getAllUser();

	UserDto updateUser(@Valid UserDto userDto, int id);

	void deleteUser(int id);
	
	User loadUserByUsername(String username);
    
    User getUserByUsername(String username);

}
