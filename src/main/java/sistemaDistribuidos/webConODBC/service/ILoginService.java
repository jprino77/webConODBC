package sistemaDistribuidos.webConODBC.service;

import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Localidad;
import sistemaDistribuidos.webConODBC.entity.Usuario;

public interface ILoginService {

	/**
	 * Obtiene todas las localidades para llenar el dropdown de localidad
	 * @return
	 */
	List<Localidad> getLocalidades();

	/**
	 * Verifica si ya existe usuario de logueo en la base.
	 * @param usuario
	 * @return
	 */
	boolean existeUsuario(String usuario);

	/**
	 * Crea usuario
	 * @param usuario
	 * @return
	 */
	boolean crearUsuario(Usuario usuario);
}
