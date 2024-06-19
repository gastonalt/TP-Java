<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Lista de localidades - DiarioDePesas</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<%@ include file="includes/header.jsp" %>
	<br>

	<div class="row">

		<div class="container">
			<h3 class="text-center">Lista de Localidades</h3>
			<hr>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/localidades?accion=new" class="btn btn-primary">Agregar nueva localidad</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Cod. postal</th>
						<th>Nombre</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach var="loc" items="${localidadesList}">

						<tr>
							<td><c:out value="${loc.cod_postal}" /></td>
							<td><c:out value="${loc.nombre}" /></td>
							<td><a href="<%=request.getContextPath()%>/localidades?accion=edit&cod_postal=<c:out value='${loc.cod_postal}' />">Editar</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="<%=request.getContextPath()%>/localidades?accion=delete&cod_postal=<c:out value='${loc.cod_postal}' />">Eliminar</a></td>
						</tr>
					</c:forEach>
		
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>