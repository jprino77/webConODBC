package sistemaDistribuidos.webConODBC.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import sistemaDistribuidos.webConODBC.entity.HorariosFilial;
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
	public String buscarCanchasGet(Map<String, Object> model) {
		model.put("titulo", "Alquilar");
		return "altaAlquiler";
	}

	@RequestMapping(value = "/buscarCanchas", method = RequestMethod.POST)
	public String buscarCanchasPost(Map<String, Object> model,
			@ModelAttribute("busquedaForm") BusquedaForm busquedaForm, HttpServletRequest request) {
		model.put("titulo", "Alquilar");
		this.getCanchas(model, busquedaForm, false);
		this.getDepotesByFilialIdMap(model, busquedaForm.getFilial());
		return "altaAlquiler";
	}

	@RequestMapping(value = "/buscarCanchasMod", method = RequestMethod.POST)
	public String buscarCanchasModPost(HttpServletResponse response, Map<String, Object> model,
			@ModelAttribute("busquedaForm") BusquedaForm busquedaForm, HttpServletRequest request) {

		LocalDateTime hoyAddOneHour = LocalDateTime.now().plusHours(2);
		if (busquedaForm.getFechahoraInicio().equals(busquedaForm.getFechahoraFin())) {
			model.put("msgResponse", "El horario de incio debe ser menor al horario fin");
		} else if (hoyAddOneHour.isAfter(busquedaForm.getFechahoraInicio())) { // Valido que la fecha hora inicio
																				// del alquiler sea menor a la hora
																				// actual mas 2
			model.put("msgResponse", "El horario de incio debe ser por lo menos 2 horas mayor a la hora actual");
		} else {
			List<Cancha> canchas = this.getCanchas(model, busquedaForm, true);

			if (canchas.isEmpty()) {
				model.put("msgResponse", "No se encontraron Canchas disponibles en esa fecha y hora");

			}

		}

		return "datatableAlquiler";
	}

	@RequestMapping(value = "/bajaModificacionAlquiler", method = RequestMethod.GET)
	public String bajaModificacionGet(Map<String, Object> model) {
		model.put("titulo", "Baja/Modificacion");
		return "bajaModificacionAlquiler";
	}

	@RequestMapping(value = "/bajaModificacionAlquiler", method = RequestMethod.POST)
	public String bajaModificacionPost(Map<String, Object> model,
			@ModelAttribute("busquedaForm") BusquedaForm busquedaForm, HttpServletRequest request) {
		model.put("titulo", "Baja/Modificacion");
		Usuario u = securityService.getUsuarioLogeado();

		List<Turno> turnos = abmService.getAlquileresUsuario(u.getNumeroAfiliadoLegajo(), busquedaForm);

		if (turnos.isEmpty()) {
			model.put("msg", "No se encontraron Alquileres para el usuario");

		} else {
			model.put("turnos", turnos);
		}
		this.getDepotesByFilialIdMap(model, busquedaForm.getFilial());

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

	@RequestMapping(value = "/diasFilial", method = RequestMethod.POST)
	@ResponseBody
	public List<Integer> getDiasFilial(HttpServletResponse response, Map<String, Object> model,
			@RequestParam int filialId) {

		List<Integer> diasFilial = abmService.buscarDiasInhabilitadosByFilialId(filialId);

		return diasFilial;

	}

	@RequestMapping(value = "/horasFilial", method = RequestMethod.POST)
	@ResponseBody
	public HorariosFilial getHorasFilial(HttpServletResponse response, Map<String, Object> model,
			@RequestParam int filialId, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaAlquiler) {

		HorariosFilial horariosFilial = abmService.buscarHorasFilialByFilialId(filialId, fechaAlquiler).get(0);

		return horariosFilial;

	}

	@RequestMapping(value = "/alquilar", method = RequestMethod.POST)
	@ResponseBody
	public boolean alquilar(HttpServletResponse response, Map<String, Object> model,
			@ModelAttribute("turno") Turno turno) {

		return abmService.guardarOActualizarTurno(turno, securityService.getUsuarioLogeado());

	}

	@RequestMapping(value = "/anular/{turnoId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean alquilar(HttpServletResponse response, Map<String, Object> model,
			@PathVariable("turnoId") int turnoId) {

		return abmService.anularTurno(turnoId);

	}

	private List<Cancha> getCanchas(Map<String, Object> model, BusquedaForm busquedaForm, boolean esModificacion) {

		LocalDateTime hoyAddOneHour = LocalDateTime.now().plusHours(1);
		List<Cancha> canchas = new ArrayList<Cancha>();

		if (busquedaForm.getFechahoraInicio().equals(busquedaForm.getFechahoraFin())) {
			model.put("msg", "El horario de incio debe ser menor al horario fin");

		} else if (hoyAddOneHour.isAfter(busquedaForm.getFechahoraInicio())) { // Valido que la fecha hora inicio del
																				// alquiler sea menor a la hora actual
																				// mas 1
			model.put("msg", "El horario de incio debe ser por lo menos 1 hora mayor a la hora actual");
		} else {
			canchas = abmService.buscarCanchasDisponibles(busquedaForm);

			model.put("canchas", canchas);
			model.put("esModificacion", esModificacion);

			if (canchas.isEmpty()) {

				model.put("msg", "No se encontraron Canchas disponibles en esa fecha y hora");
			}
		}

		model.put("busquedaForm", busquedaForm);

		return canchas;

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
