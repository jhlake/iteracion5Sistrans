package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class EspectaculoConsulta {

	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="duracion")
	private Integer duracion;
	
	@JsonProperty(value="idioma")
	private String idioma;
	
	@JsonProperty(value="costo")
	private Double costo;
	
	@JsonProperty(value="ofreceTraduccion")
	private String ofreceTraduccion;
	
	public EspectaculoConsulta(@JsonProperty(value="id")Integer id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="duracion")Integer duracion,
			@JsonProperty(value="idioma")String idioma,
			@JsonProperty(value="costo")Double costo,
			@JsonProperty(value="ofreceTraduccion")String ofreceTraduccion) {
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.idioma = idioma;
		this.costo = costo;
		this.ofreceTraduccion = ofreceTraduccion;
	}

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

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getOfreceTraduccion() {
		return ofreceTraduccion;
	}

	public void setOfreceTraduccion(String ofreceTraduccion) {
		this.ofreceTraduccion = ofreceTraduccion;
	}
}
