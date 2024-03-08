package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
}
