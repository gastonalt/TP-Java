<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Agregar/Editar localidad - DiarioDePesas</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<%@ include file="includes/header.jsp" %>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${localidad != null}">
					<form action="?accion=update" method="post">
				</c:if>
				<c:if test="${localidad == null}">
					<form action="?accion=insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${localidad != null}">
            			Editar Localidad
            		</c:if>
						<c:if test="${localidad == null}">
            			Agregar nueva Localidad
            		</c:if>
					</h2>
				</caption>
				<c:if test="${localidad != null}">
					<input type="hidden" name="cod_postal_anterior" value="<c:out value='${localidad.cod_postal}' />" />
				</c:if>
				<fieldset class="form-group">
					<label>Cod. Postal</label> <input type="text"
						value="<c:out value='${localidad.cod_postal}' />" class="form-control"
						name="cod_postal" required="required">
						
					<label>Nombre</label> <input type="text"
						value="<c:out value='${localidad.nombre}' />" class="form-control"
						name="nombre" required="required">
				</fieldset>
				<button type="submit" class="btn btn-success">Guardar</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>