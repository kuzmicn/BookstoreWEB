package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Genre;

public interface GenreRepo extends JpaRepository<Genre, Integer> {
	public Genre findByName(String name);
}
