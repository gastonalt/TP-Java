<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    if (session.getAttribute("usuario")  != null) {
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
%>
<html>
<head>
<title>DiarioDePesas</title>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
    <%@ include file="includes/header.jsp" %>

	<div class="container-fluid hero d-flex align-items-center">
	    <div class="container text-center text-white">
	        <h1 class="display-4">Bienvenido a DiarioDePesas</h1>
	        <p class="lead">Una app para seguir tus mejoras diarias</p>
	        <a href="./signup" class="btn btn-dark btn-lg">Crear cuenta</a>
	    </div>
	</div>

</body>
</html>
