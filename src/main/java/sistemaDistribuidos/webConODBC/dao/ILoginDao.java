package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.SQLException;

import sistemaDistribuidos.webConODBC.entity.Usuario;


public interface ILoginDao {

	Usuario getByUsuarioClave(String usuario, String clave, Connection con) throws SQLException;
}
