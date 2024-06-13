package com.gastonalt.diariodepesas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gastonalt.diariodepesas.model.Localidad;
import com.gastonalt.diariodepesas.utils.DatabaseUtils;

public class LocalidadDao {
	
	private static final String INSERT_LOCALIDADES_SQL = "INSERT INTO localidades (cod_postal, nombre) VALUES (?, ?);";
	private static final String SELECT_LOCALIDADES_BY_CODPOSTAL = "select cod_postal, nombre from localidades where cod_postal =?";
	private static final String SELECT_ALL_LOCALIDADES = "select * from localidades";
	private static final String DELETE_LOCALIDAD_SQL = "delete from localidades where cod_postal = ?;";
	private static final String UPDATE_LOCALIDAD_SQL = "update localidades set cod_postal = ?,nombre= ? where cod_postal = ?;";
	
	public LocalidadDao() {
	}
	
	public void insertLocalidad(Localidad localidad) throws SQLException {
		System.out.println(INSERT_LOCALIDADES_SQL);
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOCALIDADES_SQL)) {
			preparedStatement.setInt(1, localidad.getCod_postal());
			preparedStatement.setString(2, localidad.getNombre());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
	}
	
	public Localidad selectLocalidad(int cod_postal) {
		Localidad localidad = null;
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOCALIDADES_BY_CODPOSTAL);) {
			preparedStatement.setLong(1, cod_postal);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				localidad = new Localidad(cod_postal, nombre);
			}
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
		return localidad;
	}
	
	public List<Localidad> selectAllLocalidades() {
		List<Localidad> localidades = new ArrayList<>();
		try (Connection connection = DatabaseUtils.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIDADES);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int cod_postal = rs.getInt("cod_postal");
				String nombre = rs.getString("nombre");
				localidades.add(new Localidad(cod_postal, nombre));
			}
		} catch (SQLException e) {
			DatabaseUtils.printSQLException(e);
		}
		return localidades;
	}
	
	public boolean deleteLocalidad(int cod_postal) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_LOCALIDAD_SQL);) {
			statement.setInt(1, cod_postal);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateLocalidad(Localidad localidad, int cod_postal_anterior) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = DatabaseUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_LOCALIDAD_SQL);) {
			System.out.println("Localidad actualizada:"+statement);
			statement.setInt(1, localidad.getCod_postal());
			statement.setString(2, localidad.getNombre());
			statement.setInt(3, cod_postal_anterior);

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

}
