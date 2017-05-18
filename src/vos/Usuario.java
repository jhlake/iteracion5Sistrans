package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario{
	
	public static final String ADMINISTRADOR = "A";
	public static final String ORGANIZADOR = "O";
	public static final String CLIENTE = "C";
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="email")
	private String email;
	
	@JsonProperty(value="rol")
	private String rol;
	
	
	public Usuario(@JsonProperty(value="id") int id,
			@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="email") String email,
			@JsonProperty(value="rol") String rol) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}
