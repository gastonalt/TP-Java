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
		        <c:choose>
		            <c:when test="${ejer.tipoEjercicio == 'POR_REPETICION'}">
						<svg fill="#000" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" 
							 width="32" height="32" viewBox="0 0 45.174 45.174"
							 xml:space="preserve">
						<g>
							<path d="M41.573,15.879v-1.675c0-1.922-1.558-3.495-3.479-3.495h-2.761c-1.921,0-3.479,1.573-3.479,3.495v4.693H13.319v-4.693
								c0-1.922-1.558-3.495-3.479-3.495H7.079c-1.921,0-3.479,1.573-3.479,3.495v1.675c-1.979,0-3.6,1.617-3.6,3.613v6.172
								c0,1.996,1.71,3.613,3.6,3.613v1.674c0,1.922,1.558,3.513,3.479,3.513H9.84c1.921,0,3.479-1.591,3.479-3.513v-4.676h18.536v4.676
								c0,1.922,1.558,3.513,3.479,3.513h2.762c1.92,0,3.479-1.591,3.479-3.513v-1.674c1.979,0,3.6-1.619,3.6-3.613v-6.172
								C45.172,17.496,43.462,15.879,41.573,15.879z"/>
						</g>
						</svg>
		            </c:when>
		            <c:when test="${ejer.tipoEjercicio == 'POR_TIEMPO'}">
						<svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
						<path d="M12 14V11M12 6C7.85786 6 4.5 9.35786 4.5 13.5C4.5 17.6421 7.85786 21 12 21C16.1421 21 19.5 17.6421 19.5 13.5C19.5 11.5561 18.7605 9.78494 17.5474 8.4525M12 6C14.1982 6 16.1756 6.94572 17.5474 8.4525M12 6V3M19.5 6.5L17.5474 8.4525M12 3H9M12 3H15" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
						</svg>
		            </c:when>
		        </c:choose>
					<div class="d-flex flex-column flex-lg-row justify-content-between align-items-start w-100">
					  <p class="media-body pb-3 pl-3 mb-0 small lh-125 border-bottom border-gray">
					    <strong class="d-block text-gray-dark"><c:out value="${ejer.descripcion}" /></strong>
					    <c:if test="${ejer.tipoEjercicio == 'POR_REPETICION'}">
					      <b><c:out value="${ejer.ultimoResultado.pesoSoportado}" /> Kg.</b>
					    </c:if> 
					    <c:if test="${ejer.tipoEjercicio == 'POR_TIEMPO'}">
					      <b><c:out value="${ejer.ultimoResultado.tiempoMinutos}" /> min.</b>
					    </c:if>
					    - Registrado el: 
					    <fmt:formatDate value="${ejer.ultimoResultado.fechaHoraCargaResultado}" pattern="dd/MM/yyyy HH:mm:ss" />
					  </p>
					
					  <!-- Enlaces -->
					  <div class="d-flex flex-column flex-lg-row mt-2 mt-lg-0">
					    <a href="./graph?key=ejercicio&idEjercicio=<c:out value='${ejer.id_ejercicio}' />" class="mb-4 mt-2 mr-2 mb-lg-0">
					      Rendimiento
					    </a>
					    <a href="./ejercicios?accion=edit&idEjercicio=<c:out value='${ejer.id_ejercicio}' />" class="mb-4 mt-2 mr-2 mb-lg-0">
					      Modificar ejercicio
					    </a>
					    <a href="./ejercicios?idEjercicio=<c:out value='${ejer.id_ejercicio}' />" class="mb-4 mt-2 mr-2 mb-lg-0">
					      Agregar nuevo resultado
					    </a>
					  </div>
					</div>
			    </div>
			</c:forEach>
	  	</div>
    </c:if>
   	<p><a class="btn btn-primary btn-lg" href="./ejercicios?accion=new" role="button">Agregar un nuevo ejercicio »</a></p>
</div>

</body>
</html>
