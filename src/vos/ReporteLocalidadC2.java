package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteLocalidadC2{

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="boleteriaDisp")
	private int boleteriaDisp;

	public ReporteLocalidadC2(@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="precio") double precio,
			@JsonProperty(value="boleteriaDisp") int boleteriaDisp) {
		
		this.nombre = nombre;
		this.precio = precio;
		this.boleteriaDisp = boleteriaDisp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double tarifa) {
		this.precio = tarifa;
	}

	public int getBoleteriaDisp() {
		return boleteriaDisp;
	}

	public void setBoleteriaDisp(int boleteriaDisp) {
		this.boleteriaDisp = boleteriaDisp;
	}
}
