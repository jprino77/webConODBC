package sistemaDistribuidos.webConODBC.entity;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class HorariosFilial {

	private Integer id;
	private int diaSemana;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaDesde;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime hasta;
	private boolean activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}

	public LocalTime getHoraDesde() {
		return horaDesde;
	}

	public void setHoraDesde(LocalTime horaDesde) {
		this.horaDesde = horaDesde;
	}

	public LocalTime getHasta() {
		return hasta;
	}

	public void setHasta(LocalTime hasta) {
		this.hasta = hasta;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
