package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Abonamiento {
	
	@JsonProperty("usuario")
	private int idUsuario;
	
	@JsonProperty("funcion")
	private String nombreFuncion;
	
	@JsonProperty("valorAbono")
	private double valorAbono;
	
	//1 realizada, 0 rechazada
	@JsonProperty("estado")
	private int estado;
	
	public Abonamiento(@JsonProperty("usuario") int idUsuario,
			@JsonProperty("funcion") String nombreFuncion,			
			@JsonProperty("valorAbono") double valorAbono,
			@JsonProperty("estado") int estado) 
	{
		this.idUsuario = idUsuario;
		this.nombreFuncion = nombreFuncion;
		this.valorAbono = valorAbono;
		this.estado = estado;	
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreFuncion() {
		return nombreFuncion;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}

	public double getValorAbono() {
		return valorAbono;
	}

	public void setValorAbono(double valorAbono) {
		this.valorAbono = valorAbono;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
}
