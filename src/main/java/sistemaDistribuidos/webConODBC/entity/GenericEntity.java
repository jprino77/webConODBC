package sistemaDistribuidos.webConODBC.entity;
/**
 * Entidad que sirve de base por tener datos comunes a varias tablas
 *
 */
public abstract class GenericEntity {
	
	private Integer id;
	private String codigo;
	private String descripcion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
