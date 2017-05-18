package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteSitioC2{

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	@JsonProperty(value="aptoEspeciales")
	private boolean aptoEspeciales;
	
	@JsonProperty(value="horario")
	private String horario;
	
	@JsonProperty(value="tipoSilleteria")
	private String tipoSilleteria;
	
	@JsonProperty(value="proteccionClima")
	private boolean proteccionClima;
	
	@JsonProperty(value="reportesFuncionesC2")
	private List<ReporteFuncionC2> reportesFuncionesC2; 

	public ReporteSitioC2(@JsonProperty(value="id") int id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="capacidad")int capacidad,
			@JsonProperty(value="aptoEspeciales")boolean aptoEspeciales,
			@JsonProperty(value="horario")String horario,
			@JsonProperty(value="tipoSilleteria")String tipoSilleteria,
			@JsonProperty(value="proteccionClima")boolean proteccionClima,
			@JsonProperty(value="reportesFuncionesC2") List<ReporteFuncionC2> reportesFuncionesC2) {
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.aptoEspeciales = aptoEspeciales;
		this.horario = horario;
		this.tipoSilleteria = tipoSilleteria;
		this.proteccionClima = proteccionClima;
		this.reportesFuncionesC2 = reportesFuncionesC2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isAptoEspeciales() {
		return aptoEspeciales;
	}

	public void setAptoEspeciales(boolean aptoEspeciales) {
		this.aptoEspeciales = aptoEspeciales;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getTipoSilleteria() {
		return tipoSilleteria;
	}

	public void setTipoSilleteria(String tipoSilleteria) {
		this.tipoSilleteria = tipoSilleteria;
	}

	public boolean isProteccionClima() {
		return proteccionClima;
	}

	public void setProteccionClima(boolean proteccionClima) {
		this.proteccionClima = proteccionClima;
	}

	public List<ReporteFuncionC2> getReportesFuncionesC2() {
		return reportesFuncionesC2;
	}

	public void setReportesFuncionesC2(List<ReporteFuncionC2> reportesFuncionesC2) {
		this.reportesFuncionesC2 = reportesFuncionesC2;
	}
}
