package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFunciones {
	@JsonProperty(value="funciones")
	private List<Funcion> funciones;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaFunciones( @JsonProperty(value="funciones")List<Funcion> funciones){
		this.funciones = funciones;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Funcion> getFunciones() {
		return funciones;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setFuncion(List<Funcion> funciones) {
		this.funciones = funciones;
	}
}
