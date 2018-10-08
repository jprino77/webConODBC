package sistemaDistribuidos.webConODBC.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class BusquedaForm {

	private Integer filial;
	private Integer deporte;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaAlquiler;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaAlquilerHasta;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaInicio;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaFin;

	public Integer getFilial() {
		return filial;
	}

	public void setFilial(Integer filial) {
		this.filial = filial;
	}

	public Integer getDeporte() {
		return deporte;
	}

	public void setDeporte(Integer deporte) {
		this.deporte = deporte;
	}

	public LocalDate getFechaAlquiler() {
		return fechaAlquiler;
	}

	public void setFechaAlquiler(LocalDate fechaAlquiler) {
		this.fechaAlquiler = fechaAlquiler;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public LocalDate getFechaAlquilerHasta() {
		return fechaAlquilerHasta;
	}

	public void setFechaAlquilerHasta(LocalDate fechaAlquilerHasta) {
		this.fechaAlquilerHasta = fechaAlquilerHasta;
	}

	public LocalDateTime getFechahoraInicio() {
		return LocalDateTime.of(this.fechaAlquiler, this.horaInicio);
	}

	public LocalDateTime getFechahoraFin() {
		return LocalDateTime.of(this.fechaAlquiler, this.horaFin);
	}

}
