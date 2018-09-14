package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Usuario;

@Repository
public class LoginDaoImpl implements ILoginDao {

	final static Logger logger = Logger.getLogger(LoginDaoImpl.class);
	
	private static final String getByUsuarioClave = "select * from usuario where usuario = ? and clave = ?";

	@Override
	public Usuario getByUsuarioClave(String usuario, String clave, Connection con) throws SQLException {
		List<Usuario> usuarioList = new ArrayList<Usuario>();

		PreparedStatement statement = con.prepareStatement(getByUsuarioClave);
		statement.setString(1, usuario);
		statement.setString(2, clave);

		logger.info(statement.toString());
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Usuario u = new Usuario();
			u.setNumeroAfiliadoLegajo(rs.getInt("numero_afiliado_legajo"));
			u.setNombre(rs.getString("nombre"));
			u.setCalle(rs.getString("calle"));

			usuarioList.add(u);
		}
		return usuarioList.get(0);
	}

}
