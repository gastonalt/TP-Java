package com.gastonalt.diariodepesas.web;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.EjercicioDao;
import com.gastonalt.diariodepesas.dao.ResultadoDao;
import com.gastonalt.diariodepesas.model.Ejercicio;
import com.gastonalt.diariodepesas.model.Resultado;
import com.gastonalt.diariodepesas.model.Usuario;

@WebServlet("/resultados")
public class ResultadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EjercicioDao ejercicioDao;
	ResultadoDao resultadoDao;

    public ResultadoServlet() {
        ejercicioDao = new EjercicioDao();
        resultadoDao = new ResultadoDao();
    }
    
    String resultadoForm = "resultado-form.jsp";

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
			case "delete":
				deleteResultado(request, response);
				break;
			case "edit":
				showEditResultadoForm(request, response);
				break;
			case "update":
				updateResultado(request, response);
				break;
			case "new":
				showNewResultadoForm(request, response);
				break;
			case "insert":
				insertResultado(request, response);
				break;
			default:
		        response.sendRedirect(request.getContextPath());
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
