package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Espectaculo{
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="duracion")
	private int duracion;
	
	@JsonProperty(value="idioma")
	private String idioma;
	
	@JsonProperty(value="costo")
	private double costo;
	
	@JsonProperty(value="ofreceTraduccion")
	private boolean ofreceTraduccion;
	
	public Espectaculo(@JsonProperty(value="id")int id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="duracion") int duracion,
			@JsonProperty(value="idioma")String idioma,
			@JsonProperty(value="costo")double costo,
			@JsonProperty(value="ofreceTraduccion") boolean ofreceTraduccion) {
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.idioma = idioma;
		this.costo = costo;
		this.ofreceTraduccion = ofreceTraduccion;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public boolean isOfreceTraduccion() {
		return ofreceTraduccion;
	}
	public void setOfreceTraduccion(boolean ofreceTraduccion) {
		this.ofreceTraduccion = ofreceTraduccion;
	}
}
