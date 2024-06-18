package com.gastonalt.diariodepesas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gastonalt.diariodepesas.model.Resultado;
import com.gastonalt.diariodepesas.utils.DatabaseUtils;

public class ResultadoDao {
	
	private static final String INSERT_RESULTADO = "INSERT INTO resultado (id_usuario, fechaHoraCargaResultado, id_ejercicio, tiempoMinutos, cantSeries, cantRepeticiones, pesoSoportado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String LIST_RESULTADOS_BY_EJERCICIOS_AND_USUARIO = "SELECT * FROM resultado WHERE id_usuario = ? AND id_ejercicio = ? ORDER BY fechaHoraCargaResultado DESC";
    private static final String DELETE_RESULTADO = "DELETE FROM resultado WHERE id_usuario = ? AND fechaHoraCargaResultado = ?";
    
    public void insertResultado(Resultado resultado) throws SQLException {
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESULTADO)) {

            preparedStatement.setInt(1, resultado.getIdUsuario());
            preparedStatement.setTimestamp(2, new Timestamp(resultado.getFechaHoraCargaResultado().getTime()));
            preparedStatement.setInt(3, resultado.getIdEjercicio());
            preparedStatement.setObject(4, resultado.getTiempoMinutos());
            preparedStatement.setObject(5, resultado.getCantSeries());
            preparedStatement.setObject(6, resultado.getCantRepeticiones());
            preparedStatement.setObject(7, resultado.getPesoSoportado());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DatabaseUtils.printSQLException(e);
            throw e;
        }
    }
    
	public List<Resultado> listResultados(int idUsuario, int idEjercicio) throws SQLException {
	    List<Resultado> resultados = new ArrayList<>();
	    try (Connection connection = DatabaseUtils.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(LIST_RESULTADOS_BY_EJERCICIOS_AND_USUARIO)) {
	        preparedStatement.setInt(1, idUsuario);
	        preparedStatement.setInt(2, idEjercicio);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            int idEjercicioDB = resultSet.getInt("id_ejercicio");
	            int idUsuarioDB = resultSet.getInt("id_usuario");
	            Timestamp fechaHoraCargaResultado = resultSet.getTimestamp("fechaHoraCargaResultado");
	            Integer tiempoMinutos = resultSet.getInt("tiempoMinutos");
	            if (resultSet.wasNull()) tiempoMinutos = null;
	            Integer cantSeries = resultSet.getInt("cantSeries");
	            if (resultSet.wasNull()) cantSeries = null;
	            Integer cantRepeticiones = resultSet.getInt("cantRepeticiones");
	            if (resultSet.wasNull()) cantRepeticiones = null;
	            Double pesoSoportado = resultSet.getDouble("pesoSoportado");
	            if (resultSet.wasNull()) pesoSoportado = null;
	            Resultado resultado = new Resultado(idUsuarioDB, fechaHoraCargaResultado, idEjercicioDB, tiempoMinutos, cantSeries, cantRepeticiones, pesoSoportado);
	            resultados.add(resultado);
	        }
	    }
	    return resultados;
	}
	
	public boolean deleteResultado(int idUsuario, Timestamp fechaHoraCargaResultado) {
        boolean deleted = false;
        PreparedStatement stmt = null;

        try  (Connection connection = DatabaseUtils.getConnection()){
            stmt = connection.prepareStatement(DELETE_RESULTADO);
            stmt.setInt(1, idUsuario);
            stmt.setTimestamp(2, fechaHoraCargaResultado);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }
    
}
