<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="card-body">
	<h5 class="card-title">${param.bookTitle}</h5>
	<p class="card-image">
		<img alt="No picture" src="data:image/*;base64,${param.pic}"
			style="width: ${param.width}px; height: ${param.height}px;">
	</p>
	<p class="card-text">${param.bookDescription}</p>
	<p class="card-text">Price ${help.price} RSD</p>
	<p class="card-text">Issue Date: ${param.bookIssueDate}</p>
	<p class="card-text">Author: ${param.bookAuthorName}
		${param.bookAuthorSurname}</p>

	<p class="card-text">
		Genres:
		<c:forEach var="bookgenre" items="${help.bookGenres}">
            ${bookgenre.genre.name}&nbsp;
        </c:forEach>
	</p>
	<c:remove var="help" scope="session" />
</div>