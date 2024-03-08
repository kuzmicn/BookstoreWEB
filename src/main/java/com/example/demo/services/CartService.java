package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.BillRepo;
import com.example.demo.repositories.OrderedBookRepo;

import model.Bill;
import model.Book;
import model.OrderedBook;
import model.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class CartService {
	@Autowired
	BillRepo br;
	
	@Autowired
	OrderedBookRepo or;
	
	@Autowired
	ReportService rs;
	
	@Autowired
	EmailService es;
	
	public byte[] buy(Map<Book, Integer> books, User u) {
		if(books==null || books.isEmpty())
			throw new RuntimeException("You can not buy empty cart!");
		Bill bill=new Bill();
		bill.setIssueDate(new Date());
		bill.setUser(u);
		
		bill=br.saveAndFlush(bill);//Da bi racun bio vidljiv kad budem pravio orderedbooks
				
		List<OrderedBook> orderedBooks=new ArrayList<OrderedBook>();
		
		for(Entry<Book, Integer> pair:books.entrySet()) {
			Book b=pair.getKey();
			Integer count=pair.getValue();
			OrderedBook ob=new OrderedBook();
			Integer price=b.getPrice();
			
			ob.setBill(bill);
			ob.setBook(b);
			ob.setCount(count);
			ob.setPrice(price);
			
			ob=or.save(ob);
			
			orderedBooks.add(ob);
		}
		
		
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("username", u.getUsername());
		byte[] report=null;
		try {
			report=rs.generateReport("/jasperreports/bill.jrxml", params, 
					new JRBeanCollectionDataSource(orderedBooks));
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
		
		 String mail=u.getEmail();
		  
		 if(mail!=null) 
			 es.sendEmailWithAttachment(mail,"emailsendernoreply123@gmail.com", 
					 "New bill!", "Greetings!\nPlease find your bill attached.", report, "bill");
		 
		
		return report;
	}
	
	public void addToCart(Map<Book, Integer> books, Book b) {
		b.getBookGenres();
		Integer number=books.get(b);
		if(number==null)
			books.put(b, 1);
		else
			books.put(b, number+1);
	}
	
	public void deleteFromCart(Map<Book, Integer> books, Book b) {
		if(books==null)
			throw new RuntimeException("The cart is already empty!");
		
		Integer number=books.get(b);	
		
		if(number==null)
			throw new RuntimeException("The book you wanted to remove from cart is not there!");
		
		if(number!=1)
			books.put(b, number-1);
		else
			books.remove(b);
	}
}
