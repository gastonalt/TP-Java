package com.gastonalt.diariodepesas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.gastonalt.diariodepesas.model.Localidad;
import com.gastonalt.diariodepesas.model.Usuario;
import com.gastonalt.diariodepesas.utils.DatabaseUtils;

public class UsuarioDao {

	private static final String INSERT_USUARIOS_SQL = 
	    "INSERT INTO usuarios (username, password, nombre, apellido, fecha_nacimiento, direccion, email, localidad_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_USUARIO_BY_USERNAME = 
	    "SELECT u.id_usuario, u.username, u.nombre, u.apellido, u.fecha_nacimiento, u.direccion, u.email, l.cod_postal, l.nombre AS localidad_nombre " +
	    "FROM usuarios u " +
	    "JOIN localidades l ON u.localidad_id = l.cod_postal " +
	    "WHERE u.username = ?;";
	private static final String SELECT_ALL_USUARIOS = 
	    "SELECT u.id_usuario, u.username, u.password, u.nombre, u.apellido, u.fecha_nacimiento, u.direccion, u.email, l.cod_postal, l.nombre AS localidad_nombre " +
	    "FROM usuarios u " +
	    "JOIN localidades l ON u.localidad_id = l.cod_postal;";
	private static final String DELETE_USUARIO_SQL = 
	    "DELETE FROM usuarios WHERE id_usuario = ?;";
	private static final String UPDATE_USUARIO_SQL = 
	    "UPDATE usuarios SET username = ?, nombre = ?, apellido = ?, fecha_nacimiento = ?, direccion = ?, email = ?, localidad_id = ? WHERE id_usuario = ?;";
	
	private static final String FIND_USUARIO_LOGIN_SQL =
		"SELECT COUNT(*) AS user_count FROM usuarios WHERE username = ? AND password = ?";


	public UsuarioDao() {
	}
	
	public void insertUsuario(Usuario usuario) throws SQLException {
		System.out.println(INSERT_USUARIOS_SQL);
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USUARIOS_SQL)) {
			preparedStatement.setString(1, usuario.getUsername());
			preparedStatement.setString(2, usuario.getPassword());
			preparedStatement.setString(3, usuario.getNombre());
			preparedStatement.setString(4, usuario.getApellido());
			preparedStatement.setDate(5, (Date) usuario.getFechaNacimiento());
			preparedStatement.setString(6, usuario.getDireccion());
			preparedStatement.setString(7, usuario.getEmail());
			preparedStatement.setInt(8, usuario.getLocalidad().getCod_postal());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
	}
	
	public Usuario selectUsuarioByUsername(String username) {
		Localidad localidad = null;
		Usuario usuario = null;
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USUARIO_BY_USERNAME);) {
			preparedStatement.setString(1, username);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id_usuario = rs.getInt("id_usuario");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				Date fechaNacimiento = rs.getDate("fecha_nacimiento");
				String direccion = rs.getString("direccion");
				String email = rs.getString("email");
				int cod_postal = rs.getInt("cod_postal");
				String nombreLocalidad = rs.getString("localidad_nombre");
				localidad = new Localidad(cod_postal, nombreLocalidad);
				usuario = new Usuario(id_usuario, username, nombre, apellido, fechaNacimiento, direccion, email, localidad);
			}
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
		return usuario;
	}
	
	public boolean checkUserCredentials(String username, String password) {
		int usersFound = 0;
		try (Connection connection = DatabaseUtils.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(FIND_USUARIO_LOGIN_SQL);) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				usersFound = rs.getInt("user_count");
			}
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
		return usersFound == 1;
	}
	
	public List<Usuario> selectAllUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		try (Connection connection = DatabaseUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USUARIOS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id_usuario = rs.getInt("username");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				Date fechaNacimiento = rs.getDate("fecha_nacimiento");
				String direccion = rs.getString("direccion");
				String email = rs.getString("email");
				int cod_postal = rs.getInt("cod_postal");
				String nombreLocalidad = rs.getString("localidad_nombre");
				usuarios.add(new Usuario(id_usuario, username, password, nombre, apellido, fechaNacimiento, direccion, email, new Localidad(cod_postal, nombreLocalidad)));
			}
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
		return usuarios;
	}
	
	public boolean deleteUsuario(int id_usuario) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USUARIO_SQL);) {
			statement.setInt(1, id_usuario);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateUsuario(Usuario usuario) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USUARIO_SQL);) {
			System.out.println("Usuario actualizado:"+preparedStatement);
			preparedStatement.setString(1, usuario.getUsername());
			preparedStatement.setString(2, usuario.getNombre());
			preparedStatement.setString(3, usuario.getApellido());
			preparedStatement.setDate(4, (Date) usuario.getFechaNacimiento());
			preparedStatement.setString(5, usuario.getDireccion());
			preparedStatement.setString(6, usuario.getEmail());
			preparedStatement.setInt(7, usuario.getLocalidad().getCod_postal());
			preparedStatement.setInt(8, usuario.getId_usuario());

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public boolean existeUsername(String username) {
	    boolean existe = false;
	    String sql = "SELECT COUNT(*) AS count FROM usuarios WHERE username = ?";
	    
	    try (Connection connection = DatabaseUtils.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, username);
	        
	        ResultSet rs = preparedStatement.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt("count");
	            if (count > 0) {
	                existe = true;
	            }
	        }
	    } catch (SQLException e) {
	        DatabaseUtils.printSQLException(e);
	    }
	    
	    return existe;
	}
	
	public boolean existeEmail(String email) {
	    boolean existe = false;
	    String sql = "SELECT COUNT(*) AS count FROM usuarios WHERE email = ?";
	    
	    try (Connection connection = DatabaseUtils.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, email);
	        
	        ResultSet rs = preparedStatement.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt("count");
	            if (count > 0) {
	                existe = true;
	            }
	        }
	    } catch (SQLException e) {
	        DatabaseUtils.printSQLException(e);
	    }
	    
	    return existe;
	}


}