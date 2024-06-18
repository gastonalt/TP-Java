<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.gastonalt.diariodepesas.model.Usuario"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    if (session.getAttribute("usuario")  == null) {
        response.sendRedirect(request.getContextPath());
    }
%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
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
	      <span>
      		<i class="fa-solid fa-weight-hanging" style="margin-right: 4px"></i>
        	<c:if test="${lastPeso != null}">          
	      		<c:out value='${lastPeso.getPeso()}'/> Kg (última vez: <c:out value='${lastPeso.getFecha_peso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))}'/>)
      		</c:if>
    		<c:if test="${lastPeso == null}">
    			Aún no tienes pesos cargados.
      		</c:if>
	      </span>
	      <button class="btn btn-outline-primary" data-toggle="modal" data-target="#exampleModal" style="transform: translateY(-8px)" href="#"><i style="margin-right: 4px" class="fa-solid fa-plus"></i>Agregar un nuevo peso</button>
      </div>
      <!-- Modal nuevo-peso -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Agregar un nuevo peso</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
				<form action="" method="post">
			      <div class="modal-body">
						<label>Nuevo peso:</label>
						<div class="input-group mb-3">
							<input type="number" class="form-control" name="newPeso" required="required" placeholder="<c:out value='${lastPeso.getPeso()}' />">
								<div class="input-group-append">
									<span class="input-group-text">Kg.</span>
								</div>
						</div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
			        <button type="submit" class="btn btn-primary">Agregar</button>
			      </div>
		      </form>
		    </div>
		  </div>
		</div>
      <!-- Final modal nuevo-peso -->
    <c:if test="${ejercicios == null}">
		<h2 style="margin-top: 80px; color: #8dabe8;">¡Parece que no tienes ejercicios cargados aún!</h2>
	    <div class="noEjerciciosFoundBackdrop"></div>
    </c:if>
    <c:if test="${ejercicios != null}">

		<div class="my-3 p-3 bg-white rounded shadow-sm">
	    <h6 class="border-bottom border-gray pb-2 mb-0">Ejercicios</h6>
    		<c:forEach var="ejer" items="${ejercicios}">
		    	<div class="media text-muted pt-3">
			      <svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"></rect><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>
			      <div class="d-flex justify-content-between align-items-center w-100">
	   			      <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
				        <strong class="d-block text-gray-dark"><c:out value="${ejer.descripcion}" /></strong>
				        <c:if test="${ejer.tipoEjercicio == 'POR_REPETICION'}"><b><c:out value="${ejer.ultimoResultado.pesoSoportado}" /> Kg.</b></c:if> <c:if test="${ejer.tipoEjercicio == 'POR_TIEMPO'}"><b><c:out value="${ejer.ultimoResultado.tiempoMinutos}" /> min.</b></c:if> - Registrado el: <fmt:formatDate value="${ejer.ultimoResultado.fechaHoraCargaResultado}" pattern="dd/MM/yyyy HH:mm:ss" />
				      </p>
   			          <a href="./ejercicios?accion=edit&idEjercicio=<c:out value="${ejer.id_ejercicio}" />">Modificar ejercicio</a>
			          <a href="./ejercicios?idEjercicio=<c:out value="${ejer.id_ejercicio}" />" style="margin-left: 16px">Agregar nuevo resultado</a>
			      </div>
			    </div>
			</c:forEach>
	  	</div>
    </c:if>
   	<p><a class="btn btn-primary btn-lg" href="./ejercicios?accion=new" role="button">Agregar un nuevo ejercicio »</a></p>
</div>

</body>
</html>
