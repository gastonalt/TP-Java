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
     <a href="<%=request.getContextPath()%>/dashboard" class="my-3 p-3">< Todos los ejercicios</a>
		<h2>
		<c:if test="${ejercicio != null}">
         			Editar ejercicio
         		</c:if>
		<c:if test="${ejercicio == null}">
         			Agregar nuevo ejercicio
         		</c:if>
		</h2>
	<c:if test="${ejercicio == null}">
    	<form action="?accion=insert" method="post">
   	</c:if>
	<c:if test="${ejercicio != null}">
    	<form action="?accion=update" method="post">
   	</c:if>
			<c:if test="${ejercicio != null}">
				<input type="hidden" name="idEjercicio" value="<c:out value='${ejercicio.id_ejercicio}' />" />
			</c:if>
            <div class="form-group">
                <label for="tiempoMinutos">Nombre del ejercicio</label>
                <input type="text" class="form-control" required="required" value="<c:out value='${ejercicio.descripcion}' />" name="descripcion">
            </div>
           	<c:if test="${ejercicio == null}">
		        <div class="form-group">
		            <label for="tipoEjercicio">Tipo de Ejercicio</label>
		            <select class="form-control" id="tipoEjercicio" required="required" name="tipoEjercicio" onchange="toggleFields()">
		                <option value="">Seleccione...</option>
		                <option value="POR_TIEMPO">Por Tiempo</option>
		                <option value="POR_REPETICION">Por Repetición</option>
		            </select>
		        </div>
		        <div id="porTiempoFields" class="d-none">
		            <div class="form-group">
		                <label for="tiempoMinutos">Tiempo (Minutos)</label>
		                <input type="number" class="form-control" id="tiempoMinutos" name="tiempoMinutos">
		            </div>
		        </div>
		        <div id="porRepeticionFields" class="d-none">
		            <div class="form-group">
		                <label for="cantSeries">Cantidad de Series</label>
		                <input type="number" class="form-control" id="cantSeries" name="cantSeries">
		            </div>
		            <div class="form-group">
		                <label for="cantRepeticiones">Cantidad de Repeticiones</label>
		                <input type="number" class="form-control" id="cantRepeticiones" name="cantRepeticiones">
		            </div>
		            <div class="form-group">
		                <label for="pesoSoportado">Peso Soportado (kg)</label>
		                <input type="number" class="form-control" id="pesoSoportado" name="pesoSoportado">
		            </div>
		        </div>
        </c:if>
        <button type="submit" class="btn btn-primary">Guardar</button>
    </form>
</div>
<!-- Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
function toggleFields() {
	const tipoEjercicio = document.getElementById('tipoEjercicio').value;
    const porTiempoFields = document.getElementById('porTiempoFields');
    const porRepeticionFields = document.getElementById('porRepeticionFields');
    porTiempoFields.classList.add('d-none');
    porRepeticionFields.classList.add('d-none');
    document.querySelectorAll('#porTiempoFields input, #porRepeticionFields input').forEach(input => {
        input.removeAttribute('required');
    });
    if (tipoEjercicio === 'POR_TIEMPO') {
        porTiempoFields.classList.remove('d-none');
        document.getElementById('tiempoMinutos').setAttribute('required', 'required');
    } else if (tipoEjercicio === 'POR_REPETICION') {
        porRepeticionFields.classList.remove('d-none');
        document.getElementById('cantSeries').setAttribute('required', 'required');
        document.getElementById('cantRepeticiones').setAttribute('required', 'required');
        document.getElementById('pesoSoportado').setAttribute('required', 'required');
    }
}
</script>
</body>
</html>
