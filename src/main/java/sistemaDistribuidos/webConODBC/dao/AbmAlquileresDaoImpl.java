package sistemaDistribuidos.webConODBC.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.HorariosFilial;
import sistemaDistribuidos.webConODBC.entity.TipoCancha;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;

@Repository
public class AbmAlquileresDaoImpl implements IAbmAlquileresDao {
	final static Logger logger = Logger.getLogger(AbmAlquileresDaoImpl.class);

	private static final String getFiliales = "select id, nombre from filial;";

	private static final String getDeportesByFilialId = "select distinct de.id, de.descripcion from cancha ca "
			+ " inner join deporte de on de.id = ca.deporte_id" + " where ca.filial_id =  ?";
	
	private static final String getHorariosFilialByFilialId = "select dia_semana, hora_desde, hora_hasta from horarios_filial where filial_id = ?";

	private static final String getCanchasDisponibles = "call TRAER_CANCHAS_DISPONIBLES(?,?,?,?)";

	private static final String getAlquileresUsuario = "call TRAER_ALQUILERES_USUARIO(?, ?, ?, ?, ?)";

	private static final String insertTurno = "insert into turno (fecha_hora_solicitud, fecha_hora_desde, fecha_hora_hasta, cancha_id, usuario_id) values(?, ?, ?, ?,?)";

	private static final String anularTurno = "update turno set cancelado = true where id = ?";
	
	private static final String modificarTurno = "update turno set fecha_hora_desde = ?, fecha_hora_hasta = ?, cancha_id = ? where id = ?";

