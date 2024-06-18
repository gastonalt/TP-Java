package com.gastonalt.diariodepesas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gastonalt.diariodepesas.model.Ejercicio;
import com.gastonalt.diariodepesas.model.Ejercicio.TipoEjercicio;
import com.gastonalt.diariodepesas.model.Resultado;
import com.gastonalt.diariodepesas.utils.DatabaseUtils;

public class EjercicioDao {
	
    private static final String INSERT_EJERCICIO = "INSERT INTO ejercicios (descripcion, tipoEjercicio) VALUES (?, ?)";
    private static final String SELECT_EJERCICIOS_CON_ULTIMO_RESULTADO = 
            "SELECT e.id_ejercicio, e.descripcion, e.tipoEjercicio, " +
                   "r.fechaHoraCargaResultado, r.tiempoMinutos, r.cantSeries, r.cantRepeticiones, r.pesoSoportado " +
            "FROM ejercicios e " +
            "JOIN ( " +
                "SELECT id_ejercicio, MAX(fechaHoraCargaResultado) AS max_fechaHoraCargaResultado " +
                "FROM resultado " +
                "WHERE id_usuario = ? " +
                "GROUP BY id_ejercicio " +
            ") rm ON e.id_ejercicio = rm.id_ejercicio " +
            "JOIN resultado r ON rm.id_ejercicio = r.id_ejercicio AND rm.max_fechaHoraCargaResultado = r.fechaHoraCargaResultado " +
            "WHERE r.id_usuario = ? " +
            "ORDER BY e.id_ejercicio";
    private static final String SELECT_EJERCICIO_BY_ID = "SELECT id_ejercicio, descripcion, tipoEjercicio FROM ejercicios WHERE id_ejercicio = ?";
	private static final String UPDATE_EJERCICIO = "UPDATE ejercicios SET descripcion = ? WHERE id_ejercicio = ?";
	
	public EjercicioDao() {
	}
	
	public Ejercicio insertEjercicio(Ejercicio ejercicio) throws SQLException {
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EJERCICIO, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ejercicio.getDescripcion());
            preparedStatement.setString(2, ejercicio.getTipoEjercicio().toString());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idEjercicio = generatedKeys.getInt(1);
                    ejercicio.setId_ejercicio(idEjercicio);
                }
            } else {
                throw new SQLException("No se pudo insertar el ejercicio, ningún ID generado.");
            }
        } catch (SQLException e) {
            DatabaseUtils.printSQLException(e);
            throw e;
        }
        return ejercicio;
    }
	
	public List<Ejercicio> obtenerEjerciciosConUltimoResultado(int idUsuario) throws SQLException {
        List<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EJERCICIOS_CON_ULTIMO_RESULTADO)) {

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idEjercicio = resultSet.getInt("id_ejercicio");
                    String descripcion = resultSet.getString("descripcion");
                    String tipoEjercicio = resultSet.getString("tipoEjercicio");
                    Timestamp fechaHoraCargaResultado = resultSet.getTimestamp("fechaHoraCargaResultado");
                    Integer tiempoMinutos = resultSet.getObject("tiempoMinutos", Integer.class);
                    Integer cantSeries = resultSet.getObject("cantSeries", Integer.class);
                    Integer cantRepeticiones = resultSet.getObject("cantRepeticiones", Integer.class);
                    Double pesoSoportado = resultSet.getObject("pesoSoportado", Double.class);

                    Ejercicio ejercicio = new Ejercicio(idEjercicio, descripcion, TipoEjercicio.valueOf(tipoEjercicio));
                    ejercicio.setUltimoResultado(new Resultado(idUsuario, fechaHoraCargaResultado, idEjercicio, tiempoMinutos, cantSeries, cantRepeticiones, pesoSoportado));

                    ejercicios.add(ejercicio);
                }
            }
        } catch (SQLException e) {
            DatabaseUtils.printSQLException(e);
            throw e;
        }

        return ejercicios;
    }
	
    public Ejercicio getEjercicioById_ejercicio(int idEjercicio) throws SQLException {
        Ejercicio ejercicio = null;
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EJERCICIO_BY_ID)) {
            preparedStatement.setInt(1, idEjercicio);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_ejercicio = resultSet.getInt("id_ejercicio");
                String descripcion = resultSet.getString("descripcion");
                String tipoEjercicio = resultSet.getString("tipoEjercicio");
                ejercicio = new Ejercicio(id_ejercicio, descripcion, TipoEjercicio.valueOf(tipoEjercicio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener el ejercicio por ID", e);
        }

        return ejercicio;
    }
    
    public boolean updateEjercicio(int cod_ejercicio, String descripcionNueva) {
        try (Connection connection = DatabaseUtils.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EJERCICIO)) {
            preparedStatement.setString(1, descripcionNueva);
            preparedStatement.setInt(2, cod_ejercicio);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteEjercicio(int id_ejercicio) {
        String deleteResultadosSQL = "DELETE FROM resultado WHERE id_ejercicio = ?";
        String deleteEjercicioSQL = "DELETE FROM ejercicios WHERE id_ejercicio = ?";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement deleteResultadosStmt = connection.prepareStatement(deleteResultadosSQL);
             PreparedStatement deleteEjercicioStmt = connection.prepareStatement(deleteEjercicioSQL)) {

            // Elimino los resultados asociados al ejercicio
            deleteResultadosStmt.setInt(1, id_ejercicio);
            deleteResultadosStmt.executeUpdate();

            // Elimino el ejercicio
            deleteEjercicioStmt.setInt(1, id_ejercicio);
            int rowsDeleted = deleteEjercicioStmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
