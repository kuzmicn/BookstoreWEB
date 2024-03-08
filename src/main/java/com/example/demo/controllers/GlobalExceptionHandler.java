package com.example.demo.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=RuntimeException.class)
	public String handleError(RuntimeException e, Model m) {
		m.addAttribute("msg", e.getMessage());
		return "errorPage";
	}
	
}
