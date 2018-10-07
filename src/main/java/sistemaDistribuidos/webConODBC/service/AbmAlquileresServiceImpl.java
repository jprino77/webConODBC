package sistemaDistribuidos.webConODBC.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sistemaDistribuidos.webConODBC.dao.IAbmAlquileresDao;
import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.HorariosFilial;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.entity.Usuario;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;

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
	public List<Integer> buscarDiasInhabilitadosByFilialId(int filialId) {
		logger.info("Inico busqueda buscarDiasFilialByFilialId filaiid: " + filialId);
		Connection con = null;
		List<Integer> diasSemanaDisabled = new ArrayList<Integer>();
		List<Integer> diasFilial;
		try {

			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			diasFilial = abmDao.buscarDiasFilialByFilialId(filialId, con);

			/**
			 * Filtro los dias de semana que van a quedar inhabilitados en el calendario, en
			 * los que la filial va a estar cerrada Por mantenimiento o porque no abre
			 * 
			 * 0 -> domingo
			 * 1 -> Lunes
			 * 2 -> Martes
			 * 3 -> Miercoles
			 * 4 -> Jueves
			 * 5 -> viernes
			 * 6 -> Sabado
			 **/
			diasSemanaDisabled = Stream.of(0, 1, 2, 3, 4, 5, 6).filter(dia -> !diasFilial.contains(dia))
					.collect(Collectors.toList());

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
		return diasSemanaDisabled;
	}

	@Override
	public List<Cancha> buscarCanchasDisponibles(BusquedaForm form) {
		logger.info("Inico busqueda buscarCanchasDisponibles filaiid: " + form.getFilial() + "deporteId"
				+ form.getDeporte());
		List<Cancha> cancha = new ArrayList<Cancha>();
		Connection con = null;
		try {
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			cancha = abmDao.buscarCanchasDisponibles(form, con);

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

	@Override
	public boolean guardarOActualizarTurno(Turno turno, Usuario usuario) {
		logger.info("inicio guardar Turno");
		boolean exitoso = false;

		Connection con = null;
		try {
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);
			turno.setUsuario(usuario);
			abmDao.guardarOActualizarTurno(turno, con);
			exitoso = true;
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

		logger.info("fin guardar Turno");
		return exitoso;

	}

	@Override
	public List<Turno> getAlquileresUsuario(int usuarioId, BusquedaForm busquedaForm) {
		logger.info("Inico busqueda getAlquileresUsuario usuarioId: " + usuarioId);
		List<Turno> turno = new ArrayList<Turno>();
		Connection con = null;
		try {
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			turno = abmDao.getAlquileresUsuario(usuarioId, busquedaForm, con);

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
		return turno;
	}

	@Override
	public boolean anularTurno(int turnoId) {
		logger.info("inicio anularTurno Turno");
		boolean exitoso = false;

		Connection con = null;
		try {
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);
			abmDao.anularTurno(turnoId, con);
			exitoso = true;
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

		logger.info("fin guardar Turno");
		return exitoso;
	}

	@Override
	public List<HorariosFilial> buscarHorasFilialByFilialId(int filialId, LocalDate fechaAlquiler) {
		logger.info("Inico busqueda buscarHorasFilialByFilialId filialId: " + filialId);
		List<HorariosFilial> horariosFilial = new ArrayList<HorariosFilial>();
		Connection con = null;
		try {
			Class.forName(odbcDriver);
			con = DriverManager.getConnection(db);

			horariosFilial = abmDao.buscarHorasFilialByFilialId(filialId,fechaAlquiler, con);

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
		return horariosFilial;
	}

}
