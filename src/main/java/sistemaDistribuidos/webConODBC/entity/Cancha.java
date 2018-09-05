package sistemaDistribuidos.webConODBC.entity;

public class Cancha {

	private Integer id;
	private String codigo;
	private Deporte deporte;
	private TipoCancha tipoCancha;
	private Filial filial;
	private boolean desponible;

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

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public TipoCancha getTipoCancha() {
		return tipoCancha;
	}

	public void setTipoCancha(TipoCancha tipoCancha) {
		this.tipoCancha = tipoCancha;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public boolean isDesponible() {
		return desponible;
	}

	public void setDesponible(boolean desponible) {
		this.desponible = desponible;
	}

}
