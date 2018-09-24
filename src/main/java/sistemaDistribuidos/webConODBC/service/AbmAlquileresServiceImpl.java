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
import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;

@Service
public class AbmAlquileresServiceImpl implements IAbmAlquilerService {

	final static Logger logger = Logger.getLogger(AbmAlquileresServiceImpl.class);

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

		logger.info("Inico buscarFiliales");

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

	@Override
	public List<Deporte> buscarDeportesByFilialesId(int filialId) {
		logger.info("Inico busqueda deportes filaiid: " + filialId);
		List<Deporte> deporte = new ArrayList<Deporte>();
		Connection con = null;
		try {
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			deporte = abmDao.buscarDeporteByFilialId(filialId, con);

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
		return deporte;
	}

	@Override
	public List<Cancha> buscarCanchaByDeporteAndFilial(int filialId, int deporteId) {
		logger.info("Inico busqueda buscarCanchaByDeporteAndFilial filaiid: " + filialId + "deporteId" + deporteId);
		List<Cancha> cancha = new ArrayList<Cancha>();
		Connection con = null;
		try {
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			cancha = abmDao.buscarCanchaByDeporteAndFilial(filialId,deporteId, con);

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
		return cancha;
	}

}
