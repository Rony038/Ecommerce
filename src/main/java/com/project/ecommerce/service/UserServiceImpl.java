package com.project.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.JoinColumn;

import com.project.ecommerce.configuration.Const;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.exception.UserNotFoundWithEmailException;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.repository.RoleRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	public UserDto createUser(UserDto dto) {
		User user = convertToUser(dto);
		this.repository.save(user);
		user.getRoles().add(Const.ROLE_NORMAL);
		return convertToDto(user);
	}

	@Override
	public UserDto getUser(int id) {
		User user = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", id));
		return this.convertToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> userList = this.repository.findAll();
		return userList.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public UserDto updateUser(UserDto dto, int id) {
		User user = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", id));

		user.setUserName(dto.getUserName());
		user.setPassword(dto.getPassword());
		user.setEmail(dto.getEmail());

		return this.convertToDto(this.repository.save(user));
	}

	@Override
	public void deleteUser(int id) {
		User user = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", id));

		this.repository.delete(user);
	}

	public UserDto convertToDto(User user) {
		return this.modelMapper.map(user, UserDto.class);

	}

	public User convertToUser(UserDto dto) {
		User user = modelMapper.map(dto, User.class);
		user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
		//return this.modelMapper.map(dto, User.class);
		return user;
	}


	@Override
	public User getUserByUsername(String username) {
		User user = repository.findByEmail(username)
				.orElseThrow(() -> new UserNotFoundWithEmailException("User not found with username " + username));
		return user;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = convertToUser(userDto);
		user.getRoles().add(Const.ROLE_ADMIN);
		User newUser = this.repository.save(user);

		return convertToDto(user);
	}
}
