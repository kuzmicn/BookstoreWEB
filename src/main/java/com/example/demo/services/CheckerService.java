package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import model.BookGenre;

@Service
public class CheckerService {
	public boolean check(int a, List<BookGenre> bg) {
		return bg.stream()
				.mapToInt(bgenre -> bgenre.getGenre().getId())
				.anyMatch(n -> n == a);
	}
	public boolean check2(int a, List<Integer> array) {
		for(int p:array)
			if(p==a)
				return true;
		return false;
	}
}
