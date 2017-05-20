package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteRentabilidadC5 {

	
	@JsonProperty("sitio")
	private String nombreSitio;
	
	@JsonProperty("numeroBoletas")
	private int numeroBoletasVendidas;
	
	@JsonProperty("numeroAsistentes")
	private int numeroAsistentes;
	
	@JsonProperty("proporcion")
	private int proporcion;
	
	@JsonProperty("valorTotal")
	private int valorTotal;
	
	public ReporteRentabilidadC5(@JsonProperty("nombreSitio") String nombreSitio,
			@JsonProperty("numeroBoletas") int numeroBoletasVendidas,
			@JsonProperty("numeroAsistentes") int numeroAsistentes,
			@JsonProperty("proporcion") int proporcion,
			@JsonProperty("valorTotal") int valorTotal) 
	{
		this.nombreSitio = nombreSitio;
		this.numeroBoletasVendidas = numeroBoletasVendidas;
		this.numeroAsistentes = numeroAsistentes;
		this.proporcion = proporcion;
		this.valorTotal = valorTotal;
	}

	public String getNombreSitio() {
		return nombreSitio;
	}

	public void setNombreSitio(String nombreSitio) {
		this.nombreSitio = nombreSitio;
	}

	public int getNumeroBoletasVendidas() {
		return numeroBoletasVendidas;
	}

	public void setNumeroBoletasVendidas(int numeroBoletasVendidas) {
		this.numeroBoletasVendidas = numeroBoletasVendidas;
	}

	public int getNumeroAsistentes() {
		return numeroAsistentes;
	}

	public void setNumeroAsistentes(int numeroAsistentes) {
		this.numeroAsistentes = numeroAsistentes;
	}

	public int getProporcion() {
		return proporcion;
	}

	public void setProporcion(int proporcion) {
		this.proporcion = proporcion;
	}

	public int getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
	
	
}
