package sistemaDistribuidos.webConODBC.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/alquiler")
public class AbmAlquilerController {

	
	@RequestMapping(value = "/altaAlquiler", method=RequestMethod.GET)
	public String altaGet(Map<String, Object> model) {
		return "altaAlquiler";
	}
	
	@RequestMapping(value = "/altaAlquiler", method=RequestMethod.POST)
	public String altaPost(Map<String, Object> model) {
		return "altaAlquiler";
	}
	
	@RequestMapping(value = "/bajaModificacionAlquiler", method=RequestMethod.GET)
	public String bajaModificacion(Map<String, Object> model) {
		return "bajaModificacionAlquiler";
	}
}
