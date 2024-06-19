<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    if (session.getAttribute("usuario")  != null) {
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
%>
<html>
<head>
<title>DiarioDePesas</title>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<style>
	.video-background {
        position: absolute;
        right: 0;
        bottom: 0;
        min-width: 100%;
        min-height: 100%;
        width: auto;
        height: auto;
        z-index: -1;
        background: url('placeholder.jpg') no-repeat;
        background-size: cover;
    }
    .cover-container {
        position: relative;
        z-index: 1;
    }
    .bg-light{
    	background: transparent !important;
    }
    .navbar-brand{
    	color: #fff !important;
    	text-shadow: 2px 2px 12px #000;
    }
    a{
    	color: #fff !important;
    	text-shadow: 2px 2px 12px #000;
    }
</style>
</head>
<body class="text-center" cz-shortcut-listen="true">
  <div class="cover-container d-flex w-100 h-100 mx-auto flex-column">
	<video autoplay muted loop class="video-background">
       <source src="assets/background-video.mp4" type="video/mp4">
       Tu navegador no soporta videos.
    </video>
	<%@ include file="includes/header.jsp" %>
  <main role="main" class="inner cover">
    <h1 class="cover-heading text-light" style="margin-top: 30vh; text-shadow: 2px 2px 16px #222;">Todo tu progreso en un solo lugar</h1>
    <p class="lead text-light" style="text-shadow: 2px 2px 16px #222;">Crea una cuenta y comienza a seguir tus avances hoy mismo.</p>
    <p class="lead">
      <a href="./signup" class="btn btn-lg btn-dark">Crear cuenta ></a>
    </p>
  </main>

  <footer class="mastfoot mt-auto">
    <div class="inner text-light">
      <p>Trabajo práctico para <a href="https://github.com/utnfrrojava/javaClases/tree/master/tp" class="text-light" style="text-decoration: underline;">JAVA</a>, creado por <a href="https://github.com/gastonalt/TP-Java" class="text-light" style="text-decoration: underline;">@gastonalt</a>.</p>
    </div>
  </footer>
</div>


</body>
</html>
