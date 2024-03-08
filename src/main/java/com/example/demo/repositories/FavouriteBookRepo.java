package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Book;
import model.FavouriteBook;
import model.User;

public interface FavouriteBookRepo extends JpaRepository<FavouriteBook, Integer> {
	public FavouriteBook findByUserAndBook(User u, Book b);
}
