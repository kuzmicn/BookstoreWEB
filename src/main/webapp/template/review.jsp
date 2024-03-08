<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="card mt-3">
	<div class="card-body">
		<h5 class="card-title">
			Review by <a
				href="${pageContext.request.contextPath}/user/getUserFavourites/${param.id}">${param.username}
			</a>
		</h5>
		<p class="card-text">Grade: ${param.grade}</p>
		<p class="card-text">Comment: ${param.comment}</p>
	</div>
</div>