package sistemaDistribuidos.webConODBC.model;

public class BusquedaForm {

	private Integer filial;
	private Integer deporte;
	private String fechaAlquiler;
	private String horaInicio;

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

	public String getFechaAlquiler() {
		return fechaAlquiler;
	}

	public void setFechaAlquiler(String fechaAlquiler) {
		this.fechaAlquiler = fechaAlquiler;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

}
