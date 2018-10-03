package sistemaDistribuidos.webConODBC.service;

import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.entity.Usuario;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;

public interface IAbmAlquilerService {
	
	List<Filial> buscarFiliales();
	
	List<Deporte> buscarDeportesByFilialesId(int filialId);

	List<Cancha> buscarCanchasDisponibles(BusquedaForm form);
	
	boolean guardarTurno(Turno turno, Usuario usuario);
	
	boolean anularTurno(int turnoId);
	
	List<Turno> getAlquileresUsuario(int usuarioId);


}
