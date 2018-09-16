package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.SQLException;

import sistemaDistribuidos.webConODBC.entity.Usuario;


public interface ILoginDao {

	Usuario getByUsuario(String usuario, Connection con) throws SQLException;
}
