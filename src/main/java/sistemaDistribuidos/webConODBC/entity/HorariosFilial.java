package sistemaDistribuidos.webConODBC.entity;

import java.time.LocalTime;

public class HorariosFilial {

	private Integer id;
	private int diaSemana;
	private LocalTime horaDesde;
	private LocalTime hasta;
	private Filial filial;
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

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
