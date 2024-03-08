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
<title>Admin books</title>
<!-- Bootstrap CSS CDN link (you can replace it with a local copy if preferred) -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<jsp:include page="/template/navigation.jsp" />
</head>
<body>
	<div class="container mt-5">
		<form action="${pageContext.request.contextPath}/admin/salesReport"
			method="post">
			<button type="submit" class="btn btn-danger">Generate sales
				report!</button>
		</form>
		<br>
		<h2 class="mb-4">Add Book</h2>

		<!-- Form to Add Book -->
		<form action="${pageContext.request.contextPath}/admin/addBook"
			method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">Title:</label> <input type="text"
					class="form-control" id="title" name="title" required />
			</div>
			<div class="form-group">
				<label for="description">Description:</label>
				<textarea class="form-control" id="description" name="description"
					rows="3" required></textarea>
			</div>
			<div class="form-group">
				<label for="issueDate">Issue Date:</label> <input type="date"
					class="form-control" id="issueDate" name="issueDate" required />
			</div>
			<div class="form-group">
				<label for="authorId">Author:</label> <select class="form-control"
					id="authorId" name="authorId" required>
					<c:forEach items="${authorList}" var="author">
						<option value="${author.id}">${author.name}
							${author.surname}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="genres">Genres:</label> <select class="form-control"
					id="genres" name="genres" multiple required>
					<c:forEach items="${genreList}" var="genre">
						<option value="${genre.id}">${genre.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="picture">Picture (Max Size: 64KB):</label> <input
					type="file" class="form-control-file" id="picture" name="picture"
					accept="image/*" required />
			</div>
			<div class="form-group">
				<label for="price">Price:</label> <input type="number"
					class="form-control" id="price" name="price" required />
			</div>
			<button type="submit" class="btn btn-primary">Add Book</button>
		</form>
	</div>
	<br>
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