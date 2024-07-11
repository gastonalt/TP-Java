package com.gastonalt.diariodepesas.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gastonalt.diariodepesas.model.HistorialPeso;
import com.gastonalt.diariodepesas.model.Localidad;
import com.gastonalt.diariodepesas.model.Usuario;
import com.gastonalt.diariodepesas.model.UsuarioHistorialPesoDTO;
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
    
    private static String SELECT_ALL_HISTORIALPESOSALLUSERS_SQL = "SELECT u.id_usuario, u.username, u.nombre, u.apellido, u.fecha_nacimiento, u.direccion, u.email, u.isAdmin, hp.fecha_peso, hp.peso " +
            "FROM historialpeso hp " +
            "JOIN usuarios u ON hp.id_usuario = u.id_usuario " +
            "WHERE hp.fecha_peso BETWEEN ? AND ?";
    
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
	
	
	public List<UsuarioHistorialPesoDTO> selectAllHistorialPesos(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
	    Map<Integer, UsuarioHistorialPesoDTO> usuarioHistorialMap = new HashMap<>();

	    try (Connection connection = DatabaseUtils.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_HISTORIALPESOSALLUSERS_SQL);) {
	        
	        preparedStatement.setTimestamp(1, Timestamp.valueOf(fechaInicio));
	        preparedStatement.setTimestamp(2, Timestamp.valueOf(fechaFin));
	        
	        System.out.println(preparedStatement);
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        while (rs.next()) {
	            int idUsuario = rs.getInt("id_usuario");
	            String username = rs.getString("username");
	            String nombre = rs.getString("nombre");
	            String apellido = rs.getString("apellido");
	            Date fechaNacimiento = rs.getDate("fecha_nacimiento");
	            String direccion = rs.getString("direccion");
	            String email = rs.getString("email");
	            Localidad localidad = null;
	            
	            Usuario usuario = new Usuario(idUsuario, username, nombre, apellido, fechaNacimiento, direccion, email, localidad);
	            
	            LocalDateTime fechaPeso = rs.getTimestamp("fecha_peso").toLocalDateTime();
	            BigDecimal peso = rs.getBigDecimal("peso");
	            HistorialPeso historialPeso = new HistorialPeso(idUsuario, fechaPeso, peso);
	            
	            UsuarioHistorialPesoDTO usuarioHistorialPesoDTO = usuarioHistorialMap.get(idUsuario);
	            if (usuarioHistorialPesoDTO == null) {
	                List<HistorialPeso> pesos = new ArrayList<>();
	                pesos.add(historialPeso);
	                usuarioHistorialPesoDTO = new UsuarioHistorialPesoDTO(usuario, pesos);
	                usuarioHistorialMap.put(idUsuario, usuarioHistorialPesoDTO);
	            } else {
	                usuarioHistorialPesoDTO.getPesos().add(historialPeso);
	            }
	        }
	    } catch (SQLException e) {
	        DatabaseUtils.printSQLException(e);
	    }

	    return new ArrayList<>(usuarioHistorialMap.values());
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
