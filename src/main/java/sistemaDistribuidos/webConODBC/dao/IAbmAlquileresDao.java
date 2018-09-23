package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;

public interface IAbmAlquileresDao {

	List<Filial> buscarFiliales(Connection con) throws SQLException;

	List<Deporte> buscarDEporteByFilialId(int filialId, Connection con) throws SQLException;
}
