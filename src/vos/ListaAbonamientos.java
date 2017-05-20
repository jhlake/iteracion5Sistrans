package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAbonamientos {
	@JsonProperty(value="abonamientos")
	private List<Abonamiento> abonamientos;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaAbonamientos( @JsonProperty(value="abonamientos")List<Abonamiento> abonamientos){
		this.abonamientos = abonamientos;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Abonamiento> getAbonamientos() {
		return abonamientos;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setAbonamiento(List<Abonamiento> abonamientos) {
		this.abonamientos = abonamientos;
	}
}
