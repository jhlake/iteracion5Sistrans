package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteEspectaculo{

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="boletasVendidas")
	private int boletasVendidas;
	
	@JsonProperty(value="dineroRecaudado")
	private double dineroRecaudado;
	
	@JsonProperty(value="boletasVendidasNoReg")
	private int boletasVendidasNoReg;
	
	@JsonProperty(value="boletasVendidaClientes")
	private int boletasVendidaClientes;
	
	@JsonProperty(value="reportesFunciones")
	private List<ReporteFuncionC4> reportesFunciones;

	public ReporteEspectaculo(@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="boletasVendidas") int boletasVendidas,
			@JsonProperty(value="dineroRecaudado") double dineroRecaudado,
			@JsonProperty(value="boletasVendidasNoReg") int boletasVendidasNoReg,
			@JsonProperty(value="boletasVendidaClientes") int boletasVendidaClientes,
			@JsonProperty(value="reportesFunciones") List<ReporteFuncionC4> reportesFunciones) {
		
		this.nombre = nombre;
		this.boletasVendidas = boletasVendidas;
		this.dineroRecaudado = dineroRecaudado;
		this.boletasVendidasNoReg = boletasVendidasNoReg;
		this.boletasVendidaClientes = boletasVendidaClientes;
		this.reportesFunciones = reportesFunciones;
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

	public int getBoletasVendidasNoReg() {
		return boletasVendidasNoReg;
	}

	public void setBoletasVendidasNoReg(int boletasVendidasNoReg) {
		this.boletasVendidasNoReg = boletasVendidasNoReg;
	}

	public int getBoletasVendidaClientes() {
		return boletasVendidaClientes;
	}

	public void setBoletasVendidaClientes(int boletasVendidaClientes) {
		this.boletasVendidaClientes = boletasVendidaClientes;
	}

	public List<ReporteFuncionC4> getReportesFunciones() {
		return reportesFunciones;
	}

	public void setReportesFunciones(List<ReporteFuncionC4> reportesFunciones) {
		this.reportesFunciones = reportesFunciones;
	}
}
