package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.BookService;
import com.example.demo.services.PictureEncoderService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import model.Book;
import model.User;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	BookService bs;
	
	@Autowired
	UserService us;
	
	@Autowired
	PictureEncoderService pes;
	
	@GetMapping("getUserFavourites/{userId}")
	public String getUserFavourites(@PathVariable("userId") int id, Model m, HttpServletRequest req) {
		//Isto filtrirati
		User curr = us.getUser(req.getUserPrincipal().getName());
		User user=us.getUser(id);
		m.addAttribute("user", user);
		m.addAttribute("encoder", pes);
		m.addAttribute("id", curr.getId());
		return "favourites";
	}
	
	@GetMapping("/getFavourites")
	public String getFavourites(HttpServletRequest req) {
		User u = us.getUser(req.getUserPrincipal().getName());
		req.setAttribute("user", u);
		req.setAttribute("encoder", pes);
		req.setAttribute("id", u.getId());
		return "favourites";
	}
	
	@GetMapping("/deleteFromFavourites")
	public String deleteFromFavourites(@RequestParam("bookId") int id, HttpServletRequest req) {
		Book b=bs.getBook(id);
		User u=us.getUser(req.getUserPrincipal().getName());
		us.deleteFromFavourites(u, b);
		return "redirect:getFavourites";
	}
	
	@GetMapping("/addToFavourites")
	public String addToFavourites(@RequestParam("bookId") int id, HttpServletRequest req) {
		Book b=bs.getBook(id);
		User u=us.getUser(req.getUserPrincipal().getName());
		us.addToFavourites(u, b);
		return "goBackPage";
	}
}
