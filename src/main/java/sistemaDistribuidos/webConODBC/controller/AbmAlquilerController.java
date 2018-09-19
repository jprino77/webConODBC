package sistemaDistribuidos.webConODBC.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;
import sistemaDistribuidos.webConODBC.service.IAbmAlquilerService;

@Controller
@RequestMapping("/alquiler")
public class AbmAlquilerController {

	@Autowired
	IAbmAlquilerService abmService;

	@ModelAttribute("busquedaForm")
	public BusquedaForm initForm(Map<String, Object> model) {
		BusquedaForm busquedaForm = new BusquedaForm();
		Map<Integer, String> filialMap = new HashMap<Integer, String>();

		List<Filial> filial = abmService.buscarFiliales();

		//Lambda expression para llenar map a utilizar en el dropdown
		filialMap = filial.stream().collect(Collectors.toMap(Filial::getId, Filial::getNombre));
		model.put("filialMap", filialMap);
		return busquedaForm;
	}

	@RequestMapping(value = "/altaAlquiler", method = RequestMethod.GET)
	public String altaGet(Map<String, Object> model) {
		return "altaAlquiler";
	}

	@RequestMapping(value = "/altaAlquiler", method = RequestMethod.POST)
	public String altaPost(Map<String, Object> model, @ModelAttribute("busquedaForm") BusquedaForm busquedaForm) {
		return "altaAlquiler";
	}

	@RequestMapping(value = "/bajaModificacionAlquiler", method = RequestMethod.GET)
	public String bajaModificacion(Map<String, Object> model) {
		return "bajaModificacionAlquiler";
	}
}
