package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.HorariosFilial;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;

/**
 * 
 * Interface general Dao general para abm de alquileres
 *
 */
public interface IAbmAlquileresDao {

	/**
	 * Busco todas las filiales para llenar los dropdown
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Filial> buscarFiliales(Connection con) throws SQLException;

	/**
	 * Obtengo todos los deportes asociados a una filial
	 * @param filialId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Deporte> buscarDeporteByFilialId(int filialId, Connection con) throws SQLException;

	/**
	 * Obtiene todas las canchas disponibles para un deporte en una filial, en el dia y horario que se quiere alquilar
	 * @param form
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Cancha> buscarCanchasDisponibles(BusquedaForm form, Connection con) throws SQLException;

	/**
	 * Guarda o actualiza el alquiler dependiendo de si es un alta o una modificacion
	 * @param turno
	 * @param con
	 * @throws SQLException
	 */
	void guardarOActualizarTurno(Turno turno, Connection con) throws SQLException;

	/**
	 * Obtengo todos los alquileres para un usuario segun filtro de busqueda
	 * el filtro puede estar vacio (va a traer todos los alquileres)
	 * @param usuarioId
	 * @param busquedaForm
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Turno> getAlquileresUsuario(int usuarioId,  BusquedaForm busquedaForm,Connection con) throws SQLException;
	
	/**
	 * Anula turno solicitado
	 * @param turnoId
	 * @param con
	 * @throws SQLException
	 */
	void anularTurno(int turnoId, Connection con) throws SQLException;

	/**
	 * Obtiene los dias de semana de forma numerica en la que la filial esta abierta
	 * @param filialId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Integer> buscarDiasFilialByFilialId(int filialId, Connection con) throws SQLException;

	/**
	 * Obtiene el horario de apertura y cierre  de una filial para establecer el horario maximo y minimo en el datepicker
	 * @param filialId
	 * @param fechaAlquiler
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<HorariosFilial> buscarHorasFilialByFilialId(int filialId, LocalDate fechaAlquiler, Connection con) throws SQLException;
}
