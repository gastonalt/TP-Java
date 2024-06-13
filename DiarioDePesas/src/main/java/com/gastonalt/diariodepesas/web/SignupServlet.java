package com.gastonalt.diariodepesas.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.LocalidadDao;
import com.gastonalt.diariodepesas.dao.UsuarioDao;
import com.gastonalt.diariodepesas.model.Localidad;
import com.gastonalt.diariodepesas.model.Usuario;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LocalidadDao localidadDao;
	private UsuarioDao usuarioDao;

	public void init(ServletConfig config) throws ServletException {
		localidadDao = new LocalidadDao();
		usuarioDao = new UsuarioDao();
	}

    public SignupServlet() {
        super();
    }
    
    String signup="signup.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Localidad> localidadesList = localidadDao.selectAllLocalidades();
		request.setAttribute("localidadesList", localidadesList);
		RequestDispatcher dispatcher = request.getRequestDispatcher(signup);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		if(password.equals(repassword)) {
			String username = request.getParameter("username");
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			Date fechaNac = Date.valueOf(request.getParameter("fechaNac"));
			int localidad = Integer.parseInt(request.getParameter("localidad"));
			String direccion = request.getParameter("direccion");
			String email = request.getParameter("email");
			Usuario nuevoUsuario = new Usuario(username, password, nombre, apellido, fechaNac, direccion, email, new Localidad(localidad, null));
		    // Verificamos si el username ya está en uso
		    if (usuarioDao.existeUsername(username)) {
				List<Localidad> localidadesList = localidadDao.selectAllLocalidades();
				request.setAttribute("localidadesList", localidadesList);
		        request.setAttribute("errorMensaje", "El nombre de usuario ya está en uso.");
				RequestDispatcher dispatcher = request.getRequestDispatcher(signup);
				dispatcher.forward(request, response);
				return;
		    }
		    // Verificamos si el email ya está en uso
		    if (usuarioDao.existeEmail(email)) {
				List<Localidad> localidadesList = localidadDao.selectAllLocalidades();
				request.setAttribute("localidadesList", localidadesList);
		        request.setAttribute("errorMensaje", "El correo electrónico ya está en uso.");
				RequestDispatcher dispatcher = request.getRequestDispatcher(signup);
				dispatcher.forward(request, response);
				return;
		    }
			try {
				usuarioDao.insertUsuario(nuevoUsuario);
				List<Localidad> localidadesList = localidadDao.selectAllLocalidades();
				request.setAttribute("localidadesList", localidadesList);
		        request.setAttribute("exitoMensaje", "Usuario creado con éxito.");
				RequestDispatcher dispatcher = request.getRequestDispatcher(signup);
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}