package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PublicoObjetivo{
	
	@JsonProperty(value="nombre")
	private String nombre;

	public PublicoObjetivo(@JsonProperty(value="nombre") String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
