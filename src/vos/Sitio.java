package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Sitio{

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	@JsonProperty(value="aptoDiscapacidades")
	private boolean aptoDiscapacidades;
	
	@JsonProperty(value="horario")
	private String horario;
	
	@JsonProperty(value="tipoSilleteria")
	private String tipoSilleteria;
	
	@JsonProperty(value="proteccionClima")
	private boolean proteccionClima;

	public Sitio(@JsonProperty(value="id") int id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="capacidad")int capacidad,
			@JsonProperty(value="aptoDiscapacidades")boolean aptoDiscapacidades,
			@JsonProperty(value="horario")String horario,
			@JsonProperty(value="tipoSilleteria")String tipoSilleteria,
			@JsonProperty(value="proteccionClima")boolean proteccionClima) {
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.aptoDiscapacidades = aptoDiscapacidades;
		this.horario = horario;
		this.tipoSilleteria = tipoSilleteria;
		this.proteccionClima = proteccionClima;
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

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isAptoDiscapacidades() {
		return aptoDiscapacidades;
	}

	public void setAptoDiscapacidades(boolean aptoDiscapacidades) {
		this.aptoDiscapacidades = aptoDiscapacidades;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getTipoSilleteria() {
		return tipoSilleteria;
	}

	public void setTipoSilleteria(String tipoSilleteria) {
		this.tipoSilleteria = tipoSilleteria;
	}

	public boolean isProteccionClima() {
		return proteccionClima;
	}

	public void setProteccionClima(boolean proteccionClima) {
		this.proteccionClima = proteccionClima;
	}
}
