package com.gastonalt.diariodepesas.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.LocalidadDao;
import com.gastonalt.diariodepesas.dao.UsuarioDao;
import com.gastonalt.diariodepesas.model.Ejercicio;
import com.gastonalt.diariodepesas.model.Localidad;
import com.gastonalt.diariodepesas.model.Resultado;
import com.gastonalt.diariodepesas.model.Usuario;

@WebServlet("/user")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDao usuarioDao;
	private LocalidadDao localidadDao;

    public UsuarioServlet() {
    	localidadDao = new LocalidadDao();
        usuarioDao = new UsuarioDao();
    }
    
    String signup = "signup.jsp";
    String list = "usuario-list.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("accion");
		if(action == null) {
			action = "";
		}
		try {
			switch (action) {
			case "showAllUsers":
				showAllUsers(request, response);
				break;
			default:
				signUp(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		} catch (IOException e) {
	        e.printStackTrace();
	        throw new ServletException("IO Error", e);
	    } catch (ServletException e) {
	        e.printStackTrace();
	        throw new ServletException("Servlet Error", e);
	    }
	}
	
	private void showAllUsers(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		Usuario usuarioLogged = (Usuario) request.getSession().getAttribute("usuario");
		if(usuarioLogged == null) {
	        response.sendRedirect(request.getContextPath());
	        return;
		}
	    if (!usuarioLogged.isAdmin()) {
	        response.sendRedirect(request.getContextPath());
	        return;
	    }
		List<Usuario> usuarios = usuarioDao.selectAllUsuarios();
		RequestDispatcher dispatcher = request.getRequestDispatcher(list);
		request.setAttribute("usuarios", usuarios);
		dispatcher.forward(request, response);
	}

	private void signUp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Localidad> localidadesList = localidadDao.selectAllLocalidades();
		request.setAttribute("localidadesList", localidadesList);
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		RequestDispatcher dispatcher = request.getRequestDispatcher(signup);
		request.setAttribute("usuario", usuario);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		int id_usuario = usuario.getId_usuario();
		String username = request.getParameter("username");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		Date fechaNac = Date.valueOf(request.getParameter("fechaNac"));
		int localidad = Integer.parseInt(request.getParameter("localidad"));
		String direccion = request.getParameter("direccion");
		String email = request.getParameter("email");
		
		Usuario usuarioUpdated = new Usuario(id_usuario,username,nombre,apellido,fechaNac,direccion,email,new Localidad(localidad, null));
		try {
			usuarioDao.updateUsuario(usuarioUpdated);
	        request.setAttribute("exitoMensaje", "Usuario editado con éxito.");
	        request.setAttribute("volverLink", "dashboard");
			RequestDispatcher dispatcher = request.getRequestDispatcher(signup);
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
