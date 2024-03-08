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
<title>Genres Page</title>
<!-- Bootstrap CSS CDN link (you can replace it with a local copy if preferred) -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<jsp:include page="/template/navigation.jsp" />
</head>
<body>
	<div class="container mt-5">
		<h2 class="mb-4">Genres Page</h2>

		<!-- Form to Add Genre -->
		<form action="${pageContext.request.contextPath}/admin/addGenre"
			method="post" class="mb-4">
			<div class="form-group">
				<label for="genreName">Genre Name:</label> <input type="text"
					class="form-control" id="genreName" name="genreName" required />
			</div>
			<button type="submit" class="btn btn-primary">Add Genre</button>
		</form>

		<!-- Form to Delete Genre -->
		<form action="${pageContext.request.contextPath}/admin/deleteGenre"
			method="post">
			<div class="form-group">
				<label for="genreId">Select Genre to Delete:</label> <select
					class="form-control" id="genreId" name="genreId">
					<c:forEach items="${genreList}" var="genre">
						<option value="${genre.id}">${genre.name}</option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" class="btn btn-danger">Delete Genre</button>
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