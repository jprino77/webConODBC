package sistemaDistribuidos.webConODBC.service;

import java.util.List;

import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;

public interface IAbmAlquilerService {
	
	List<Filial> buscarFiliales();
	
	List<Deporte> buscarDeportesByFilialesId(int filialId);

}
