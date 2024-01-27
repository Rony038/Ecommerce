package com.project.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserRepository repository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto dto) {
        return this.convertToDto(this.repository.save(this.convertToUser(dto)));
    }

    @Override
    public UserDto getUser(int id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", id));
        return this.convertToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = this.repository.findAll();
        return userList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto dto, int id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", id));
        
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());

        return this.convertToDto(this.repository.save(user));
    }

    @Override
    public void deleteUser(int id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", id));

        this.repository.delete(user);
    }

    public UserDto convertToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);

    }

    public User convertToUser(UserDto dto) {
        return this.modelMapper.map(dto, User.class);

    }
}
