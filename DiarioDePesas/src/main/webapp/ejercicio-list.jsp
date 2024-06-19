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
		        <c:choose>
		            <c:when test="${ejercicio.tipoEjercicio == 'POR_REPETICION'}">
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
			      <div class="d-flex justify-content-between align-items-center w-100">
	   			      <p class="media-body pb-3 pl-3 mb-0 small lh-125 border-bottom border-gray">
				        <strong class="d-block text-gray-dark"><c:if test="${ejercicio.tipoEjercicio == 'POR_REPETICION'}"><c:out value="${res.pesoSoportado}" /> Kg. (Series: <c:out value="${res.cantSeries}" />, Repeticiones: <c:out value="${res.cantRepeticiones}" />)</c:if> <c:if test="${ejercicio.tipoEjercicio == 'POR_TIEMPO'}"><c:out value="${res.tiempoMinutos}" /> min.</c:if></strong>
				        Registrado el: <fmt:formatDate value="${res.fechaHoraCargaResultado}" pattern="dd/MM/yyyy HH:mm:ss" />
				      </p>
				      <div>
	   			          <a href="?accion=editResultado&idEjercicio=<c:out value="${ejercicio.id_ejercicio}" />&fechaHoraCargaResultado=<c:out value="${res.fechaHoraCargaResultado}" />">Editar</a>
       					  <a href="javascript:void(0);" style="margin-left: 16px" onclick="openDeleteResultadoModal('<c:out value="${res.idUsuario}"/>', '<c:out value="${res.fechaHoraCargaResultado}"/>')">Eliminar</a>
   			          </div>
			      </div>
			    </div>
			</c:forEach>
	  	</div>
   	<p><a class="btn btn-primary btn-lg" href="?accion=newResultado&idEjercicio=<c:out value="${ejercicio.id_ejercicio}" />" role="button">Agregar un nuevo resultado »</a></p>
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