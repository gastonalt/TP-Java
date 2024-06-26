package com.gastonalt.diariodepesas.web;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.EjercicioDao;
import com.gastonalt.diariodepesas.dao.ResultadoDao;
import com.gastonalt.diariodepesas.model.Ejercicio;
import com.gastonalt.diariodepesas.model.Ejercicio.TipoEjercicio;
import com.gastonalt.diariodepesas.model.Localidad;
import com.gastonalt.diariodepesas.model.Resultado;
import com.gastonalt.diariodepesas.model.Usuario;

@WebServlet("/ejercicios")
public class EjercicioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EjercicioDao ejercicioDao;
	ResultadoDao resultadoDao;

    public EjercicioServlet() {
        ejercicioDao = new EjercicioDao();
        resultadoDao = new ResultadoDao();
    }
    
    String form = "ejercicio-form.jsp";
    String resultadoForm = "resultado-form.jsp";
    String list = "ejercicio-list.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	    if (usuario == null) {
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	        return;
	    }
		String action=request.getParameter("accion");
		if(action == null) {
			action = "";
		}
		String ejercicio =request.getParameter("idEjercicio");
		if(ejercicio == null) {
			ejercicio = "";
		}
		try {
			switch (action) {
			case "new":
				showNewForm(request, response);
				break;
			case "insert":
				insertEjercicio(request, response);
				break;
			case "delete":
				deleteEjercicio(request, response);
				break;
			case "update":
				updateEjercicio(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			////// MÉTODOS DE RESULTADO, MOVER A SU PROPIO SERVLET //////
			case "deleteResultado":
				deleteResultado(request, response);
				break;
			case "editResultado":
				showEditResultadoForm(request, response);
				break;
			case "updateResultado":
				updateResultado(request, response);
				break;
			case "newResultado":
				showNewResultadoForm(request, response);
				break;
			case "insertResultado":
				insertResultado(request, response);
				break;
			//////
			default:
				listEjercicio(request, response);
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
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(form);
		dispatcher.forward(request, response);
	}
	
	private void insertEjercicio(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		Resultado resultado;
		Usuario loggedUser = (Usuario) request.getSession().getAttribute("usuario");
		String descripcion = request.getParameter("descripcion");
		TipoEjercicio tipoEjercicio = TipoEjercicio.valueOf(request.getParameter("tipoEjercicio"));
		System.out.println(tipoEjercicio);
		Ejercicio ejercicio = new Ejercicio(descripcion, tipoEjercicio);
		Ejercicio ejercicioCargado = ejercicioDao.insertEjercicio(ejercicio);
		if(tipoEjercicio == Ejercicio.TipoEjercicio.POR_REPETICION) {
			int cantSeries = Integer.parseInt(request.getParameter("cantSeries"));
			int cantRepeticiones = Integer.parseInt(request.getParameter("cantRepeticiones"));
			Double pesoSoportado = Double.parseDouble(request.getParameter("pesoSoportado"));
			resultado = new Resultado(loggedUser.getId_usuario(), new Timestamp(new Date().getTime()) ,ejercicioCargado.getId_ejercicio(), cantSeries, cantRepeticiones, pesoSoportado);
		}else{
			int tiempoMinutos = Integer.parseInt(request.getParameter("tiempoMinutos"));
			resultado = new Resultado(loggedUser.getId_usuario(), new Timestamp(new Date().getTime()) ,ejercicioCargado.getId_ejercicio(), tiempoMinutos);
		}
		System.out.println(ejercicioCargado.getId_ejercicio());
		resultadoDao.insertResultado(resultado);
		response.sendRedirect("dashboard");
	}
	
	private void listEjercicio(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
	    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	    int idEjercicio = Integer.parseInt(request.getParameter("idEjercicio"));
	    List<Resultado> resultados = new ArrayList<Resultado>();
	    try {
	        resultados = resultadoDao.listResultados(usuario.getId_usuario(), idEjercicio);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    Ejercicio ejercicio = ejercicioDao.getEjercicioById_ejercicio(idEjercicio);
	    request.setAttribute("ejercicio", ejercicio);
	    request.setAttribute("resultados", resultados);
	    RequestDispatcher dispatcher = request.getRequestDispatcher(list);
	    dispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id_ejercicio = Integer.parseInt(request.getParameter("idEjercicio"));
		Ejercicio ejercicioBD = ejercicioDao.getEjercicioById_ejercicio(id_ejercicio);
		RequestDispatcher dispatcher = request.getRequestDispatcher(form);
		request.setAttribute("ejercicio", ejercicioBD);
		dispatcher.forward(request, response);
	}
	
	private void updateEjercicio(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id_ejercicio = Integer.parseInt(request.getParameter("idEjercicio"));
		String descripcion = request.getParameter("descripcion");
		
		ejercicioDao.updateEjercicio(id_ejercicio, descripcion);
		response.sendRedirect("./ejercicios?idEjercicio=" + id_ejercicio);
	}
	
	private void deleteEjercicio(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id_ejercicio = Integer.parseInt(request.getParameter("idEjercicio"));
		ejercicioDao.deleteEjercicio(id_ejercicio);
		response.sendRedirect("./dashboard");
	}
	
	////////////////////////////// MÉTODOS DE "RESULTADO" MOVER A SU PROPIO SERVLET //////////////////////////////
	
	private void deleteResultado(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
        String fechaHoraCargaResultado = request.getParameter("fechaHoraCargaResultado");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parsedDate = dateFormat.parse(fechaHoraCargaResultado);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            resultadoDao.deleteResultado(id_usuario, timestamp);
            response.sendRedirect("./dashboard");
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de fecha y hora incorrecto: " + fechaHoraCargaResultado, e);
        }
    }
	
	private void showEditResultadoForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        String fechaHoraCargaResultado = request.getParameter("fechaHoraCargaResultado");
        int id_ejercicio = Integer.parseInt(request.getParameter("idEjercicio"));

		Ejercicio ejercicioBD = ejercicioDao.getEjercicioById_ejercicio(id_ejercicio);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parsedDate = dateFormat.parse(fechaHoraCargaResultado);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
    		Resultado resultadoBD = resultadoDao.getOneResultado(usuario.getId_usuario(), timestamp);
    		RequestDispatcher dispatcher = request.getRequestDispatcher(resultadoForm);
    		request.setAttribute("ejercicio", ejercicioBD);
    		request.setAttribute("resultado", resultadoBD);
    		dispatcher.forward(request, response);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de fecha y hora incorrecto: " + fechaHoraCargaResultado, e);
        }
    }
	
	private void updateResultado(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Resultado resultado;
		int id_ejercicio = Integer.parseInt(request.getParameter("idEjercicio"));
		Usuario loggedUser = (Usuario) request.getSession().getAttribute("usuario");
        String fechaHoraCargaResultado = request.getParameter("fechaHoraCargaResultado");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate;
		try {
			parsedDate = dateFormat.parse(fechaHoraCargaResultado);
	        Timestamp timestamp = new Timestamp(parsedDate.getTime());
			Ejercicio ejercicioBD = ejercicioDao.getEjercicioById_ejercicio(id_ejercicio);
			if(ejercicioBD.getTipoEjercicio() == Ejercicio.TipoEjercicio.POR_REPETICION) {
				int cantSeries = Integer.parseInt(request.getParameter("cantSeries"));
				int cantRepeticiones = Integer.parseInt(request.getParameter("cantRepeticiones"));
				Double pesoSoportado = Double.parseDouble(request.getParameter("pesoSoportado"));
				resultado = new Resultado(loggedUser.getId_usuario(), timestamp ,ejercicioBD.getId_ejercicio(), cantSeries, cantRepeticiones, pesoSoportado);
			}else{
				int tiempoMinutos = Integer.parseInt(request.getParameter("tiempoMinutos"));
				resultado = new Resultado(loggedUser.getId_usuario(), timestamp ,ejercicioBD.getId_ejercicio(), tiempoMinutos);
			}
			resultadoDao.updateResultado(resultado);
			response.sendRedirect("./ejercicios?idEjercicio=" + id_ejercicio);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
	
	private void showNewResultadoForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id_ejercicio = Integer.parseInt(request.getParameter("idEjercicio"));
		Ejercicio ejercicioBD = ejercicioDao.getEjercicioById_ejercicio(id_ejercicio);
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultadoForm);
		request.setAttribute("ejercicio", ejercicioBD);
		dispatcher.forward(request, response);
    }
	
	private void insertResultado(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		Resultado resultado;
        int id_ejercicio = Integer.parseInt(request.getParameter("idEjercicio"));
		Usuario loggedUser = (Usuario) request.getSession().getAttribute("usuario");
		Ejercicio ejercicioBD = ejercicioDao.getEjercicioById_ejercicio(id_ejercicio);
		if(ejercicioBD.getTipoEjercicio() == Ejercicio.TipoEjercicio.POR_REPETICION) {
			int cantSeries = Integer.parseInt(request.getParameter("cantSeries"));
			int cantRepeticiones = Integer.parseInt(request.getParameter("cantRepeticiones"));
			Double pesoSoportado = Double.parseDouble(request.getParameter("pesoSoportado"));
			resultado = new Resultado(loggedUser.getId_usuario(), new Timestamp(new Date().getTime()) , id_ejercicio, cantSeries, cantRepeticiones, pesoSoportado);
		}else{
			int tiempoMinutos = Integer.parseInt(request.getParameter("tiempoMinutos"));
			resultado = new Resultado(loggedUser.getId_usuario(), new Timestamp(new Date().getTime()) , id_ejercicio, tiempoMinutos);
		}
		resultadoDao.insertResultado(resultado);
		response.sendRedirect("./ejercicios?idEjercicio=" + id_ejercicio);
	}

}
