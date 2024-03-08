package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.OrderedBook;

public interface OrderedBookRepo extends JpaRepository<OrderedBook, Integer> {
	@Query("select ob from OrderedBook ob order by ob.book.id")
	public List<OrderedBook> getSortedByBooks();
}
