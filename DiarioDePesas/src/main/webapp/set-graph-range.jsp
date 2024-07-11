<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>DiarioDePesas</title>
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
				<form action="<%=request.getContextPath()%>/graph?key=showAllPesos" method="post">
				<caption>
					<h2>
            			Ver progreso de usuarios
					</h2>
				</caption>
					<fieldset class="form-group">
						<label>Fecha inicio</label> <input type="date"
							class="form-control"
							name="fechaInicio" required="required">
	
						<label>Fecha fin</label> <input type="date"
							class="form-control"
							name="fechaFin" required="required">
					</fieldset>
					<button type="submit" class="btn btn-primary">Generar gráfica</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>