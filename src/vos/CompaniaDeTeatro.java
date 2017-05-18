package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CompaniaDeTeatro {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="representante")
	private String representante;
	
	@JsonProperty(value="paisOrigen")
	private String paisOrigen;
	
	@JsonProperty(value="urlWeb")
	private String urlWeb;
	
	@JsonProperty(value="fechaLlegada")
	private String fechaLlegada;
	
	@JsonProperty(value="fechaSalida")
	private String fechaSalida;
	
	@JsonProperty(value="festival")
	private int festival;
	
	public CompaniaDeTeatro(@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="representante") String representante, 
			@JsonProperty(value="paisOrigen") String paisOrigen, 
			@JsonProperty(value="urlWeb") String urlWeb,
			@JsonProperty(value="fechaLlegada") String fechaLlegada,
			@JsonProperty(value="fechaSalida") String fechaSalida,
			@JsonProperty(value="festival") int festival
			) {
		this.id = id;
		this.nombre = nombre;
		this.representante = representante;
		this.paisOrigen = paisOrigen;
		this.urlWeb = urlWeb;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
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
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public String getPaisOrigen() {
		return paisOrigen;
	}
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	public String getUrlWeb() {
		return urlWeb;
	}
	public void setUrlWeb(String urlWeb) {
		this.urlWeb = urlWeb;
	}
	public String getFechaLlegada() {
		return fechaLlegada;
	}
	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}
	public String getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
}
