<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book search</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<jsp:include page="template/navigation.jsp" />
</head>
<body>
	<div class="container mt-5 ">
		<form action="${pageContext.request.contextPath}/books/search"
			method="get" class="mb-3">
			<input type="hidden" name="newReq" value="1"> <label
				for="genres">Genres:</label> <select name="genres" multiple
				class="form-control">
				<c:forEach items="${genres}" var="genre">
					<option value="${genre.id}"
						<c:if test="${genresFilter != null && checker.check2(genre.id, genresFilter)}">selected</c:if>>${genre.name}</option>
				</c:forEach>
			</select>
			<div class="form-group">
				<label for="authors">Authors:</label> <select id="authors"
					name="authors" multiple class="form-control">
					<c:forEach items="${authors}" var="author">
						<option value="${author.id}"
							<c:if test="${authorsFilter != null && checker.check2(author.id, authorsFilter)}">selected</c:if>>${author.name}
							${author.surname}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="title">Title:</label> <input type="text" id="title"
					name="title" class="form-control"
					<c:if test="${title!=null }">value="${title}" </c:if>>
			</div>
			<div class="form-group">
				<label>Sort by Price:</label>
				<div class="form-check">
					<input type="radio" id="sortAscending" name="sortAscendingPrice"
						value="true"
						<c:if test="${sortAscending!=null and sortAscending}">checked</c:if>
						class="form-check-input"> <label for="sortAscending"
						class="form-check-label">Ascending</label>
				</div>
				<div class="form-check">
					<input type="radio" id="sortDescending" name="sortAscendingPrice"
						value="false"
						<c:if test="${sortAscending==null or not sortAscending}">checked</c:if>
						class="form-check-input"> <label for="sortDescending"
						class="form-check-label">Descending</label>
				</div>
			</div>

			<button type="submit" class="btn btn-primary " name="pageNum"
				value="0">Apply Filters</button>
		</form>
	</div>
	<c:if test="${!empty page.getContent()}">
	<div class="container mt-5 ">
		<c:forEach items="${page.getContent()}" var="book">
			<div class="card mt-3">
				<c:set var="help" value="${book}" scope="session" />
				<jsp:include page="template/book.jsp">
					<jsp:param name="bookTitle" value="${book.title}" />
					<jsp:param name="width" value="${75}" />
					<jsp:param name="height" value="${150}" />
					<jsp:param name="pic" value="${encoder.encode(book.picture)}" />
					<jsp:param name="bookDescription" value="${book.description}" />
					<jsp:param name="bookIssueDate" value="${book.issueDate}" />
					<jsp:param name="bookAuthorName" value="${book.author.name}" />
					<jsp:param name="bookAuthorSurname" value="${book.author.surname}" />
				</jsp:include>
				<a
					href="${pageContext.request.contextPath}/books/seeBook/${book.id}"
					class="btn btn-primary">View Book</a>
				<sec:authorize access="hasRole('ADMIN')">
					<a
						href="${pageContext.request.contextPath}/admin/updateBookForm?bookId=${book.id}"
						class="btn btn-danger">Update book</a>
				</sec:authorize>
				<sec:authorize access="hasRole('USER')">
					<form
						action="${pageContext.request.contextPath}/user/addToFavourites"
						method="get">
						<input type="hidden" name="bookId" value="${book.id}">
						<button type="submit" class="btn btn-primary">Add to
							favourites</button>
					</form>
					<form action="${pageContext.request.contextPath}/cart/addToCart"
						method="get">
						<input type="hidden" name="bookId" value="${book.id}">
						<button type="submit" class="btn btn-danger">Add to cart</button>
					</form>
				</sec:authorize>
			</div>
		</c:forEach>
		</div>
<br>
		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<c:if test="${page.number > 0}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/books/search?pageNum=${page.number-1}">Previous</a>
					</li>
				</c:if>
				<c:forEach begin="0" end="${page.totalPages - 1}" varStatus="i">
					<li
						class="page-item <c:if test="${i.index eq page.number}">active</c:if>">
						<a class="page-link"
						href="${pageContext.request.contextPath}/books/search?pageNum=${i.index}">${i.index + 1}</a>
					</li>
				</c:forEach>
				<c:if test="${page.number < page.totalPages - 1}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/books/search?pageNum=${page.number+1}">Next</a>
					</li>
				</c:if>
			</ul>
		</nav>
	</c:if>


	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+Wy8E+NUZl6a4NBn5f6bYIepFjELb8Q9Vd6"
		crossorigin="anonymous"></script>
</body>
</html>