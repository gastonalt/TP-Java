<%@page import="com.gastonalt.diariodepesas.model.Usuario"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario != null) {
        boolean isAdmin = usuario.isAdmin();
        getServletContext().log("Usuario en sesión: " + usuario.getUsername());
%>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01"
                aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
            <a class="navbar-brand" href="./">DiarioDePesas</a>
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" href="./">Home</a>
                </li>
                <% if (isAdmin) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="./localidades">Localidades</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./user?accion=showAllUsers">Usuarios</a>
                    </li>
                <% } %>
                    <li class="nav-item">
                        <a class="nav-link" href="./graph?key=peso">Evolución de peso</a>
                    </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <a class="btn my-2 my-sm-0" href="<%= request.getContextPath() %>/user">@<%= usuario.getUsername() %></a>
                <a class="btn btn-dark my-2 my-sm-0" href="./logout">Cerrar sesión</a>
            </form>
        </div>
    </nav>
</header>
<%
    } else { // usuario == null
%>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01"
                aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
            <a class="navbar-brand" href="./">DiarioDePesas</a>
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" href="./">Home</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <a class="btn my-2 my-sm-0" href="./login">Iniciar sesión</a>
                <a class="btn btn-dark my-2 my-sm-0" href="./signup">Crear una nueva cuenta</a>
            </form>
        </div>
    </nav>
</header>
<%
    }
%>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
