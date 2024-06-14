package com.gastonalt.diariodepesas.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.LocalidadDao;
import com.gastonalt.diariodepesas.model.Localidad;

import javax.servlet.ServletConfig;

@WebServlet("/localidades")
public class LocalidadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LocalidadDao localidadDao;

	public void init(ServletConfig config) throws ServletException {
		localidadDao = new LocalidadDao();
	}
	
	public LocalidadServlet() {
		super();
	}
	
    String form="localidad-form.jsp";
    String list="localidades-list.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("accion");
		if(action == null) {
			action = "";
		}
		try {
			switch (action) {
			case "new":
				showNewForm(request, response);
				break;
			case "insert":
				insertLocalidad(request, response);
				break;
			case "delete":
				deleteLocalidad(request, response);
				break;
			case "update":
				updateLocalidad(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			default:
				listLocalidades(request, response);
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void listLocalidades(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Localidad> localidadesList = localidadDao.selectAllLocalidades();
		request.setAttribute("localidadesList", localidadesList);
		RequestDispatcher dispatcher = request.getRequestDispatcher(list);
		dispatcher.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(form);
		dispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int cod_postal = Integer.parseInt(request.getParameter("cod_postal"));
		Localidad localidadBD = localidadDao.selectLocalidad(cod_postal);
		RequestDispatcher dispatcher = request.getRequestDispatcher(form);
		request.setAttribute("localidad", localidadBD);
		dispatcher.forward(request, response);
	}
	
	private void insertLocalidad(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int cod_postal = Integer.parseInt(request.getParameter("cod_postal"));
		String nombre = request.getParameter("nombre");
		Localidad newLocalidad = new Localidad(cod_postal, nombre);
		localidadDao.insertLocalidad(newLocalidad);
		response.sendRedirect("LocalidadServlet?accion=list");
	}
	
	private void updateLocalidad(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int cod_postal_anterior = Integer.parseInt(request.getParameter("cod_postal_anterior"));
		int cod_postal = Integer.parseInt(request.getParameter("cod_postal"));
		String nombre = request.getParameter("nombre");
		
		System.out.println(cod_postal);
		System.out.println(nombre);

		Localidad localidadActualizada = new Localidad(cod_postal, nombre);
		localidadDao.updateLocalidad(localidadActualizada, cod_postal_anterior);
		response.sendRedirect("LocalidadServlet?accion=list");
	}

	private void deleteLocalidad(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int cod_postal = Integer.parseInt(request.getParameter("cod_postal"));
		localidadDao.deleteLocalidad(cod_postal);
		response.sendRedirect("LocalidadServlet?accion=list");
	}

}
