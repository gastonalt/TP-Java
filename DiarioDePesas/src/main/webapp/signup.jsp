<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Crear cuenta - DiarioDePesas</title>
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
				<form action="<%=request.getContextPath()%>/signup" method="post">
				<caption>
					<h2>
            			Crear usuario
					</h2>
				</caption>
				<c:if test="${errorMensaje != null}">
					<div class="alert alert-danger" role="alert">
					  <c:out value='${errorMensaje}' />
					</div>
				</c:if>
				
				<c:if test="${exitoMensaje != null}">
					<div class="alert alert-success" role="alert">
					  <c:out value='${exitoMensaje}' />
					</div>
				</c:if>
				<c:if test="${exitoMensaje == null}">
					<fieldset class="form-group">
						<label>Nombre de usuario</label> <input type="text"
							class="form-control"
							name="username" required="required">
	
						<label>Contraseña</label> <input type="password"
							class="form-control"
							name="password" required="required">
	
						<label>Repetir contraseña</label> <input type="password"
							class="form-control"
							name="repassword" required="required">
	
						<label>Nombre</label> <input type="text"
							class="form-control"
							name="nombre" required="required">
	
						<label>Apellido</label> <input type="text"
							class="form-control"
							name="apellido" required="required">
	
						<label>Fecha de nacimiento</label> <input type="date"
							data-date-format="dd/mm/yyyy"
							class="form-control"
							name="fechaNac" required="required">
	
						<label>Localidad</label><select name="localidad" class="form-control">
						  <option  selected >Seleccione una localidad</option>
							<c:forEach var="loc" items="${localidadesList}">
							  <option value="<c:out value="${loc.cod_postal}" />"><c:out value="${loc.nombre}" /></option>
							</c:forEach>
						</select>
	
						<label>Direccion</label> <input type="text"
							class="form-control"
							name="direccion" required="required">
	
						<label>Email</label> <input type="text"
							class="form-control"
							name="email" required="required">
							
					</fieldset>
					<button type="submit" class="btn btn-success">Crear</button>
				</c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>