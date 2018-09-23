package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;

@Repository
public class AbmAlquileresDaoImpl implements IAbmAlquileresDao {
	final static Logger logger = Logger.getLogger(AbmAlquileresDaoImpl.class);

	private static final String getFiliales = "select id, nombre from filial;";
	
	private static final String getDeportesByFilialId = "select distinct de.id, de.descripcion from cancha ca "
														+" inner join deporte de on de.id = ca.deporte_id"
														+" where ca.filial_id =  ?";
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
	public List<Deporte> buscarDEporteByFilialId(int filialId,Connection con) throws SQLException {
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

}
