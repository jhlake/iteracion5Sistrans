package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteFuncionC4{

	@JsonProperty(value="fecha")
	private String fecha;

	@JsonProperty(value="boletasVendidas")
	private int boletasVendidas;
	
	@JsonProperty(value="dineroRecaudado")
	private double dineroRecaudado;
	
	@JsonProperty(value="porcentajeOcupacion")
	private double porcentajeOcupacion;

	public ReporteFuncionC4(@JsonProperty(value="fecha") String fecha,
			@JsonProperty(value="boletasVendidas") int boletasVendidas,
			@JsonProperty(value="dineroRecaudado") double dineroRecaudado,
			@JsonProperty(value="porcentajeOcupacion") double porcentajeOcupacion) {
		
		this.fecha = fecha;
		this.boletasVendidas = boletasVendidas;
		this.dineroRecaudado = dineroRecaudado;
		this.porcentajeOcupacion = porcentajeOcupacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public double getPorcentajeOcupacion() {
		return porcentajeOcupacion;
	}

	public void setPorcentajeOcupacion(double porcentajeOcupacion) {
		this.porcentajeOcupacion = porcentajeOcupacion;
	}
}
