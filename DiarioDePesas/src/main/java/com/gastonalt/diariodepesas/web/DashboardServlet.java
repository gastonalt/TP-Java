package com.gastonalt.diariodepesas.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gastonalt.diariodepesas.dao.EjercicioDao;
import com.gastonalt.diariodepesas.dao.HistorialPesoDao;
import com.gastonalt.diariodepesas.model.Ejercicio;
import com.gastonalt.diariodepesas.model.HistorialPeso;
import com.gastonalt.diariodepesas.model.Usuario;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HistorialPesoDao historialPesoDao;
	EjercicioDao ejercicioDao;

    public DashboardServlet() {
        super();
        historialPesoDao = new HistorialPesoDao();
        ejercicioDao = new EjercicioDao();
    }
    String dashboard = "dashboard.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		RequestDispatcher dispatcher = request.getRequestDispatcher(dashboard);
		List<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		if(usuario != null) {
			HistorialPeso lastPeso = historialPesoDao.selectLastHistorialPeso(usuario.getId_usuario());
			request.setAttribute("lastPeso", lastPeso);
			try {
				ejercicios = ejercicioDao.obtenerEjerciciosConUltimoResultado(usuario.getId_usuario());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (ejercicios.isEmpty()) {
			    ejercicios = null;
		    }
		}
		request.setAttribute("ejercicios", ejercicios);
		dispatcher.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BigDecimal new_peso = new BigDecimal(request.getParameter("newPeso"));
		Usuario loggedUser = (Usuario) request.getSession().getAttribute("usuario");
		HistorialPeso newHistorialPeso = new HistorialPeso(loggedUser.getId_usuario(), LocalDateTime.now(), new_peso);
		try {
			historialPesoDao.insertHistorialPeso(newHistorialPeso);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
