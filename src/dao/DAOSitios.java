package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tm.FestivAndesMaster;
import vos.Sitio;

public class DAOSitios {
	
	private static final String TABLA_SITIOS = "SITIOS";
	private static final String ID = "ID";
	private static final String CAPACIDAD = "CAPACIDAD";
	
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
	public DAOSitios() {
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
	
	public int darCapacidadSitio(int idSitio) throws Exception{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_SITIOS;
		sql += " WHERE "+ID+" = "+idSitio;
		 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro ningun sitio con el id "+idSitio);
		}
		return rs.getInt(CAPACIDAD);
	}
	
	public Sitio darSitio(int idSitio) throws Exception{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_SITIOS;
		sql += " WHERE "+ID+" = "+idSitio;
		 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro ningun sitio con el id "+idSitio);
		}
		return new Sitio(rs.getInt("ID"),
				rs.getString("NOMBRE"), 
				rs.getInt("CAPACIDAD"),
				rs.getString("APTODISCAPACIDADES").equalsIgnoreCase("S"),
				rs.getString("HORARIO"),
				rs.getString("TIPO_SILLETERIA"),
				rs.getString("PROTECCION_CLIMA").equalsIgnoreCase("S"));
	}
	
	
	
	
	
	
	
}
