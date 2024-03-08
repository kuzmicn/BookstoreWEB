<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <jsp:include page="/template/navigation.jsp" />
    <title>Thank you!</title>
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="jumbotron text-center">
            <h1 class="display-4">Thank you for buying!</h1>
            <hr class="my-4">

            <c:choose>
                <c:when test="${!empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/cart/buy-download" method="post">
                        <button type="submit" class="btn btn-primary btn-lg">Download Bill</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Bootstrap JS and Popper.js CDN links (required for Bootstrap) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
            integrity="sha384-d2nMPHC8XhD0aIqbJyhYNcbY9ESc/XSQpxqRe+Ae6pZLSmo4G1FuI6O5Hz9TMb50"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+Wy8E+NUZl6a4NBn5f6bYIepFjELb8Q9Vd6"
            crossorigin="anonymous"></script>
</body>
</html>