package com.gastonalt.diariodepesas.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gastonalt.diariodepesas.model.HistorialPeso;
import com.gastonalt.diariodepesas.model.Localidad;
import com.gastonalt.diariodepesas.utils.DatabaseUtils;

public class HistorialPesoDao {
	
	private static String INSERT_HISTORIALPESO_SQL = 
        "INSERT INTO HistorialPeso (id_usuario, fecha_peso, peso) VALUES (?, ?, ?)";
    private static String SELECT_LAST_HISTORIALPESO_SQL = 
        "SELECT id_usuario, fecha_peso, peso " +
        "FROM HistorialPeso " +
        "WHERE id_usuario = ? " +
        "ORDER BY fecha_peso DESC " +
        "LIMIT 1";
    private static String SELECT_ALL_HISTORIALPESOS_SQL = 
        "SELECT id_usuario, fecha_peso, peso " +
        "FROM HistorialPeso " +
        "WHERE id_usuario = ? " +
        "ORDER BY fecha_peso";
    private static String UPDATE_HISTORIALPESO_SQL = 
        "UPDATE HistorialPeso " +
        "SET peso = ? " +
        "WHERE id_usuario = ? AND fecha_peso = ?";
    private static String DELETE_HISTORIAL_SQL = 
        "DELETE FROM HistorialPeso " +
        "WHERE id_usuario = ? AND fecha_peso = ?";
    
	public void insertHistorialPeso(HistorialPeso historialPeso) throws SQLException {
		System.out.println(INSERT_HISTORIALPESO_SQL);
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HISTORIALPESO_SQL)) {
			preparedStatement.setInt(1, historialPeso.getId_usuario());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(historialPeso.getFecha_peso()));
			preparedStatement.setBigDecimal(3, historialPeso.getPeso());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
	}
	
	public HistorialPeso selectLastHistorialPeso(int id_usuario) {
		System.out.println(SELECT_LAST_HISTORIALPESO_SQL);
		HistorialPeso historialPeso = null;
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_HISTORIALPESO_SQL);) {
			preparedStatement.setLong(1, id_usuario);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				LocalDateTime fechaPeso = rs.getTimestamp("fecha_peso").toLocalDateTime();
				BigDecimal peso = rs.getBigDecimal("peso");
				historialPeso = new HistorialPeso(id_usuario, fechaPeso, peso);
			}
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
		return historialPeso;
	}
	
	public List<HistorialPeso> selectAllHistorialPeso(int id_usuario) {
		List<HistorialPeso> historialPeso = new ArrayList<>();
		try (Connection connection = DatabaseUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_HISTORIALPESOS_SQL);) {
			preparedStatement.setLong(1, id_usuario);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				LocalDateTime fechaPeso = rs.getTimestamp("fecha_peso").toLocalDateTime();
				BigDecimal peso = rs.getBigDecimal("peso");
				historialPeso.add(new HistorialPeso(id_usuario, fechaPeso, peso));
			}
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
		return historialPeso;
	}
	
	public boolean deleteLocalidad(HistorialPeso historialPeso) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = DatabaseUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HISTORIAL_SQL);) {
			preparedStatement.setInt(1, historialPeso.getId_usuario());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(historialPeso.getFecha_peso()));
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateLocalidad(HistorialPeso historialPeso) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = DatabaseUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HISTORIALPESO_SQL);) {
			System.out.println("Localidad actualizada:"+preparedStatement);
			preparedStatement.setBigDecimal(1, historialPeso.getPeso());
			preparedStatement.setInt(2, historialPeso.getId_usuario());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(historialPeso.getFecha_peso()));

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

}
