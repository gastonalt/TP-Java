<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List"%>
<%@ page import="com.gastonalt.diariodepesas.utils.DateUtils" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Listado de usuarios - DiarioDePesa</title>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <br/>
   	<div class="row">

	<div class="container">
		<h3 class="text-center">Lista de Usuarios</h3>
   <table class="table table-striped table-sm">
    <thead>
        <tr>
            <th>#</th>
            <th>Username</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Fecha de Nacimiento</th>
            <th>Dirección</th>
            <th>Email</th>
            <th>Localidad</th>
        </tr>
    </thead>
    <tbody>
        <% 
            List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
            if (usuarios != null && !usuarios.isEmpty()) {
                int count = 1;
                for (Usuario usr : usuarios) {
                    %>
                    <tr>
                        <td><%= count++ %></td>
                        <td><%= usr.getUsername() %></td>
                        <td><%= usr.getNombre() %></td>
                        <td><%= usr.getApellido() %></td>
						<td><%= usr.getFechaNacimiento() != null ? DateUtils.formatDate(usr.getFechaNacimiento(), "dd/MM/yyyy") : "" %></td>
                        <td><%= usr.getDireccion() %></td>
                        <td><%= usr.getEmail() %></td>
                        <td><%= usr.getLocalidad() != null ? usr.getLocalidad().getNombre() : "" %></td>
                    </tr>
                    <%
                }
            } else {
                %>
                <tr>
                    <td colspan="8">No hay usuarios cargados.</td>
                </tr>
                <%
		            }
		        %>
    	</tbody>
	</table>
   </div>
   </div>
</body>
</html>