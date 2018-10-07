package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.HorariosFilial;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;

public interface IAbmAlquileresDao {

	List<Filial> buscarFiliales(Connection con) throws SQLException;

	List<Deporte> buscarDeporteByFilialId(int filialId, Connection con) throws SQLException;

	List<Cancha> buscarCanchasDisponibles(BusquedaForm form, Connection con) throws SQLException;

	void guardarOActualizarTurno(Turno turno, Connection con) throws SQLException;

	List<Turno> getAlquileresUsuario(int usuarioId,  BusquedaForm busquedaForm,Connection con) throws SQLException;
	
	void anularTurno(int turnoId, Connection con) throws SQLException;

	List<HorariosFilial> buscarHorariosFilialByFilialId(int filialId, Connection con) throws SQLException;
}
