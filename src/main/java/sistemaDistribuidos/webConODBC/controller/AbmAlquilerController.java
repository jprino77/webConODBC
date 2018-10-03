package sistemaDistribuidos.webConODBC.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sistemaDistribuidos.webConODBC.entity.Cancha;
import sistemaDistribuidos.webConODBC.entity.Deporte;
import sistemaDistribuidos.webConODBC.entity.Filial;
import sistemaDistribuidos.webConODBC.entity.Turno;
import sistemaDistribuidos.webConODBC.entity.Usuario;
import sistemaDistribuidos.webConODBC.model.BusquedaForm;
import sistemaDistribuidos.webConODBC.service.IAbmAlquilerService;
import sistemaDistribuidos.webConODBC.service.ISecurityService;

@Controller
@RequestMapping("/alquiler")
public class AbmAlquilerController {

	@Autowired
	IAbmAlquilerService abmService;

	@Autowired
	ISecurityService securityService;

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

	@ModelAttribute("turno")
	public Turno initTurno(Map<String, Object> model) {

		return new Turno();
	}

	@RequestMapping(value = "/buscarCanchas", method = RequestMethod.GET)
	public String altaGet(Map<String, Object> model) {
		return "altaAlquiler";
	}

	@RequestMapping(value = "/buscarCanchas", method = RequestMethod.POST)
	public String buscarCanchas(Map<String, Object> model, @ModelAttribute("busquedaForm") BusquedaForm busquedaForm,
			HttpServletRequest request) {
		List<Cancha> canchas = abmService.buscarCanchasDisponibles(busquedaForm);
		model.put("canchas", canchas);

		if (canchas.isEmpty()) {

			model.put("msg", "No se encontraron Canchas disponibles en esa fecha y hora");
		}
		this.getDepotesByFilialIdMap(model, busquedaForm.getFilial());
		return "altaAlquiler";
	}

	@RequestMapping(value = "/bajaModificacionAlquiler", method = RequestMethod.GET)
	public String bajaModificacion(Map<String, Object> model) {
		
		Usuario u = securityService.getUsuarioLogeado();
		
		List<Turno> turnos = abmService.getAlquileresUsuario(u.getNumeroAfiliadoLegajo());
		
		if(turnos.isEmpty()) {
			model.put("msg", "No se encontraron Alquileres para el usuario");

		}else {
			model.put("turnos",turnos);
		}
		
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

	@RequestMapping(value = "/alquilar", method = RequestMethod.POST)
	@ResponseBody
	public boolean alquilar(HttpServletResponse response, Map<String, Object> model,
			@ModelAttribute("turno") Turno turno) {

		return abmService.guardarTurno(turno, securityService.getUsuarioLogeado());

	}
	
	@RequestMapping(value = "/anular/{turnoId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean alquilar(HttpServletResponse response, Map<String, Object> model,
			@PathVariable("turnoId") int turnoId) {

		
		return abmService.anularTurno(turnoId);

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
