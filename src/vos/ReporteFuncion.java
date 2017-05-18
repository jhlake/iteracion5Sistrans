package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteFuncion{
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="fecha")
	private String fecha;

	@JsonProperty(value="boletasVendidas")
	private int boletasVendidas;
	
	@JsonProperty(value="dineroRecaudado")
	private double dineroRecaudado;
	
	@JsonProperty(value="boletasVendidasNoReg")
	private int boletasVendidasNoReg;
	
	@JsonProperty(value="boletasVendidaClientes")
	private int boletasVendidaClientes;
	
	@JsonProperty(value="reportesLocalidades")
	private List<ReporteLocalidad> reportesLocalidades;

	public ReporteFuncion(@JsonProperty(value="id") int id,
			@JsonProperty(value="fecha") String fecha,
			@JsonProperty(value="boletasVendidas") int boletasVendidas,
			@JsonProperty(value="dineroRecaudado") double dineroRecaudado,
			@JsonProperty(value="boletasVendidasNoReg") int boletasVendidasNoReg,
			@JsonProperty(value="boletasVendidaClientes") int boletasVendidaClientes,
			@JsonProperty(value="reportesLocalidades") List<ReporteLocalidad> reportesLocalidades) {
		this.fecha = fecha;
		this.boletasVendidas = boletasVendidas;
		this.dineroRecaudado = dineroRecaudado;
		this.boletasVendidasNoReg = boletasVendidasNoReg;
		this.boletasVendidaClientes = boletasVendidaClientes;
		this.reportesLocalidades = reportesLocalidades;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<ReporteLocalidad> getReportesLocalidades() {
		return reportesLocalidades;
	}

	public void setReportesLocalidades(List<ReporteLocalidad> reportesLocalidades) {
		this.reportesLocalidades = reportesLocalidades;
	}
}
