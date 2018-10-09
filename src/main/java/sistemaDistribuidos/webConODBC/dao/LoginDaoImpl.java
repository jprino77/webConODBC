package sistemaDistribuidos.webConODBC.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemaDistribuidos.webConODBC.entity.Localidad;
import sistemaDistribuidos.webConODBC.entity.Usuario;

@Repository
public class LoginDaoImpl implements ILoginDao {

	final static Logger logger = Logger.getLogger(LoginDaoImpl.class);

	private static final String getByUsuario = "select u.numero_afiliado_legajo, u.nombre, u.apellido, u.calle, u.altura, u.localidad_id, u.telefono, u.email, u.fecha_nacimieto,u.usuario,u.clave, u.rol_id,rol.codigo,rol.descripcion, activo, l.codigo_postal, l.nombre"
			+ " from usuario u" + " inner join rol rol on u.rol_id = rol.id"
			+ " inner join localidad l on l.id = u.localidad_id" + " where u.usuario = ? and rol.codigo='00'";

	private static final String countUsuario = "select count(*) as count from usuario u where u.usuario = ?";

	private static final String getLocalidades = "select id, nombre from localidad";
	
	private static final String crearUsuario = "call CREAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '00')";

	@Override
	public Usuario getByUsuario(String usuario, Connection con) throws SQLException {
		List<Usuario> usuarioList = new ArrayList<Usuario>();

		PreparedStatement statement = con.prepareStatement(getByUsuario);
		statement.setString(1, usuario);

		logger.info(statement.toString());
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Usuario u = new Usuario();

			u.setNumeroAfiliadoLegajo(rs.getInt("numero_afiliado_legajo"));
			u.setNombre(rs.getString("nombre"));
			u.setCalle(rs.getString("calle"));
			u.setUsuario(rs.getString("usuario"));
			u.setClave(rs.getString("clave"));

			usuarioList.add(u);
		}
		return usuarioList.get(0);
	}

	@Override
	public boolean existeUsuario(String usuario, Connection con) throws SQLException {
		int count = 0;
		boolean existe = false;
		PreparedStatement statement = con.prepareStatement(countUsuario);
		statement.setString(1, usuario);

		logger.info(statement.toString());
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {

			count = rs.getInt("count");
		}

		if (count > 0) {
			existe = true;
		}
		return existe;
	}

	@Override
	public List<Localidad> getLocalidades(Connection con) throws SQLException {
		List<Localidad> localidadList = new ArrayList<Localidad>();

		PreparedStatement statement = con.prepareStatement(getLocalidades);

		logger.info(statement.toString());
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Localidad l = new Localidad();

			l.setId(rs.getInt("id"));
			l.setNombre(rs.getString("nombre"));

			localidadList.add(l);
		}
		return localidadList;
	}
	
	
	
	@Override
	public boolean crearUsuario(Usuario usuario, Connection con) throws SQLException {

		CallableStatement statement = con.prepareCall(crearUsuario);
		statement.setString(1, usuario.getNombre());
		statement.setString(2, usuario.getApellido());
		statement.setString(3, usuario.getCalle());
		statement.setInt(4, usuario.getAltura());
		statement.setInt(5, usuario.getLocalidad());
		statement.setString(6, usuario.getTelefono());
		statement.setString(7, usuario.getEmail());
		statement.setDate(8, Date.valueOf(usuario.getFechaNAcimiento()));
		statement.setString(9, usuario.getUsuario());
		statement.setString(10, usuario.getClave());

		
		return statement.execute();

	}

}
