package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Abonamiento {
	
	@JsonProperty("idAbono")
	private int idAbono;
	
	@JsonProperty("fecha")
	private Date fecha;
	
	
	public Abonamiento(@JsonProperty("idAbono") int idAbono,
			@JsonProperty("fecha") Date fecha) 
	{
		this.idAbono = idAbono;
		this.fecha = fecha;
		
	}


	public int getIdAbono() {
		return idAbono;
	}


	public void setIdAbono(int idAbono) {
		this.idAbono = idAbono;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	
	
}
