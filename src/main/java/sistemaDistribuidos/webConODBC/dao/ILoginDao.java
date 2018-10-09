package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Localidad;
import sistemaDistribuidos.webConODBC.entity.Usuario;


public interface ILoginDao {

	Usuario getByUsuario(String usuario, Connection con) throws SQLException;

	List<Localidad> getLocalidades(Connection con) throws SQLException;
}
