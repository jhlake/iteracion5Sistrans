package vos;

import java.time.LocalDateTime;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcion{

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="fechaFuncion")
	private String fechaFuncion;
	
	@JsonProperty(value="realizada")
	private boolean realizada;
	
	public Funcion(@JsonProperty(value="id") int id,
			@JsonProperty(value="fechaFuncion") String fechaFuncion,
			@JsonProperty(value="realizada") boolean realizada) {
		this.id = id;
		this.fechaFuncion = fechaFuncion;
		this.realizada = realizada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFechaFuncion() {
		return fechaFuncion;
	}

	public void setFechaFuncion(String fechaFuncion) {
		this.fechaFuncion = fechaFuncion;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}
}
