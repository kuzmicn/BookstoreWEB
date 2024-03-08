<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Favourite books</title>
<!-- Bootstrap CSS CDN link (you can replace it with a local copy if preferred) -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<jsp:include page="template/navigation.jsp" />
</head>
<body>

	<div class="container mt-5">
		<c:if test="${!empty user}">
			<c:choose>
				<c:when test="${id!=user.id }">
					<h2>Favourite books of user: ${user.username}</h2>
				</c:when>
				<c:otherwise>
					<h2>${user.username}, your favourite books are:</h2>
				</c:otherwise>
			</c:choose>

			<c:forEach items="${user.favouriteBooks}" var="fav">
				<c:set var="help" value="${fav.book}" scope="session" />
				<div class="card mt-3">
					<jsp:include page="template/book.jsp">
						<jsp:param name="bookTitle" value="${fav.book.title}" />
						<jsp:param name="width" value="${50}" />
						<jsp:param name="height" value="${100}" />
						<jsp:param name="pic" value="${encoder.encode(fav.book.picture)}" />
						<jsp:param name="bookDescription" value="${fav.book.description}" />
						<jsp:param name="bookIssueDate" value="${fav.book.issueDate}" />
						<jsp:param name="bookAuthorName" value="${fav.book.author.name}" />
						<jsp:param name="bookAuthorSurname" value="${fav.book.author.surname}" />
					</jsp:include>
					<c:if test="${id==user.id }">
						<form
							action="${pageContext.request.contextPath}/user/deleteFromFavourites"
							method="get">
							<input type="hidden" name="bookId" value="${fav.book.id}">
							<button type="submit" class="btn btn-danger">Remove from
								Favourites</button>
						</form>
					</c:if>
					<a
						href="${pageContext.request.contextPath}/books/seeBook/${fav.book.id}"
						class="btn btn-primary">View Book</a>
				</div>
			</c:forEach>
		</c:if>
	</div>

	<!-- Bootstrap JS and Popper.js CDN links (required for Bootstrap) -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+Wy8E+NUZl6a4NBn5f6bYIepFjELb8Q9Vd6"
        crossorigin="anonymous"></script>

</body>
</html>