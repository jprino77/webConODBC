package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Localidad;
import sistemaDistribuidos.webConODBC.entity.Usuario;

/**
 * 
 * Dao utilizado para el login y el registro de usuario
 *
 */
public interface ILoginDao {

	/**
	 * Obtiene Los Datos del Usuario a partir del usuario que usa para loguearse
	 * 
	 * @param usuario
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	Usuario getByUsuario(String usuario, Connection con) throws SQLException;

	/**
	 * Obtengo todas las localidades para llenar Dropdown de registro de usuario
	 * 
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Localidad> getLocalidades(Connection con) throws SQLException;

	/**
	 * Verifica existencia de usuario (el que se usa para loguearse) al momento de crear usuario
	 * @param usuario
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	boolean existeUsuario(String usuario, Connection con) throws SQLException;
	
	
	/**
	 * Da de alta un usuario en la base
	 * @param usuario
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	boolean crearUsuario(Usuario usuario, Connection con) throws SQLException;
}
