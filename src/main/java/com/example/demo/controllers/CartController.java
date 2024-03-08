package com.example.demo.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.BookService;
import com.example.demo.services.CartService;
import com.example.demo.services.PictureEncoderService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import model.User;

@Controller
@RequestMapping("/cart/")
public class CartController {
	@Autowired
	BookService bs;
	
	@Autowired
	CartService cs;
	
	@Autowired
	PictureEncoderService pes;
	
	@Autowired
	UserService us;
	
	@GetMapping("/")
	public String getCart(Model m) {
		m.addAttribute("encoder", pes);
		return "cart";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("delete")
	public String deleteFromCart(@RequestParam("bookId") int id, HttpServletRequest req) {
		Map<Book, Integer> books= (Map<Book, Integer>) req.getSession().getAttribute("cartBooks");
		Book b=bs.getBook(id);
		cs.deleteFromCart(books, b);
		req.getSession().setAttribute("cartBooks", books);//potencijalno nepotrebno
		return "redirect:";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("buy")
	public String buy(HttpServletRequest req, HttpServletResponse response) {
		Map<Book, Integer> books=(Map<Book, Integer>) req.getSession().getAttribute("cartBooks");
		User u=us.getUser(req.getUserPrincipal().getName());
		byte[] bill=cs.buy(books, u);
		
		req.getSession().removeAttribute("cartBooks");
		
		if(bill==null)
			req.setAttribute("error", "There has been an error while creating your bill, "
					+ "if you want your copy please contact the customer support!");
		else {
			req.getSession().setAttribute("bill", bill);
		}
		return "thanksForBuying";
	}
	
	@PostMapping("/buy-download")
	public void buyDownload(HttpServletRequest req, HttpServletResponse response) throws IOException {
		byte[] report = (byte[]) req.getSession().getAttribute("bill");
		if(report==null)
			throw new RuntimeException("There is no bill to download!");
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=bill.pdf");
		response.setContentLength(report.length);
		response.getOutputStream().write(report);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		req.getSession().removeAttribute("bill");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("addToCart")
	public String addToCart(@RequestParam("bookId") int id, HttpServletRequest req) {
		Book b=bs.getBook(id);
		Map<Book, Integer> books=(Map<Book, Integer>) req.getSession().getAttribute("cartBooks");
		
		if(books==null)
			books= new HashMap<Book, Integer>();
		
		cs.addToCart(books, b);
		req.getSession().setAttribute("cartBooks", books);
		
		return "goBackPage";
	}
}
