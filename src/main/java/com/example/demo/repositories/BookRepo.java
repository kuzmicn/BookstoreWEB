package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {
	@Query("select b from Book b where "
			+ "(:title is null or b.title like %:title%) "
	       + "and (:genres is null or exists ("
	       			+ "select bg from BookGenre bg where "
	       			+ "bg.book.id=b.id and bg.genre.id in :genres)) "
	        + "and (:authors is null or b.author.id in :authors)"
			)//Could be implemented with criteria API
	Page<Book> getFilteredBooks(@Param("authors") List<Integer> authors, 
			@Param("genres") List<Integer> genres, 
			@Param("title") String title, 
			Pageable p);
}
