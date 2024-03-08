package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Book;
import model.BookGenre;

public interface BookGenreRepo extends JpaRepository<BookGenre, Integer> {
	public Integer deleteByBook(Book b);
}
