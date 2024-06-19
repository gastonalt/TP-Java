<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<%
	if (session.getAttribute("usuario")  == null) {
	    response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<title>Crear ejercicio - DiarioDePesas</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="css/styles.css">
	<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<%@ include file="includes/header.jsp" %>
	<br>
	<div class="container">
     <a href="<%=request.getContextPath()%>/ejercicios?idEjercicio=<c:out value='${ejercicio.id_ejercicio}' />" class="my-3 p-3">< <c:out value='${ejercicio.descripcion}' /></a>
		<h2>
		<c:if test="${ejercicio != null}">
         			Editar resultado para ejercicio: <c:out value='${ejercicio.descripcion}' />
         		</c:if>
		<c:if test="${ejercicio == null}">
         			Agregar nuevo resultado para ejercicio: <c:out value='${ejercicio.descripcion}' />
         		</c:if>
		</h2>
	<c:if test="${resultado == null}">
    	<form action="?accion=insertResultado" method="post">
   	</c:if>
	<c:if test="${resultado != null}">
    	<form action="?accion=updateResultado" method="post">
   	</c:if>
			<input type="hidden" name="idEjercicio" value="<c:out value='${ejercicio.id_ejercicio}' />" />
			<c:if test="${resultado != null}">
				<input type="hidden" name="fechaHoraCargaResultado" value="<c:out value='${resultado.fechaHoraCargaResultado}' />" />
			</c:if>
           		<c:if test="${ejercicio.tipoEjercicio == 'POR_TIEMPO'}">
				    <div id="porTiempoFields">
				        <div class="form-group">
				            <label for="tiempoMinutos">Tiempo (Minutos)</label>
				            <input type="number" class="form-control" id="tiempoMinutos" value="<c:out value='${resultado.tiempoMinutos}' />" name="tiempoMinutos">
				        </div>
				    </div>
				</c:if>
				
				<c:if test="${ejercicio.tipoEjercicio == 'POR_REPETICION'}">
				    <div id="porRepeticionFields">
				        <div class="form-group">
				            <label for="cantSeries">Cantidad de Series</label>
				            <input type="number" class="form-control" id="cantSeries" value="<c:out value='${resultado.cantSeries}' />" name="cantSeries">
				        </div>
				        <div class="form-group">
				            <label for="cantRepeticiones">Cantidad de Repeticiones</label>
				            <input type="number" class="form-control" id="cantRepeticiones" value="<c:out value='${resultado.cantRepeticiones}' />" name="cantRepeticiones">
				        </div>
				        <div class="form-group">
				            <label for="pesoSoportado">Peso Soportado (kg)</label>
				            <input type="number" class="form-control" id="pesoSoportado" value="<c:out value='${resultado.pesoSoportado}' />" name="pesoSoportado">
				        </div>
				    </div>
				</c:if>
	        <button type="submit" class="btn btn-primary">Guardar</button>
    </form>
</div>
<!-- Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
