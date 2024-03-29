package com.project.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String userName);
	
	//User findByUserName(String username);
}
