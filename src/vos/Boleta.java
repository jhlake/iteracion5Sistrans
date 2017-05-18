package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Boleta{

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="idFuncion")
	private int idFuncion;
	
	@JsonProperty(value="idSitio")
	private int idSitio;
	
	@JsonProperty(value="localidad")
	private int localidad;
	
	@JsonProperty(value="filaSilla")
	private Integer filaSilla;
	
	@JsonProperty(value="columnaSilla")
	private Integer columnaSilla;
	
	@JsonProperty(value="idCliente")
	private Integer idCliente;

	public Boleta(@JsonProperty(value="id")int id,
			@JsonProperty(value="idFuncion")int idFuncion,
			@JsonProperty(value="idSitio")int idSitio,
			@JsonProperty(value="localidad")int localidad,
			@JsonProperty(value="filaSilla")Integer filaSilla,
			@JsonProperty(value="columnaSilla")Integer columnaSilla,
			@JsonProperty(value="idCliente")Integer idCliente) {
		this.id = id;
		this.idFuncion = idFuncion;
		this.idSitio = idSitio;
		this.localidad = localidad;
		this.filaSilla = filaSilla;
		this.columnaSilla = columnaSilla;
		this.idCliente = idCliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}

	public int getIdSitio() {
		return idSitio;
	}

	public void setIdSitio(int idSitio) {
		this.idSitio = idSitio;
	}

	public int getLocalidad() {
		return localidad;
	}

	public void setLocalidad(int localidad) {
		this.localidad = localidad;
	}

	public Integer getFilaSilla() {
		return filaSilla;
	}

	public void setFilaSilla(Integer filaSilla) {
		this.filaSilla = filaSilla;
	}

	public Integer getColumnaSilla() {
		return columnaSilla;
	}

	public void setColumnaSilla(Integer columnaSilla) {
		this.columnaSilla = columnaSilla;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
}
