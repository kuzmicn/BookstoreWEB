package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.services.BookService;
import com.example.demo.services.CheckerService;
import com.example.demo.services.PictureEncoderService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import model.Book;
import model.User;

@Controller
@RequestMapping("/books/")
public class BookController {
	@Autowired
	BookService bs;

	@Autowired
	UserService us;

	@Autowired
	PictureEncoderService pes;

	@Autowired
	CheckerService cs;

	@GetMapping("seeBook/{book}")
	public String seeBook(@PathVariable("book") int id, Model m) {
		// Book info, reviews and options for add to cart or favourites through link
		Book book = bs.getBook(id);
		m.addAttribute("book", book);
		m.addAttribute("encoder", pes);
		ReviewDTO dto = new ReviewDTO();
		dto.setGrade(5);
		m.addAttribute("reviewDTO", dto);
		return "bookPage";
	}

	@PostMapping("leaveReview")
	public String leaveReview(HttpServletRequest req, ReviewDTO rev) {
		User u = us.getUser(req.getUserPrincipal().getName());
		bs.addReview(rev, u);
		return "redirect:/books/seeBook/" + rev.getBookId();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("search")
	public String getBooks(@RequestParam(required = false) Integer newReq,
			@RequestParam(required = false) List<Integer> genres, @RequestParam(required = false) List<Integer> authors,
			@RequestParam(required = false) String title, @RequestParam(required = false) Boolean sortAscendingPrice,
			@RequestParam(required = false) Integer pageNum, HttpServletRequest req) {

		if (newReq == null) {// newReq is null if you did not apply new filter
			if (pageNum == null)// If accesed through next page it wont be null
				pageNum = (Integer) req.getSession().getAttribute("pageNumberSess");
			genres = (List<Integer>) req.getSession().getAttribute("genresFilter");
			authors = (List<Integer>) req.getSession().getAttribute("authorsFilter");
			title = (String) req.getSession().getAttribute("title");
			sortAscendingPrice = (Boolean) req.getSession().getAttribute("sortAscending");
		}

		if (pageNum == null)
			pageNum = 0;

		Sort sort = Sort.by("price");
		if (sortAscendingPrice == null || !sortAscendingPrice)
			sort = sort.descending();

		Pageable p = PageRequest.of(pageNum, 3, sort);
		Page<Book> page = bs.getFilteredBooks(genres, authors, title, p);

		// Update current params in session
		if (authors != null)
			req.getSession().setAttribute("authorsFilter", authors);
		else
			req.getSession().removeAttribute("authorsFilter");

		if (genres != null)
			req.getSession().setAttribute("genresFilter", genres);
		else
			req.getSession().removeAttribute("genresFilter");

		if (title != null)
			req.getSession().setAttribute("title", title);
		else
			req.getSession().removeAttribute("title");

		req.getSession().setAttribute("sortAscending", sortAscendingPrice);
		req.getSession().setAttribute("pageNumberSess", pageNum);

		req.setAttribute("encoder", pes);
		req.setAttribute("genres", bs.getGenres());
		req.setAttribute("authors", bs.getAuthors());
		req.setAttribute("checker", cs);
		req.setAttribute("page", page);

		return "booksSearch";
	}

}