	@Override
	public List<Filial> buscarFiliales(Connection con) throws SQLException {
		List<Filial> filialList = new ArrayList<Filial>();

		PreparedStatement statement = con.prepareStatement(getFiliales);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Filial filial = new Filial();

			filial.setId(rs.getInt("id"));
			filial.setNombre(rs.getString("nombre"));

			filialList.add(filial);
		}
		return filialList;
	}

	@Override
	public List<Deporte> buscarDeporteByFilialId(int filialId, Connection con) throws SQLException {
		List<Deporte> deporteList = new ArrayList<Deporte>();

		PreparedStatement statement = con.prepareStatement(getDeportesByFilialId);
		statement.setInt(1, filialId);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Deporte deporte = new Deporte();

			deporte.setId(rs.getInt("id"));
			deporte.setDescripcion(rs.getString("descripcion"));

			deporteList.add(deporte);
		}
		return deporteList;
	}
	
	@Override
	public List<Integer> buscarDiasFilialByFilialId(int filialId, Connection con) throws SQLException {
		List<Integer> diasFilialList = new ArrayList<Integer>();

		PreparedStatement statement = con.prepareStatement(getHorariosFilialByFilialId);
		statement.setInt(1, filialId);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {

			diasFilialList.add(rs.getInt("dia_semana"));
		}
		return diasFilialList;
	}

	@Override
	public List<Cancha> buscarCanchasDisponibles(BusquedaForm form, Connection con) throws SQLException {
		List<Cancha> canchaList = new ArrayList<Cancha>();

		CallableStatement statement = con.prepareCall(getCanchasDisponibles);
		statement.setInt(1, form.getFilial());
		statement.setInt(2, form.getDeporte());

		statement.setTimestamp(3, Timestamp.valueOf(form.getFechahoraInicio()));
		statement.setTimestamp(4, Timestamp.valueOf(form.getFechahoraFin()));

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Cancha cancha = new Cancha();
			TipoCancha tipoCancha = new TipoCancha();

			cancha.setId(rs.getInt("cancha_id"));
			cancha.setCodigo(rs.getString("cancha_codigo"));
			tipoCancha.setId(rs.getInt("tipo_id"));
			tipoCancha.setDescripcion(rs.getString("tipo_descripcion"));
			cancha.setTipoCancha(tipoCancha);
			canchaList.add(cancha);
		}
		return canchaList;
	}

	@Override
	public void guardarOActualizarTurno(Turno turno, Connection con) throws SQLException {

		PreparedStatement statement = null;
		if(turno.getEsModificacion()) {
			statement = con.prepareStatement(modificarTurno);
			
			statement.setTimestamp(1, Timestamp.valueOf(turno.getFechaHoraDesde()));
			statement.setTimestamp(2, Timestamp.valueOf(turno.getFechaHoraHasta()));
			statement.setInt(3, turno.getCancha().getId());
			statement.setInt(4, turno.getId());
			
		}else {
			statement = con.prepareStatement(insertTurno);
			
			statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			statement.setTimestamp(2, Timestamp.valueOf(turno.getFechaHoraDesde()));
			statement.setTimestamp(3, Timestamp.valueOf(turno.getFechaHoraHasta()));
			statement.setInt(4, turno.getCancha().getId());
			statement.setInt(5, turno.getUsuario().getNumeroAfiliadoLegajo());
		}
		


		statement.execute();

	}

	@Override
	public List<Turno> getAlquileresUsuario(int usuarioId, BusquedaForm busquedaForm, Connection con) throws SQLException {
		List<Turno> turnoList = new ArrayList<Turno>();

		CallableStatement statement = con.prepareCall(getAlquileresUsuario);
		statement.setInt(1, usuarioId);
		
		if(busquedaForm.getFilial() != 0 && busquedaForm.getFilial() != null ) {
			statement.setInt(2, busquedaForm.getFilial());
		}else {
			statement.setNull(2, Types.INTEGER);
		}
		
		
		if(busquedaForm.getDeporte() != null ) {
			statement.setInt(3, busquedaForm.getDeporte());
		}else {
			statement.setNull(3, Types.INTEGER);
		}
		
		if(busquedaForm.getFechaAlquiler() != null && busquedaForm.getFechaAlquilerHasta() != null) {
			statement.setDate(4, Date.valueOf(busquedaForm.getFechaAlquiler()));
			statement.setDate(5, Date.valueOf(busquedaForm.getFechaAlquilerHasta()));
		}else {
			statement.setNull(4, Types.DATE);
			statement.setNull(5, Types.DATE);
		}
		
		
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Turno turno = new Turno();
			Cancha cancha = new Cancha();
			TipoCancha tipoCancha = new TipoCancha();
			Deporte deporte = new Deporte();
			Filial filial = new Filial();

			cancha.setId(rs.getInt("cancha_id"));
			cancha.setCodigo(rs.getString("cancha_codigo"));

			tipoCancha.setDescripcion(rs.getString("tipo_cancha_descripcion"));

			deporte.setId(rs.getInt("deporte_id"));
			deporte.setDescripcion(rs.getString("deporte_descripcion"));

			filial.setId(rs.getInt("filial_id"));

			turno.setId(rs.getInt("turno_id"));
			turno.setFechaHoraDesde(rs.getTimestamp("fecha_hora_desde").toLocalDateTime());
			turno.setFechaHoraHasta(rs.getTimestamp("fecha_hora_hasta").toLocalDateTime());
			turno.setPuedeAnular(rs.getBoolean("puede_anular"));

			cancha.setTipoCancha(tipoCancha);
			cancha.setDeporte(deporte);
			cancha.setFilial(filial);
			turno.setCancha(cancha);

			turnoList.add(turno);

		}
		return turnoList;
	}

	@Override
	public void anularTurno(int turnoId, Connection con) throws SQLException {
		PreparedStatement statement = con.prepareStatement(anularTurno);
		statement.setInt(1, turnoId);
		statement.executeUpdate();

	}

	@Override
	public List<HorariosFilial> buscarHorasFilialByFilialId(int filialId,LocalDate fechaAlquiler, Connection con) throws SQLException {
		List<HorariosFilial> horarioFilialList = new ArrayList<HorariosFilial>();
		
		int diaSemana = fechaAlquiler.getDayOfWeek().getValue();
		
		// El plugin del datepicker empieza a contar la semana desde el 0 (domingo) 
		// por este motivo en la tabla horariosFilial tambien esta con 0 si abriera los domingos la filial
		// mientras que LocalDate empieza a contar desde 1
		if(diaSemana == 7) {
			
			diaSemana = 0;
		}

		PreparedStatement statement = con.prepareStatement(getHorariosFilialByFilialId + " and dia_semana= ?");
		
		statement.setInt(1, filialId);
		statement.setInt(2, diaSemana);
		
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			HorariosFilial horariosFilial = new HorariosFilial();

			horariosFilial.setHoraDesde(rs.getTime("hora_desde").toLocalTime());
			horariosFilial.setHasta(rs.getTime("hora_hasta").toLocalTime());

			horarioFilialList.add(horariosFilial);
		}
		return horarioFilialList;
	}

}
