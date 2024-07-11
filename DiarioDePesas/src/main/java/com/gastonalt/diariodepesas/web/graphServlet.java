package com.gastonalt.diariodepesas.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.EjercicioDao;
import com.gastonalt.diariodepesas.dao.HistorialPesoDao;
import com.gastonalt.diariodepesas.dao.ResultadoDao;
import com.gastonalt.diariodepesas.model.Ejercicio;
import com.gastonalt.diariodepesas.model.HistorialPeso;
import com.gastonalt.diariodepesas.model.Resultado;
import com.gastonalt.diariodepesas.model.Usuario;
import com.gastonalt.diariodepesas.model.UsuarioHistorialPesoDTO;
import com.gastonalt.diariodepesas.model.Ejercicio.TipoEjercicio;

@WebServlet("/graph")
public class graphServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HistorialPesoDao historialPesoDao;
	ResultadoDao resultadoDao;
	EjercicioDao ejercicioDao;

    public graphServlet() {
        super();
        this.historialPesoDao = new HistorialPesoDao();
        this.ejercicioDao = new EjercicioDao();
        this.resultadoDao = new ResultadoDao();
    }
    
    String graph = "graph.jsp";
    String aug = "all-users-graph.jsp";
    String sgr = "set-graph-range.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	    if (usuario == null) {
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	        return;
	    }
		String action=request.getParameter("key");
		if(action == null) {
			action = "";
		}
		try {
			switch (action) {
			case "peso":
				historialPeso(request, response);
				break;
			case "ejercicio":
				historialEjercicio(request, response);
				break;
			case "showAllPesos":
				historialAllPesos(request, response);
				break;
			case "setRangoPesos":
				setRangoPesos(request, response);
				break;
			default:
				// signUp(request, response);
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
	
	private void setRangoPesos(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		boolean isAdmin = ((Usuario) request.getSession().getAttribute("usuario")).isAdmin();
	    if (!isAdmin) {
	        response.sendRedirect(request.getContextPath());
	        return;
	    }
	    RequestDispatcher dispatcher = request.getRequestDispatcher(sgr);
	    dispatcher.forward(request, response);
	}
	
	private void historialAllPesos(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		boolean isAdmin = ((Usuario) request.getSession().getAttribute("usuario")).isAdmin();
	    if (!isAdmin) {
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	        return;
	    }
	    String fechaInicioStr = request.getParameter("fechaInicio");
	    String fechaFinStr = request.getParameter("fechaFin");
	    
	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    
	    LocalDate fechaInicioDate = LocalDate.parse(fechaInicioStr, inputFormatter);
	    LocalDate fechaFinDate = LocalDate.parse(fechaFinStr, inputFormatter);

	    LocalDateTime fechaInicio = fechaInicioDate.atStartOfDay();
	    LocalDateTime fechaFin = fechaFinDate.atTime(23, 59, 59);

	    String fechaInicioFormatted = fechaInicioDate.format(outputFormatter);
	    String fechaFinFormatted = fechaFinDate.format(outputFormatter);
	    
	    List<UsuarioHistorialPesoDTO> historialPesos = historialPesoDao.selectAllHistorialPesos(fechaInicio, fechaFin);
	    request.setAttribute("usuariosHistorial", historialPesos);
	    request.setAttribute("fechaInicioStr", fechaInicioFormatted);
	    request.setAttribute("fechaFinStr", fechaFinFormatted);
	    RequestDispatcher dispatcher = request.getRequestDispatcher(aug);
	    dispatcher.forward(request, response);
	}
	
	private void historialPeso(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		boolean mostrarGraph = true;
        Usuario loggedUser = (Usuario) request.getSession().getAttribute("usuario");
        List<HistorialPeso> historiaPesos = historialPesoDao.selectAllHistorialPeso(loggedUser.getId_usuario());
        
        double[] pesos = new double[historiaPesos.size()];
        String[] fechas = new String[historiaPesos.size()];
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (int i = 0; i < historiaPesos.size(); i++) {
            HistorialPeso historial = historiaPesos.get(i);
            pesos[i] = historial.getPeso().doubleValue();
            fechas[i] = "\"" +  historial.getFecha_peso().format(formatter).toString() + "\"";
        }
	    request.setAttribute("pesos", pesos);
	    request.setAttribute("fechas", fechas);
	    request.setAttribute("title", "Progreso de Peso");
	    if(historiaPesos.size() < 2) {
	    	mostrarGraph = false;
	    }
	    request.setAttribute("mostrarGraph", mostrarGraph);
	    RequestDispatcher dispatcher = request.getRequestDispatcher(graph);
	    dispatcher.forward(request, response);
	}
	
	private void historialEjercicio(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		boolean mostrarGraph = true;
	    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	    int idEjercicio = Integer.parseInt(request.getParameter("idEjercicio"));
	    List<Resultado> resultados = resultadoDao.listResultados(usuario.getId_usuario(), idEjercicio, "asc");
	    Ejercicio ejercicio = ejercicioDao.getEjercicioById_ejercicio(idEjercicio);
        
        double[] pesos = new double[resultados.size()];
        String[] fechas = new String[resultados.size()];
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (int i = 0; i < resultados.size(); i++) {
            Resultado resultado = resultados.get(i);
            if(ejercicio.getTipoEjercicio() == TipoEjercicio.POR_REPETICION) {
            	pesos[i] = resultado.getPesoSoportado().doubleValue();
            }else {
            	pesos[i] = resultado.getTiempoMinutos();
            }
            fechas[i] = "\"" +  resultado.getFechaHoraCargaResultado().toLocalDateTime().format(formatter).toString() + "\"";
        }
	    request.setAttribute("ejercicio", ejercicio);
	    request.setAttribute("tipoEjercicio", ejercicio.getTipoEjercicio().toString());
	    request.setAttribute("pesos", pesos);
	    request.setAttribute("fechas", fechas);
	    request.setAttribute("title", "Progreso de ejercicio: " + ejercicio.getDescripcion());
	    if(resultados.size() < 2) {
	    	mostrarGraph = false;
	    }
	    request.setAttribute("mostrarGraph", mostrarGraph);
	    RequestDispatcher dispatcher = request.getRequestDispatcher(graph);
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
