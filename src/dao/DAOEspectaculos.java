package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tm.FestivAndesMaster;
import vos.Espectaculo;

public class DAOEspectaculos{
	
	public static final String TABLA_ESPECTACULOS = "ESPECTACULOS";
	public static final String ID = "ID";
	public static final String NOMBRE = "NOMBRE";
	public static final String DURACION = "DURACION";
	public static final String IDIOMA = "IDIOMA";
	public static final String COSTO = "COSTO";
	public static final String OFRECE_TRADUCCION = "OFRECE_TRADUCCION";

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOEspectaculos() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	public String darNombreEspectaculo(int idEspectaculo) throws Exception{
		String sql = "SELECT * FROM"+FestivAndesMaster.ESQUEMA+"."+TABLA_ESPECTACULOS;
		sql = " WHERE "+ID+" = "+idEspectaculo;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro el espectaculo con id "+idEspectaculo);
		}
		return rs.getString(NOMBRE);
	}
	
	public List<Espectaculo> darEspectaculosPorCriterios(List<String> atributos, List<String> valores) throws Exception{
		if(atributos.size() != valores.size()){
			throw new Exception("Todos los atributos deben tener valor");
		}
		if(atributos.isEmpty()){
			throw new Exception("Debe haber al menos un atributo");
		}
		List<Espectaculo> espectaculos = new ArrayList<>();
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_ESPECTACULOS;
		sql += " WHERE "+atributos.get(0)+" = "+valores.get(0);
		for(int i=1; i<atributos.size(); i++){
			sql += " AND "+atributos.get(i)+" = "+valores.get(i);
		}
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next()){
			int id = rs.getInt(ID);
			String nombre = rs.getString(NOMBRE);
			int duracion = rs.getInt(DURACION);
			String idioma = rs.getString(IDIOMA);
			double costo = rs.getDouble(COSTO);
			boolean ofreceTraduccion = rs.getString(OFRECE_TRADUCCION).equalsIgnoreCase("S");
			Espectaculo espectaculo = new Espectaculo(id, nombre, duracion, idioma, costo, ofreceTraduccion);
			espectaculos.add(espectaculo);
		}
		return espectaculos;
	}
	
	
	
	
	
	
	
	
	
	
	
}
