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
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<jsp:include page="template/navigation.jsp" />
</head>
<body>
	<h2>Shopping Cart</h2>

	<c:choose>
		<c:when test="${not empty cartBooks}">
			<c:forEach items="${cartBooks}" var="entry">
				<c:set var="help" value="${entry.key}" scope="session" />
				<div class="card mt-3">
					<jsp:include page="template/book.jsp">
						<jsp:param name="bookTitle" value="${help.title}" />
						<jsp:param name="width" value="${80}" />
						<jsp:param name="height" value="${160}" />
						<jsp:param name="pic" value="${encoder.encode(help.picture)}" />
						<jsp:param name="bookDescription" value="${help.description}" />
						<jsp:param name="bookIssueDate" value="${help.issueDate}" />
						<jsp:param name="bookAuthorName" value="${help.author.name}" />
						<jsp:param name="bookAuthorSurname" value="${help.author.surname}" />
					</jsp:include>
					
					<p>Count: ${entry.value }</p>
					<form action="${pageContext.request.contextPath}/cart/delete"
						method="get">
						<input type="hidden" name="bookId" value="${entry.key.id}">
						<button type="submit" class="btn btn-danger">Remove from
							Cart</button>
					</form>

					<a
						href="${pageContext.request.contextPath}/books/seeBook/${entry.key.id}"
						class="btn btn-primary">View Book</a>
				</div>
			</c:forEach>
			<br><br>
			<div class="d-flex justify-content-center">
    <a href="${pageContext.request.contextPath}/cart/buy" class="btn btn-success">
        <strong>Buy Books</strong>
    </a>
</div>
		</c:when>
		<c:otherwise>
			Your cart is empty!
		</c:otherwise>
	</c:choose>


	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
		integrity="sha384-d2nMPHC8XhD0aIqbJyhYNcbY9ESc/XSQpxqRe+Ae6pZLSmo4G1FuI6O5Hz9TMb50"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+Wy8E+NUZl6a4NBn5f6bYIepFjELb8Q9Vd6"
		crossorigin="anonymous"></script>

</body>
</html>