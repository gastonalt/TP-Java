<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.gastonalt.diariodepesas.model.Usuario"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<%
	if (session.getAttribute("usuario")  == null) {
	    response.sendRedirect(request.getContextPath() + "/index.jsp");
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

<div class="container">
      <h1 class="display-3" style="margin-top: 40px">Bienvenido, <b><c:out value='${usuario.getNombre()}' /></b></h1>
      <div style="display: flex; justify-content: space-between">
	      <span><i class="fa-solid fa-weight-hanging" style="margin-right: 4px"></i> 64 Kg (última vez: 23/04/2024)</span>
	      <button class="btn btn-outline-primary" data-toggle="modal" data-target="#exampleModal" style="transform: translateY(-8px)" href="#"><i style="margin-right: 4px" class="fa-solid fa-plus"></i>Agregar un nuevo peso</button>
      </div>
      <!-- Modal nuevo-peso -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        ...
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary">Save changes</button>
		      </div>
		    </div>
		  </div>
		</div>
      <!-- Final modal nuevo-peso -->
      <h2 style="margin-top: 80px; color: #8dabe8;">¡Parece que no tienes ejercicios cargados aún!</h2>
      <div class="noEjerciciosFoundBackdrop"></div>
      <p><a class="btn btn-primary btn-lg" href="#" role="button">Agregar un ejercicio »</a></p>
</div>

</body>
</html>
