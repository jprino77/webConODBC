package sistemaDistribuidos.webConODBC.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
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

		// Lambda expression para llenar map a utilizar en el dropdown
		filialMap = filial.stream().collect(Collectors.toMap(Filial::getId, Filial::getNombre));
		model.put("filialMap", filialMap);

		return busquedaForm;
	}

	@RequestMapping(value = "/buscarCanchas", method = RequestMethod.GET)
	public String altaGet(Map<String, Object> model) {
		return "altaAlquiler";
	}

	@RequestMapping(value = "/buscarCanchas", method = RequestMethod.POST)
	public String buscarCanchas(Map<String, Object> model, @ModelAttribute("busquedaForm") BusquedaForm busquedaForm) {
		List<Cancha> canchas = abmService.buscarCanchaByDeporteAndFilial(busquedaForm.getFilial(),
				busquedaForm.getDeporte());
		model.put("canchas", canchas);
		this.getDepotesByFilialIdMap(model, busquedaForm.getFilial());

		return "altaAlquiler";
	}

	@RequestMapping(value = "/bajaModificacionAlquiler", method = RequestMethod.GET)
	public String bajaModificacion(Map<String, Object> model) {
		return "bajaModificacionAlquiler";
	}

	@RequestMapping(value = "/deportes", method = RequestMethod.POST)
	@ResponseBody
	public List<Deporte> getDeportesFilial(HttpServletResponse response, Map<String, Object> model,
			@RequestParam int filialId) {
		List<Deporte> deportes = this.getDeportesByFilialId(filialId);

		if (deportes.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		return deportes;

	}

	private void getDepotesByFilialIdMap(Map<String, Object> model, int filialId) {
		List<Deporte> deportes = this.getDeportesByFilialId(filialId);
		Map<Integer, String> deporteMap = new HashMap<Integer, String>();
		deporteMap = deportes.stream().collect(Collectors.toMap(Deporte::getId, Deporte::getDescripcion));

		model.put("deporteMap", deporteMap);

	}

	private List<Deporte> getDeportesByFilialId(int filialId) {

		return abmService.buscarDeportesByFilialesId(filialId);
	}
}
