package com.gastonalt.diariodepesas.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.UsuarioDao;
import com.gastonalt.diariodepesas.model.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDao usuarioDao;

    public LoginServlet() {
		usuarioDao = new UsuarioDao();
    }
    
    String login="login.jsp";
    String index="index.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioLogged = (Usuario) request.getSession().getAttribute("usuario");
	    if (usuarioLogged != null) {
	        response.sendRedirect(request.getContextPath() + "/dashboard");
	        return;
	    }
		RequestDispatcher dispatcher = request.getRequestDispatcher(login);
		dispatcher.forward(request, response);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username").toLowerCase();
        String password = request.getParameter("password");

        if (!usuarioDao.checkUserCredentials(username, password)) {
            request.setAttribute("credencialesIncorrectas", "Credenciales incorrectas.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            Usuario usuarioLogueado = usuarioDao.selectUsuarioByUsername(username);
            if (usuarioLogueado != null) {
                getServletContext().log("Usuario logueado: " + usuarioLogueado.getUsername());
                request.getSession().setAttribute("usuario", usuarioLogueado);
            } else {
                getServletContext().log("Error: No se pudo encontrar el usuario después de la autenticación.");
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
