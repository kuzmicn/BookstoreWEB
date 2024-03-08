<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>Register</title>
<sec:authorize access="hasRole('ADMIN')">
	<jsp:include page="template/navigation.jsp" />
</sec:authorize>
<!-- Bootstrap CSS CDN link (you can replace it with a local copy if preferred) -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>

	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<h3 class="text-center">Register</h3>
					</div>
					<div class="card-body">
						<form action="register-confirm" method="post">
							<div class="form-group">
								<label for="username">Username</label> <input type="text"
									class="form-control" id="username" name="username" required>
							</div>
							<div class="form-group">
								<label for="password">Password</label> <input type="password"
									class="form-control" id="password" name="password" required>
							</div>
							<div class="form-group">
								<label for="email">E-mail</label> <input type="email"
									class="form-control" id="email" name="email" required>
							</div>

							<div class="form-group">
								<label for="name">Name</label> <input type="text"
									class="form-control" id="name" name="name" required>
							</div>

							<div class="form-group">
								<label for="surname">Surname</label> <input type="text"
									class="form-control" id="surname" name="surname" required>
							</div>
							<button type="submit" class="btn btn-primary btn-block">Register</button>
						</form>
					</div>
					<sec:authorize access="!isAuthenticated()">
						<div class="card-footer">
							<p>You already have an account?</p>
							<a href="${pageContext.request.contextPath}/login"
								class="btn btn-primary">Login</a>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
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
