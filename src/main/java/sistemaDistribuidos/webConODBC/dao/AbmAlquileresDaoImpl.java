package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.TipoCancha;
import sistemaDistribuidos.webConODBC.entity.Turno;

@Repository
public class AbmAlquileresDaoImpl implements IAbmAlquileresDao {
	final static Logger logger = Logger.getLogger(AbmAlquileresDaoImpl.class);

	private static final String getFiliales = "select id, nombre from filial;";
	
	private static final String getDeportesByFilialId = "select distinct de.id, de.descripcion from cancha ca "
														+" inner join deporte de on de.id = ca.deporte_id"
														+" where ca.filial_id =  ?";
	
	private static final String getCanchaByDeperteAndFilial = "select ca.id as 'cancha_id', ca.codigo as 'cancha_codigo', tc.id as 'tipo_id', tc.descripcion as 'tipo_descripcion' from cancha ca "
															  + "inner join tipo_cancha tc on tc.id= ca.tipo_cancha_id "
															  + "where ca.filial_id = ? and ca.deporte_id = ?";
	
	private static final String insertTurno = "insert into turno (fecha_hora_solicitud, fecha_hora_desde, fecha_hora_hasta, cancha_id, usuario_id) values(?, ?, ?, ?,?)";
	
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
	public List<Deporte> buscarDeporteByFilialId(int filialId,Connection con) throws SQLException {
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
	public List<Cancha> buscarCanchaByDeporteAndFilial(int filialId,int deporteId, Connection con) throws SQLException {
		List<Cancha> canchaList = new ArrayList<Cancha>();

		PreparedStatement statement = con.prepareStatement(getCanchaByDeperteAndFilial);
		statement.setInt(1, filialId);
		statement.setInt(2, deporteId);
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
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		statement.setDate(1, Date.valueOf(LocalDate.now()));
		statement.setDate(2,  Date.valueOf(turno.getFechaHoraDesde().toLocalDate()));
		statement.setDate(3,  Date.valueOf(turno.getFechaHoraHasta().toLocalDate()));
		statement.setInt(4, turno.getCancha().getId());
		statement.setInt(5, turno.getUsuario().getNumeroAfiliadoLegajo());

		statement.execute();
		
	}

}
