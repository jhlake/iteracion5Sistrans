package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tm.FestivAndesMaster;

public class DAOFunciones {
	
	private static final String TABLA_FUNCIONES = "FUNCIONES";
	private static final String FECHA = "FECHA";
	private static final String ID = "ID";
	private static final String ESPECTACULO = "ESPECTACULO";
	private static final String REALIZADA = "REALIZADA";
	private static final String SITIO = "SITIO";
	
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
	public DAOFunciones() {
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
	
	public void registrarRealizacion(int idFuncion) throws SQLException{
		String sql = "UPDATE "+FestivAndesMaster.ESQUEMA+"."+TABLA_FUNCIONES+" SET ";
		sql += REALIZADA+" = 'S'";
		sql += " WHERE "+ID+" = "+idFuncion;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public String darFechaFuncion(int idFuncion) throws Exception{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_FUNCIONES;
		sql += " WHERE "+ID+" = "+idFuncion;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro la funcion con el id "+idFuncion);
		}
		return rs.getString(FECHA);
	}
	
	public int darIdEspectaculoFuncion(int idFuncion) throws Exception{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_FUNCIONES;
		sql += " WHERE "+ID+" = "+idFuncion;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro la funcion con el id "+idFuncion);
		}
		return rs.getInt(ESPECTACULO);
	}
	
	public List<Integer> idsFuncionesEspectaculo(int idEspectaculo) throws SQLException{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_FUNCIONES;
		sql += " WHERE "+ESPECTACULO+" = "+idEspectaculo;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		List<Integer> lista = new ArrayList<>();
		while(rs.next()){
			lista.add(rs.getInt(ID));
		}
		return lista;
	}
	
	public int darIdSitioFuncion(int idFuncion) throws Exception{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_FUNCIONES;
		sql += " WHERE "+ID+" = "+idFuncion;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro la funcion con el id "+idFuncion);
		}
		return rs.getInt(SITIO);
	}
	
	public List<Integer> idsFuncionesSitio(int idSitio) throws SQLException{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_FUNCIONES;
		sql += " WHERE "+SITIO+" = "+idSitio;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		List<Integer> lista = new ArrayList<>();
		while(rs.next()){
			lista.add(rs.getInt(ID));
		}
		return lista;
	}
	
	
	
	
	
	
	
	
}
