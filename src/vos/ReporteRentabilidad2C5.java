package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteRentabilidad2C5 {

	
	@JsonProperty("espectaculo")
	private String nombreEspectaculo;
	
	@JsonProperty("numeroBoletas")
	private int numeroBoletasVendidas;
	
	@JsonProperty("numeroAsistentes")
	private int numeroAsistentes;
	
	@JsonProperty("proporcion")
	private int proporcion;
	
	@JsonProperty("valorTotal")
	private int valorTotal;
	
	public ReporteRentabilidad2C5(@JsonProperty("nombreEspectaculo") String nombreEspectaculo,
			@JsonProperty("numeroBoletas") int numeroBoletasVendidas,
			@JsonProperty("numeroAsistentes") int numeroAsistentes,
			@JsonProperty("proporcion") int proporcion,
			@JsonProperty("valorTotal") int valorTotal) 
	{
		this.nombreEspectaculo = nombreEspectaculo;
		this.numeroBoletasVendidas = numeroBoletasVendidas;
		this.numeroAsistentes = numeroAsistentes;
		this.proporcion = proporcion;
		this.valorTotal = valorTotal;
	}

	public String getNombreEspectaculo() {
		return nombreEspectaculo;
	}

	public void setNombreEspectaculo(String nombreEspectaculo) {
		this.nombreEspectaculo = nombreEspectaculo;
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
