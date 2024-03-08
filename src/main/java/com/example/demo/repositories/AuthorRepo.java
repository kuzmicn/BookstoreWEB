package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Author;

public interface AuthorRepo extends JpaRepository<Author, Integer> {

}
