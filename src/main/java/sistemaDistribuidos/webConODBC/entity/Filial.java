package sistemaDistribuidos.webConODBC.entity;

import java.util.List;

public class Filial {

	private Integer id;
	private String nombre;
	private String calle;
	private String altura;
	private Localidad localidad;
	private String email;
	private List<HorariosFilial> horariosFilials;
	private boolean activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<HorariosFilial> getHorariosFilials() {
		return horariosFilials;
	}

	public void setHorariosFilials(List<HorariosFilial> horariosFilials) {
		this.horariosFilials = horariosFilials;
	}

}
