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

import sistemaDistribuidos.webConODBC.dao.ILoginDao;
import sistemaDistribuidos.webConODBC.entity.Localidad;
import sistemaDistribuidos.webConODBC.entity.Usuario;

@Service
public class LoginServiceImpl implements ILoginService {

	final static Logger logger = Logger.getLogger(LoginServiceImpl.class);

	// Se Obtiene del application.properties
	@Value("${application.odbcDriver}")
	String odbcDriver;

	// Se Obtiene del application.properties
	@Value("${application.db}")
	String db;

	@Autowired
	ILoginDao loginDao;
	
	@Override
	public List<Localidad> getLocalidades() {
		logger.info("Inico getLocalidades");

		List<Localidad> localidad = new ArrayList<Localidad>();
		Connection con = null;
		try {
			/**
			 * Se abre coneccion aca porque dentro de un mismo servicio podrias tener
			 * multiples llamados a daos de esta forma ahorras abrir varias y cerrar
			 * conecciones por cada uno
			 */
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			localidad = loginDao.getLocalidades(con);

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
		return localidad;
	}
	
	
	@Override
	public boolean existeUsuario(String usuario) {
		logger.info("Inico existeUsuario");
		Connection con = null;
		boolean existe = false;
		try {
			/**
			 * Se abre coneccion aca porque dentro de un mismo servicio podrias tener
			 * multiples llamados a daos de esta forma ahorras abrir varias y cerrar
			 * conecciones por cada uno
			 */
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			existe = loginDao.existeUsuario(usuario, con);

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
		
		return existe;
	}
	
	
	
	@Override
	public boolean crearUsuario(Usuario usuario) {
		logger.info("Inico existeUsuario");
		Connection con = null;
		boolean exito = false;
		try {
			/**
			 * Se abre coneccion aca porque dentro de un mismo servicio podrias tener
			 * multiples llamados a daos de esta forma ahorras abrir varias y cerrar
			 * conecciones por cada uno
			 */
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			exito=  loginDao.crearUsuario(usuario, con);

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
		
		return exito;
	}


}
