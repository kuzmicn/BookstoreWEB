package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Bill;

public interface BillRepo extends JpaRepository<Bill, Integer> {

}
