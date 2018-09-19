package sistemaDistribuidos.webConODBC.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sistemaDistribuidos.webConODBC.dao.ILoginDao;
import sistemaDistribuidos.webConODBC.entity.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	final static Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	ILoginDao loginDao;

	// Se Obtiene del application.properties
	@Value("${application.odbcDriver}")
	String odbcDriver;

	// Se Obtiene del application.properties
	@Value("${application.db}")
	String db;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usuario usuario = null;
		Connection con = null;
		try {
			/**
			 * Se abre coneccion aca porque dentro de un mismo servicio podrias tener
			 * multiples llamados a daos de esta forma ahorras abrir varias y cerrar
			 * conecciones por cada uno
			 */
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			usuario = loginDao.getByUsuario(userName, con);

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
		if (usuario == null) {
			logger.info("User not found! " + userName);
			throw new UsernameNotFoundException("Usuario " + userName + " was no encontrado");
		}

		logger.info("Found User: " + usuario.getUsuario());

		// [ROLE_USER, ROLE_ADMIN,..]
//		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//		if (roleNames != null) {
//			for (String role : roleNames) {
				// ROLE_USER, ROLE_ADMIN,..
				GrantedAuthority authority = new SimpleGrantedAuthority("afiliado");
				grantList.add(authority);
//			}
//		}

		usuario.setAuthorities(grantList);
		

		return usuario;
	}

}
