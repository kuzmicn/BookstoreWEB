package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import model.Book;
import model.Review;
import model.User;

public interface ReviewRepo extends JpaRepository<Review, Integer>{
	List<Review> findByBook(Book b);
	Review findByBookAndUser(Book b, User u);
}
