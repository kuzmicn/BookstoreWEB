package com.example.demo.services;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.repositories.AuthorRepo;
import com.example.demo.repositories.BookGenreRepo;
import com.example.demo.repositories.BookRepo;
import com.example.demo.repositories.GenreRepo;
import com.example.demo.repositories.OrderedBookRepo;

import jakarta.transaction.Transactional;
import model.Author;
import model.Book;
import model.BookGenre;
import model.Genre;
import model.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class AdminService {
	@Autowired
	BookRepo br;

	@Autowired
	BookGenreRepo bgr;

	@Autowired
	GenreRepo gr;

	@Autowired
	AuthorRepo ar;
	
	@Autowired
	OrderedBookRepo obr;
	
	@Autowired
	BookService bs;
	
	@Autowired
	ReportService rs;
	
	@Autowired
	EmailService es;
	
	public byte[] generateSalesReport(User u) {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("admin", u.getUsername());
		byte[] report=null;
		try {
			report=rs.generateReport("/jasperreports/salesReport.jrxml", params, 
					new JRBeanCollectionDataSource(obr.getSortedByBooks()));
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
		
		 String mail=u.getEmail();
		  
		 if(mail!=null)
			 es.sendEmailWithAttachment(mail,"emailsendernoreply123@gmail.com", 
					 "New sales report!", "Greetings!\nPlease find your sales report attached.", 
					 report, "salesReport");
		 
		
		return report;
	}

	public List<Book> getAllBooks(){
		return br.findAll();
	}
	
	@Transactional
	public void updateBook(String title, String description, Date issueDate, int authorId, int[] genreIds,
			MultipartFile picture, int id, int price) {
		
		if(title.isEmpty())
			throw new RuntimeException("Non-existent title");
		
		if(description.isEmpty())
			throw new RuntimeException("Non-existent description");
		
		Optional<Author> author = ar.findById(authorId);
		if(author.isEmpty())
			throw new RuntimeException("Non-existent author");
		
		Book book = bs.getBook(id);
		book.setTitle(title);
		book.setDescription(description);
		book.setIssueDate(issueDate);
		book.setAuthor(author.get());
		book.setPrice(price);
		if(picture!=null && picture.getSize()>0 && picture.getSize()<=64000)
			try {
				book.setPicture(picture.getBytes());
			} catch (IOException e) {
				//Picture wont be set if it is invalid
			}
		
		br.saveAndFlush(book);
		
		bgr.deleteByBook(book);
		
		for (Integer genreId : genreIds) {
			Optional<Genre> genre =gr.findById(genreId);
			if(genre.isEmpty())
				continue;
			BookGenre bookGenre = new BookGenre();
			bookGenre.setBook(book);
			bookGenre.setGenre(genre.get());
			
			bgr.save(bookGenre);
		}		
	}
	
	public Book addBook(String title, String description, Date issueDate, int authorId, int[] genreIds,
			MultipartFile picture, int price) {

		if(issueDate==null)
			throw new RuntimeException("Non-existent issueDate");
		
		if(title.isEmpty())
			throw new RuntimeException("Non-existent title");
		
		if(description.isEmpty())
			throw new RuntimeException("Non-existent description");
		
		Optional<Author> author = ar.findById(authorId);
		if(author.isEmpty())
			throw new RuntimeException("Non-existent author");
		
		if(price<1)
			throw new RuntimeException("Price must be bigger than 0");
		
		if(picture.getSize()==0)
			throw new RuntimeException("Non-existent picture");
		
		if(picture.getSize()>64000)
			throw new RuntimeException("Picture too big");
		
		Book book = new Book();
		book.setTitle(title);
		book.setDescription(description);
		book.setIssueDate(issueDate);
		book.setAuthor(author.get());
		book.setPrice(price);
		try {
			book.setPicture(picture.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Upload valid picture");
		}

		br.saveAndFlush(book);

		for (Integer genreId : genreIds) {
			Optional<Genre> genre =gr.findById(genreId);
			if(genre.isEmpty())
				continue;
			BookGenre bookGenre = new BookGenre();
			bookGenre.setBook(book);
			bookGenre.setGenre(genre.get());
			
			bgr.save(bookGenre);
		}		
		
		return book;
	}

	public List<Genre> getAllGenres() {
		return gr.findAll();
	}

	public boolean addGenre(String name) {
		if (name.isEmpty())
			throw new RuntimeException("No name in genre");
		if (gr.findByName(name) != null)
			throw new RuntimeException("This genre already exists!");
		Genre genre = new Genre();
		genre.setName(name);
		gr.save(genre);
		return true;
	}

	public void deleteGenre(int genreId) {
		gr.deleteById(genreId);// If exception occurs, exceptionhandler catches it
	}

	public List<Author> getAllAuthors() {
		return ar.findAll();
	}

	public void addAuthor(String name, String surname) {
		if (name.isEmpty() || surname.isEmpty())
			throw new RuntimeException("You need to pass name and surname both to add author!");

		Author author = new Author();
		author.setName(name);
		author.setSurname(surname);
		ar.save(author);
	}

	public void deleteAuthor(@RequestParam("authorId") int authorId) {
		ar.deleteById(authorId);// If exception occurs, exceptionhandler catches it
	}
}
