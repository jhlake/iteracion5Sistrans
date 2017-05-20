package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReporteRentabilidad2C5 {
	@JsonProperty(value="rentabilidades")
	private List<ReporteRentabilidad2C5> rentabilidades;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaReporteRentabilidad2C5( @JsonProperty(value="rentabilidades")List<ReporteRentabilidad2C5> rentabilidades){
		this.rentabilidades = rentabilidades;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<ReporteRentabilidad2C5> getRentabilidades() {
		return rentabilidades;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setFuncion(List<ReporteRentabilidad2C5> rentabilidades) {
		this.rentabilidades = rentabilidades;
	}
}
