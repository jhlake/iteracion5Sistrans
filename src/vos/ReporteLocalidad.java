package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteLocalidad{
	
	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="boletasVendidas")
	private int boletasVendidas;
	
	@JsonProperty(value="dineroRecaudado")
	private double dineroRecaudado;

	public ReporteLocalidad(@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="boletasVendidas") int boletasVendidas,
			@JsonProperty(value="dineroRecaudado") double dineroRecaudado) {
		this.nombre = nombre;
		this.boletasVendidas = boletasVendidas;
		this.dineroRecaudado = dineroRecaudado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getBoletasVendidas() {
		return boletasVendidas;
	}

	public void setBoletasVendidas(int boletasVendidas) {
		this.boletasVendidas = boletasVendidas;
	}

	public double getDineroRecaudado() {
		return dineroRecaudado;
	}

	public void setDineroRecaudado(double dineroRecaudado) {
		this.dineroRecaudado = dineroRecaudado;
	}
}
