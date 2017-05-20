package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCompanias {
	@JsonProperty(value="companias")
	private List<CompaniaDeTeatro> companias;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaCompanias( @JsonProperty(value="companias")List<CompaniaDeTeatro> companias){
		this.companias = companias;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<CompaniaDeTeatro> getCompanias() {
		return companias;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setCompania(List<CompaniaDeTeatro> companias) {
		this.companias = companias;
	}
}
