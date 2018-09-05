package sistemaDistribuidos.webConODBC.entity;

import java.time.LocalDate;

public class Usuario {

	private Integer numeroAfiliadoLegajo;
	private String nombre;
	private String calle;
	private Integer altura;
	private Localidad localidad;
	private String telefono;
	private String email;
	private LocalDate fechaNAcimiento;
	private String usuario;
	private String clave;
	private Rol rol;
	private boolean activo;

	public Integer getNumeroAfiliadoLegajo() {
		return numeroAfiliadoLegajo;
	}

	public void setNumeroAfiliadoLegajo(Integer numeroAfiliadoLegajo) {
		this.numeroAfiliadoLegajo = numeroAfiliadoLegajo;
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

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechaNAcimiento() {
		return fechaNAcimiento;
	}

	public void setFechaNAcimiento(LocalDate fechaNAcimiento) {
		this.fechaNAcimiento = fechaNAcimiento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
