package sistemaDistribuidos.webConODBC.service;

import sistemaDistribuidos.webConODBC.entity.Usuario;

public interface ILoginService {

	Usuario getByUsuarioClave(String usuario, String clave);
}
