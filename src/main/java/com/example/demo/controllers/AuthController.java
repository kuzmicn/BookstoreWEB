package com.example.demo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class AuthController {
	@Autowired
	UserService us;

	@GetMapping("/login")
	public String login(HttpServletRequest req) {
		Principal p= req.getUserPrincipal();
		if(p!=null)
			throw new RuntimeException("You are already logged in!");
		return "login";
	}

	@GetMapping("/register")
	public String register(HttpServletRequest req) {
		Principal p= req.getUserPrincipal();
		if(p!=null)
			throw new RuntimeException("You are already logged in!");
		return "register";
	}

	@PostMapping("/register-confirm")
	public String register(@RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("name") String name,
			@RequestParam("surname") String surname, @RequestParam("email") String email,
			HttpServletRequest req) {
		Principal p= req.getUserPrincipal();
		if(p!=null)
			throw new RuntimeException("You are already logged in!");
		us.saveUser(username,password,email,name,surname, "USER");
		return "login";
	}
}
