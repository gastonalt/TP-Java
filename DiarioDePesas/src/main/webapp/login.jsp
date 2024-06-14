<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Iniciar sesión - DiarioDePesas</title>
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
				<form action="<%=request.getContextPath()%>/login" method="post">
				<caption>
					<h2>
            			Iniciar sesión
					</h2>
				</caption>
				<c:if test="${credencialesIncorrectas != null}">
					<div class="alert alert-danger" role="alert">
					  <c:out value='${credencialesIncorrectas}' />
					</div>
				</c:if>
					<fieldset class="form-group">
						<label>Nombre de usuario</label> <input type="text"
							class="form-control"
							name="username" required="required">
	
						<label>Contraseña</label> <input type="password"
							class="form-control"
							name="password" required="required">
					</fieldset>
					<button type="submit" class="btn btn-success">Iniciar sesión</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>