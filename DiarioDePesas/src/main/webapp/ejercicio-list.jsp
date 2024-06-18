<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ejercicio: <c:out value="${ejercicio.descripcion}" /> - DiarioDePesa</title>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <br/>
	<div class="container">
       <a href="<%=request.getContextPath()%>/dashboard" class="my-3 p-3">< Todos los ejercicios</a>
		<div class="my-3 p-3 bg-white rounded shadow-sm">
	    <h6 class="border-bottom border-gray pb-2 mb-0"><c:out value="${ejercicio.descripcion}" /></h6>
            <c:set var="resultadoSize" value="${fn:length(resultados)}"/>
    		<c:forEach var="res" items="${resultados}">
		    	<div class="media text-muted pt-3">
			      <svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 32x32"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"></rect><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>
			      <div class="d-flex justify-content-between align-items-center w-100">
	   			      <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
				        <strong class="d-block text-gray-dark"><c:if test="${ejercicio.tipoEjercicio == 'POR_REPETICION'}"><c:out value="${res.pesoSoportado}" /> Kg.</c:if> <c:if test="${ejercicio.tipoEjercicio == 'POR_TIEMPO'}"><c:out value="${res.tiempoMinutos}" /> min.</c:if></strong>
				        Registrado el: <fmt:formatDate value="${res.fechaHoraCargaResultado}" pattern="dd/MM/yyyy HH:mm:ss" />
				      </p>
				      <div>
	   			          <a href="#">Editar</a>
       					  <a href="javascript:void(0);" style="margin-left: 16px" onclick="openDeleteResultadoModal('<c:out value="${res.idUsuario}"/>', '<c:out value="${res.fechaHoraCargaResultado}"/>')">Eliminar</a>
   			          </div>
			      </div>
			    </div>
			</c:forEach>
	  	</div>
   	<p><a class="btn btn-primary btn-lg" href="?idEjercicio=" role="button">Agregar un nuevo resultado »</a></p>
   	<button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#deleteModal">Eliminar ejercicio</button>
 	      <!-- Modal eliminar-ejercicio -->
		<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Eliminar ejercicio</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
				<form action="?accion=delete&idEjercicio=<c:out value="${ejercicio.id_ejercicio}" />" method="post">
			      <div class="modal-body">
						Está seguro que desea eliminar el ejercicio y todos sus pesos? Esta acción no puede deshacerse.
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
			        <button type="submit" class="btn btn-danger">Eliminar modal</button>
			      </div>
		      </form>
		    </div>
		  </div>
		</div>
      <!-- Final modal eliminar-ejercicio  -->
      <!-- Modal eliminar-resultado -->
	<div class="modal fade" id="deleteResultadoModal" tabindex="-1" role="dialog" aria-labelledby="deleteResultadoLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="deleteResultadoLabel">Eliminar resultado</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <form action="?accion=deleteResultado" method="post">
	        <div class="modal-body">
	          ¿Está seguro que desea eliminar este resultado? Esta acción no puede deshacerse.<br>
           	  <c:if test="${resultadoSize == 1}"><b>Atención! Al eliminar este peso también eliminará el ejercicio.</b></c:if>
	          <input type="hidden" id="deleteUsuarioId" name="id_usuario">
	          <input type="hidden" id="deleteFechaHora" name="fechaHoraCargaResultado">
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
	          <button type="submit" class="btn btn-danger">Eliminar</button>
	        </div>
	      </form>
	    </div>
	  </div>
	</div>
	<!-- Final modal eliminar-resultado -->
	      
</div>
<script>
  function openDeleteResultadoModal(idUsuario, fechaHoraCargaResultado) {
    document.getElementById('deleteUsuarioId').value = idUsuario;
    document.getElementById('deleteFechaHora').value = fechaHoraCargaResultado;
    $('#deleteResultadoModal').modal('show');
  }
</script>

</body>
</html>