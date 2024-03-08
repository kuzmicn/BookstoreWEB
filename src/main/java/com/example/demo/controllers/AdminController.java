package com.example.demo.controllers;

import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.services.AdminService;
import com.example.demo.services.BookService;
import com.example.demo.services.CheckerService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import model.User;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
	AdminService as;
	
	@Autowired
	CheckerService checker;
	
	@Autowired
	BookService bs;
	
	@Autowired
	UserService us;
	
	@GetMapping("addAdmin")
	public String addNewAdmin() {
		return "register";
	}
	
	@PostMapping("register-confirm")
	public String register(@RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("name") String name,
			@RequestParam("surname") String surname, @RequestParam("email") String email) {
		us.saveUser(username,password,email,name,surname, "ADMIN");
		return "redirect:/books/search";
	}
	
	@PostMapping("salesReport")
	public void getSalesReport(HttpServletRequest req, HttpServletResponse response) throws IOException {
		User u=us.getUser(req.getUserPrincipal().getName());
		byte[] sales=as.generateSalesReport(u);
		if(sales==null)
			throw new RuntimeException("There has been an error while generating sales report!");
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=sales.pdf");
		response.setContentLength(sales.length);
		response.getOutputStream().write(sales);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@GetMapping("manageBooks")
	public String getBooks(Model m) {
		m.addAttribute("genreList", as.getAllGenres());
		m.addAttribute("authorList", as.getAllAuthors());
		return "admin/adminBooksPage";
	}
	
	@PostMapping("addBook")
	public String addBook(@RequestParam String title,
				@RequestParam String description,
    			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date issueDate,
    			@RequestParam int authorId,
    			@RequestParam int price,
    			@RequestParam int[] genres,
    			@RequestParam MultipartFile picture,
    			Model m) {
		Book b= as.addBook(title, description, issueDate, authorId, genres, picture, price);
		
		return "redirect:/books/seeBook/"+b.getId();
	}
	
	@GetMapping("updateBookForm")
	public String updateBookForm(@RequestParam int bookId, Model m) {
		Book b=bs.getBook(bookId);
		
		
		m.addAttribute("book", b);
		m.addAttribute("genreList", as.getAllGenres());
		m.addAttribute("authorList", as.getAllAuthors());
		m.addAttribute("checker", checker);
		return "admin/updateBook";
	}
	
	@PostMapping("updateBook")
	public String updateBook(@RequestParam String title,
			@RequestParam String description,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date issueDate,
			@RequestParam int authorId,
			@RequestParam int price,
			@RequestParam int[] genres,
			@RequestParam MultipartFile picture,
			@RequestParam int bookId
			){
		
		as.updateBook(title, description, issueDate, authorId, genres, picture, bookId, price);
		
		return "redirect:/books/seeBook/"+bookId;
	}
	
	@GetMapping("getGenres")
	public String getGenres(Model m) {
		m.addAttribute("genreList", as.getAllGenres());
		return "admin/genresPage";
	}
	
	@PostMapping("addGenre")
	public String addGenre(@RequestParam("genreName") String name) {
		as.addGenre(name);
		return "redirect:getGenres";
	}
	
	@PostMapping("deleteGenre")
	public String deleteGenre(@RequestParam("genreId") int id) {
		as.deleteGenre(id);
		return "redirect:getGenres";
	}
	
	@GetMapping("getAuthors")
	public String getAuthors(Model m) {
		m.addAttribute("authorList", as.getAllAuthors());
		return "admin/authorsPage";
	}
	
	@PostMapping("addAuthor")
	public String addAuthor(@RequestParam("authorName") String name, 
			@RequestParam("authorSurname") String surname) {
		as.addAuthor(name, surname);
		return "redirect:getAuthors";
	}
	
	@PostMapping("deleteAuthor")
	public String deleteAuthor(@RequestParam("authorId") int id) {
		as.deleteAuthor(id);
		return "redirect:getAuthors";
	}
}
