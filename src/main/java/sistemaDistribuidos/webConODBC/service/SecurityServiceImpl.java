package sistemaDistribuidos.webConODBC.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sistemaDistribuidos.webConODBC.entity.Usuario;

@Service
public class SecurityServiceImpl implements ISecurityService {

	@Override
	public Usuario getUsuarioLogeado() {
		
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuario;
	}

}
