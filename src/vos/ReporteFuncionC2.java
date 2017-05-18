package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteFuncionC2{

	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="nombreEspectaculo")
	private String nombreEspectaculo;
	
	@JsonProperty(value="reportesLocalidadesC2")
	private List<ReporteLocalidadC2> reportesLocalidadesC2;

	public ReporteFuncionC2(@JsonProperty(value="fecha") String fecha,
			@JsonProperty(value="nombreEspectaculo") String nombreEspectaculo,
			@JsonProperty(value="reportesLocalidadesC2") List<ReporteLocalidadC2> reportesLocalidadesC2) {
		super();
		this.fecha = fecha;
		this.nombreEspectaculo = nombreEspectaculo;
		this.reportesLocalidadesC2 = reportesLocalidadesC2;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombreEspectaculo() {
		return nombreEspectaculo;
	}

	public void setNombreEspectaculo(String nombreEspectaculo) {
		this.nombreEspectaculo = nombreEspectaculo;
	}

	public List<ReporteLocalidadC2> getReportesLocalidadesC2() {
		return reportesLocalidadesC2;
	}

	public void setReportesLocalidadesC2(List<ReporteLocalidadC2> reportesLocalidadesC2) {
		this.reportesLocalidadesC2 = reportesLocalidadesC2;
	}
}
