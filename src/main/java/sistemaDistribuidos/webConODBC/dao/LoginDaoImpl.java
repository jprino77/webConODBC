package sistemaDistribuidos.webConODBC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Rol;
import sistemaDistribuidos.webConODBC.entity.Usuario;

@Repository
public class LoginDaoImpl implements ILoginDao {

	final static Logger logger = Logger.getLogger(LoginDaoImpl.class);

	private static final String getByUsuario = "select u.numero_afiliado_legajo, u.nombre, u.apellido, u.calle, u.altura, u.localidad_id, u.telefono, u.email, u.fecha_nacimieto,u.usuario,u.clave, u.rol_id,rol.codigo,rol.descripcion, activo, l.codigo_postal, l.nombre"
												+ " from usuario u" 
												+ " inner join rol rol on u.rol_id = rol.id"
												+ " inner join localidad l on l.id = u.localidad_id" 
												+ " where u.usuario = ?";

	@Override
	public Usuario getByUsuario(String usuario, Connection con) throws SQLException {
		List<Usuario> usuarioList = new ArrayList<Usuario>();

		PreparedStatement statement = con.prepareStatement(getByUsuario);
		statement.setString(1, usuario);

		logger.info(statement.toString());
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Usuario u = new Usuario();
			Rol rol = new Rol();
		
			u.setNumeroAfiliadoLegajo(rs.getInt("numero_afiliado_legajo"));
			u.setNombre(rs.getString("nombre"));
			u.setCalle(rs.getString("calle"));
			u.setUsuario(rs.getString("usuario"));
			u.setClave(rs.getString("clave"));

			usuarioList.add(u);
		}
		return usuarioList.get(0);
	}

}
