package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Filial;

@Repository
public class AbmAlquileresDaoImpl implements IAbmAlquileresDao {
	final static Logger logger = Logger.getLogger(AbmAlquileresDaoImpl.class);

	private static final String getFiliales = "select id, nombre from filial;";
			
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

}
