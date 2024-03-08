<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Authors Page</title>
<!-- Bootstrap CSS CDN link (you can replace it with a local copy if preferred) -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<jsp:include page="/template/navigation.jsp" />
</head>
<body>

	<div class="container mt-5">
		<h2 class="mb-4">Authors Page</h2>

		<!-- Form to Add Author -->
		<form action="${pageContext.request.contextPath}/admin/addAuthor"
			method="post" class="mb-4">
			<div class="form-group">
				<label for="authorName">Author Name:</label> <input type="text"
					class="form-control" id="authorName" name="authorName" required />
			</div>
			<div class="form-group">
				<label for="authorSurname">Author Surname:</label> <input
					type="text" class="form-control" id="authorSurname"
					name="authorSurname" required />
			</div>
			<button type="submit" class="btn btn-primary">Add Author</button>
		</form>

		<!-- Form to Delete Author -->
		<form action="${pageContext.request.contextPath}/admin/deleteAuthor"
			method="post">
			<div class="form-group">
				<label for="authorId">Select Author to Delete:</label> <select
					class="form-control" id="authorId" name="authorId">
					<c:forEach items="${authorList}" var="author">
						<option value="${author.id}">${author.name}
							${author.surname}</option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" class="btn btn-danger">Delete Author</button>
		</form>
	</div>
	<!-- Bootstrap JS and Popper.js CDN links (required for Bootstrap) -->
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