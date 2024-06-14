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
				<c:if test="${usuario == null}">
					<form action="<%=request.getContextPath()%>/signup" method="post">
	   			</c:if>
				<c:if test="${usuario != null}">
					<form action="<%=request.getContextPath()%>/user" method="post">
				</c:if>
				<caption>
					<h2>
  						<c:if test="${usuario == null}">
            			Crear usuario
            			</c:if>
  						<c:if test="${usuario != null}">
  						Editar perfil
  						</c:if>
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
					<c:if test="${volverLink != null}">
						<a href="<%=request.getContextPath()%>/<c:out value='${volverLink}' />">< Volver</a>
					</c:if>
					<c:if test="${inicarSesionLink != null}">
						<a href="<%=request.getContextPath()%>/<c:out value='${inicarSesionLink}' />">< Iniciar sesión</a>
					</c:if>
				</c:if>
				<c:if test="${exitoMensaje == null}">
					<fieldset class="form-group">
						<label>Nombre de usuario</label> <input type="text"
							class="form-control" value="<c:out value='${usuario.username}' />"
							name="username" required="required">
						<c:if test="${usuario == null}">
							<label>Contraseña</label> <input type="password"
								class="form-control"
								name="password" required="required">
		
							<label>Repetir contraseña</label> <input type="password"
								class="form-control"
								name="repassword" required="required">
						</c:if>
						<label>Nombre</label> <input type="text"
							class="form-control" value="<c:out value='${usuario.nombre}' />"
							name="nombre" required="required">
	
						<label>Apellido</label> <input type="text"
							class="form-control" value="<c:out value='${usuario.apellido}' />"
							name="apellido" required="required">
	
						<label>Fecha de nacimiento</label> <input type="date"
							data-date-format="dd/mm/yyyy"
							class="form-control" value="<c:out value='${usuario.fechaNacimiento}' />"
							name="fechaNac" required="required">
	
						<label>Localidad</label><select name="localidad" class="form-control">
	  						<c:if test="${usuario == null}">
						  		<option  selected >Seleccione una localidad</option>
            				</c:if>
	  						<c:if test="${usuario != null}">
						  		<option selected value="<c:out value="${usuario.getLocalidad().cod_postal}" />"><c:out value="${usuario.getLocalidad().nombre}" /></option>
            				</c:if>
							<c:forEach var="loc" items="${localidadesList}">
							  <option value="<c:out value="${loc.cod_postal}" />"><c:out value="${loc.nombre}" /></option>
							</c:forEach>
						</select>
	
						<label>Direccion</label> <input type="text"
							class="form-control" value="<c:out value='${usuario.direccion}' />"
							name="direccion" required="required">
	
						<label>Email</label> <input type="text"
							class="form-control" value="<c:out value='${usuario.email}' />"
							name="email" required="required">
							
					</fieldset>
					<button type="submit" class="btn btn-success">
 						<c:if test="${usuario == null}">
 						Crear cuenta
 						</c:if>
  						<c:if test="${usuario != null}">
  						Guardar perfil
  						</c:if>
					</button>
				</c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>