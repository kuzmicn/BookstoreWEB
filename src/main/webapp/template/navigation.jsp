<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand" href="${pageContext.request.contextPath}/">Bookstore</a>

	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav ml-auto">
			<sec:authorize access="hasRole('USER')">
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/user/getFavourites"><sec:authentication
							property="principal.user.username" />'s favourites</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/cart/">My cart</a></li>
			</sec:authorize>
			<sec:authorize access="hasRole('ADMIN')">
				<!-- Display admin link only if the user is an admin -->
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/admin/addAdmin">Add new admin</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/admin/getAuthors">Manage
						authors</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/admin/getGenres">Manage
						genres</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/admin/manageBooks">Manage
						books</a></li>
			</sec:authorize>

			<sec:authorize access="isAuthenticated()">
				<!-- Display "Search Books" and "Logout" links only if the user is authenticated -->

				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/books/search">Search
						books</a></li>

				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</sec:authorize>
		</ul>
	</div>
</nav>
