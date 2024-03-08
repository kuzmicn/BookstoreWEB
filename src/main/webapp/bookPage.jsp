<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<jsp:include page="template/navigation.jsp" />
<meta charset="UTF-8">
<title>Book</title>
</head>
<body>
	<div class="container mt-5">
		<button class="btn btn-primary go-back-btn" onclick="history.back()">Go
			Back</button>
		<br>
		<c:set var="help" value="${book}" scope="session" />
		<jsp:include page="template/book.jsp">
			<jsp:param name="bookTitle" value="${book.title}" />
			<jsp:param name="width" value="${150}" />
			<jsp:param name="height" value="${300}" />
			<jsp:param name="pic" value="${encoder.encode(book.picture)}" />
			<jsp:param name="bookDescription" value="${book.description}" />
			<jsp:param name="bookIssueDate" value="${book.issueDate}" />
			<jsp:param name="bookAuthorName" value="${book.author.name}" />
			<jsp:param name="bookAuthorSurname" value="${book.author.surname}" />
		</jsp:include>
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
	<br>
	<sec:authorize access="hasRole('USER')">
		<div class="container mt-5">
			<h2>Leave a Review</h2>

			<form:form
				action="${pageContext.request.contextPath}/books/leaveReview"
				method="post" modelAttribute="reviewDTO">
				<div class="form-group">
					<label for="comment">Comment:</label>
					<form:textarea path="comment" id="comment" class="form-control"
						rows="3" required="true" />
				</div>

				<div class="form-group">
					<label for="grade">Grade:</label>
					<form:input path="grade" id="grade" class="form-control"
						type="number" min="1" max="5" required="true" />
				</div>

				<div class="form-group">
					<form:hidden path="bookId" value="${book.id }" />
				</div>

				<button type="submit" class="btn btn-primary">Submit Review</button>
			</form:form>
		</div>
	</sec:authorize>

	<br>
	<c:forEach items="${book.reviews}" var="rev">
		<jsp:include page="template/review.jsp">
			<jsp:param name="grade" value="${rev.grade}" />
			<jsp:param name="comment" value="${rev.comment}" />
			<jsp:param name="id" value="${rev.user.id}" />
			<jsp:param name="username" value="${rev.user.username}" />
		</jsp:include>
	</c:forEach>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+Wy8E+NUZl6a4NBn5f6bYIepFjELb8Q9Vd6"
		crossorigin="anonymous"></script>
</body>
</html>