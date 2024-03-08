package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.FavouriteBookRepo;
import com.example.demo.repositories.RoleRepo;
import com.example.demo.repositories.UserRepo;

import model.Book;
import model.FavouriteBook;
import model.User;

@Service
public class UserService {
	@Autowired
	UserRepo ur;
	
	@Autowired
	FavouriteBookRepo fbr;
	
	@Autowired
	RoleRepo rr;
	
	@Autowired
	PasswordEncoder passEncoder;
	
	public void addToFavourites(User u, Book b) {
		if(fbr.findByUserAndBook(u, b)!=null)
			throw new RuntimeException("This book is already in your favourites");
		
		FavouriteBook fb=new FavouriteBook();
		fb.setBook(b);
		fb.setUser(u);
		
		fbr.save(fb);
	}
	
	public void deleteFromFavourites(User u, Book b) {
		FavouriteBook fb=fbr.findByUserAndBook(u, b);
		if(fb==null)
			throw new RuntimeException("The book you wanted to remove from favourites "
										+ "is not in your favourites!");
		fbr.delete(fb);
	}
	
	public User getUser(int id) {
		Optional<User> userOp=ur.findById(id);
		if(userOp.isEmpty())
			throw new RuntimeException("User does not exist!");
		return userOp.get();
	}
	
	public User getUser(String username) {
		User user=ur.findByUsername(username);
		if(user==null)
			throw new RuntimeException("User does not exist!");
		return user;
	}

	public void saveUser(String username, String password, String email, String name, 
			String surname, String role) {
		
		if(password.length()<5)
			throw new RuntimeException("Your password should have at least 5 characters!");
		
		if(ur.findByUsername(username)!=null)
			throw new RuntimeException("This username is already taken");
		
		User u = new User();
		u.setEmail(email);
		u.setName(name);
		u.setPassword(passEncoder.encode(password));
		u.setSurname(surname);
		u.setUsername(username);
		u.setRole(rr.findByName(role));
		u = ur.save(u);
	}
}
