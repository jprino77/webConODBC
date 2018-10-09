package sistemaDistribuidos.webConODBC.service;

import java.time.LocalDate;
import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.HorariosFilial;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.entity.Usuario;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;

public interface IAbmAlquilerService {

	/**
	 * Obtiene filiales para llenar los dropdown
	 * 
	 * @return
	 */
	List<Filial> buscarFiliales();

	/**
	 * Obtiene todos los deportes para una filial
	 * 
	 * @param filialId
	 * @return
	 */
	List<Deporte> buscarDeportesByFilialesId(int filialId);

	/**
	 * Obtiene canchas diponibles para una filial, deporte, en el dia y hora
	 * establecido en el filtro de busqueda. El filtro siempre viene completo
	 * 
	 * @param form
	 * @return
	 */
	List<Cancha> buscarCanchasDisponibles(BusquedaForm form);

	/**
	 * Guarda o actualiza un turno dependiendo de si es alta o modificacion
	 * 
	 * @param turno
	 * @param usuario
	 * @return
	 */
	boolean guardarOActualizarTurno(Turno turno, Usuario usuario);

	/**
	 * Anula turno
	 * 
	 * @param turnoId
	 * @return
	 */
	boolean anularTurno(int turnoId);

	/**
	 * Lista de alquileres para un usuario determinado, segun el filtro de busqueda
	 * establecido, el mismo puede venir vacio
	 * 
	 * @param usuarioId
	 * @param busquedaForm
	 * @return
	 */
	List<Turno> getAlquileresUsuario(int usuarioId, BusquedaForm busquedaForm);

	/**
	 * Devuelve la lista de dias, en forma numerica, en los que la filial no esta
	 * abierta. Los cuales quedaran inhabilitados en el datepicker
	 * 
	 * @param filialId
	 * @return
	 */
	List<Integer> buscarDiasInhabilitadosByFilialId(int filialId);

	/**
	 * Horarios de apertura y cierre de una filial en determinado dia de la semana
	 * @param filialId
	 * @param fechaAlquiler
	 * @return
	 */
	List<HorariosFilial> buscarHorasFilialByFilialId(int filialId, LocalDate fechaAlquiler);

}
