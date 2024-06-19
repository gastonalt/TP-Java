<%@page import="java.util.Arrays"%>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String tipoEjercicio = (String) request.getAttribute("tipoEjercicio");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value='${title}' /> - DiarioDePesas</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <br/>
   	<div class="row">
	    <div class="container">
          <a href="<%=request.getContextPath()%>/dashboard" class="my-3 p-3">< Volver</a>
	        <h1><c:out value='${title}' /></h1>
	        <c:if test="${mostrarGraph}">
	        	<canvas id="graficoProgreso"></canvas>
	        </c:if>
  	        <c:if test="${!mostrarGraph}">
	        	Aún no hay datos que mostrar. Cargá mas datos para visualizar el gráfico.
        	</c:if>
	    </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>

    var data = <%= Arrays.toString((double[]) request.getAttribute("pesos")) %>;
    var fechas = <%= Arrays.toString((String[]) request.getAttribute("fechas")) %>;
    var tipoEjercicio = "<%= tipoEjercicio %>";
    var unidad = tipoEjercicio === "POR_TIEMPO" ? "min" : "kg";
    
       var ctx = document.getElementById('graficoProgreso').getContext('2d');
       var myChart = new Chart(ctx, {
           type: 'line',
           data: {
               labels: fechas,
               datasets: [{
                   label: 'Progreso',
                   data: data
               }]
           },
           options: {
               scales: {
                   y: {
                       beginAtZero: true,
                       ticks: {
                           callback: function(value, index, values) {
                        	   return value + ' ' + unidad;
                           }
                       }
                   }
               },
               plugins: {
                   tooltip: {
                       callbacks: {
                           label: function(context) {
                               var label = context.dataset.label || '';
                               if (label) {
                                   label += ': ';
                               }
                               label += context.parsed.y + ' ' + unidad;
                               return label;
                           }
                       }
                   }
               }
           }
       });
    </script>
</body>
