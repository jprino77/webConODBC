package sistemaDistribuidos.webConODBC.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.TipoCancha;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;

@Repository
public class AbmAlquileresDaoImpl implements IAbmAlquileresDao {
	final static Logger logger = Logger.getLogger(AbmAlquileresDaoImpl.class);

	private static final String getFiliales = "select id, nombre from filial;";

	private static final String getDeportesByFilialId = "select distinct de.id, de.descripcion from cancha ca "
			+ " inner join deporte de on de.id = ca.deporte_id" + " where ca.filial_id =  ?";

	private static final String getCanchasDisponibles = "call TRAER_CANCHAS_DISPONIBLES(?,?,?,?)";

	private static final String getAlquileresUsuario = "call TRAER_ALQUILERES_USUARIO(?)";

	private static final String insertTurno = "insert into turno (fecha_hora_solicitud, fecha_hora_desde, fecha_hora_hasta, cancha_id, usuario_id) values(?, ?, ?, ?,?)";

	private static final String anularTurno = "update turno set cancelado = true where id = ?";

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
	public void guardarTurno(Turno turno, Connection con) throws SQLException {

		PreparedStatement statement = con.prepareStatement(insertTurno);

		statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
		statement.setTimestamp(2, Timestamp.valueOf(turno.getFechaHoraDesde()));
		statement.setTimestamp(3, Timestamp.valueOf(turno.getFechaHoraHasta()));
		statement.setInt(4, turno.getCancha().getId());
		statement.setInt(5, turno.getUsuario().getNumeroAfiliadoLegajo());

		statement.execute();

	}

	@Override
	public List<Turno> getAlquileresUsuario(int usuarioId, Connection con) throws SQLException {
		List<Turno> turnoList = new ArrayList<Turno>();

		CallableStatement statement = con.prepareCall(getAlquileresUsuario);
		statement.setInt(1, usuarioId);

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

			deporte.setId(rs.getInt("cancha_id"));
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

}
