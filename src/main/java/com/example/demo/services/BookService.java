package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.repositories.AuthorRepo;
import com.example.demo.repositories.BookGenreRepo;
import com.example.demo.repositories.BookRepo;
import com.example.demo.repositories.GenreRepo;
import com.example.demo.repositories.ReviewRepo;

import model.Author;
import model.Book;
import model.Genre;
import model.Review;
import model.User;

@Service
public class BookService {
	@Autowired
	BookRepo br;

	@Autowired
	ReviewRepo rr;

	@Autowired
	BookGenreRepo bgr;
	
	@Autowired
	AuthorRepo ar;
	
	@Autowired
	GenreRepo gr;
	
	public List<Author> getAuthors(){
		return ar.findAll();
	}
	
	public List<Genre> getGenres(){
		return gr.findAll();
	}

	public Page<Book> getFilteredBooks(List<Integer> genres, List<Integer> authors, String title, Pageable p) {

		
		return  br.getFilteredBooks(authors, genres, title, p);
	}

	public Book getBook(int id) {
		Optional<Book> bookOp = br.findById(id);
		if (bookOp.isEmpty()) {
			throw new RuntimeException("The book that you selected does not exist!");
		}
		return bookOp.get();
	}

	public List<Book> getAllBooks() {
		return br.findAll();
	}

	public void addReview(ReviewDTO rev, User user) {
		Optional<Book> bookOp = br.findById(rev.getBookId());
		if (bookOp.isEmpty()) {
			throw new RuntimeException("The book that you tried to review does not exist!");
		}

		if (rev.getGrade() > 5 || rev.getGrade() < 1)
			throw new RuntimeException("Grade must be between 1 and 5!");

		Book b = bookOp.get();

		if (rr.findByBookAndUser(b, user) != null)
			throw new RuntimeException("You already reviewed this book!");

		Review review = new Review();
		review.setUser(user);
		review.setBook(b);
		review.setComment(rev.getComment());
		review.setGrade(rev.getGrade());

		review = rr.save(review);
	}
}
