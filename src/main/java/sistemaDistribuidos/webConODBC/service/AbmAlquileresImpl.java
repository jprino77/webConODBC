package sistemaDistribuidos.webConODBC.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sistemaDistribuidos.webConODBC.dao.IAbmAlquileresDao;
import sistemaDistribuidos.webConODBC.entity.Filial;

@Service
public class AbmAlquileresImpl implements IAbmAlquilerService {
	
	final static Logger logger = Logger.getLogger(AbmAlquileresImpl.class);

	// Se Obtiene del application.properties
	@Value("${application.odbcDriver}")
	String odbcDriver;

	// Se Obtiene del application.properties
	@Value("${application.db}")
	String db;

	@Autowired
	IAbmAlquileresDao abmDao;

	@Override
	public List<Filial> buscarFiliales() {
		List<Filial> filial = new ArrayList<Filial>();
		Connection con = null;
		try {
			/**
			 * Se abre coneccion aca porque dentro de un mismo servicio podrias tener
			 * multiples llamados a daos de esta forma ahorras abrir varias y cerrar
			 * conecciones por cada uno
			 */
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			filial = abmDao.buscarFiliales(con);

		} catch (ClassNotFoundException e) {
			logger.error("Eror al cargar driver");
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("Eror al ejecutar query");
			e.printStackTrace();
		} finally {
			try {
				con.close();

			} catch (SQLException e) {
				logger.error("Eror al cerrar coneccion");
				e.printStackTrace();
			}
		}
		return filial;
	}

}
