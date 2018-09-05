package sistemaDistribuidos.webConODBC.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sistemaDistribuidos.webConODBC.dao.ILoginDao;
import sistemaDistribuidos.webConODBC.entity.Usuario;

@Service
public class LoginServiceImpl implements ILoginService {

	final static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	/**
	 * inyeccion de dependencia, es equivalente a usarlo como singleton
	 * se va a instanciar cuando se levanta la app @Autowired accedes a dicha instancia
	 */
	
	@Autowired
	ILoginDao loginDao;
	
	//Se Obtiene del application.properties
	@Value("${application.odbcDriver}")
	String odbcDriver;
	
	//Se Obtiene del application.properties
	@Value("${application.db}")
	String db;
	
	@Override
	public Usuario getByUsuarioClave(String uss, String clave) {
		
		Usuario usuario = null;
		Connection con = null;
		try {
			/**
			 * Se abre coneccion aca porque dentro de un mismo servicio podrias
			 *  tener multiples llamados a daos
			 *   de esta forma ahorras abrir varias 
			 *   y cerrar conecciones por cada uno
			 */
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);
			
			usuario = loginDao.getByUsuarioClave(uss, clave, con);
		
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
		
		return usuario;
	}

}
