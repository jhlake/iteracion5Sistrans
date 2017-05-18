package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tm.FestivAndesMaster;
import vos.Boleta;

public class DAOBoletas{
	
	private static final String TABLA_BOLETAS = "BOLETAS";
	private static final String ID = "ID";
	private static final String FUNCION = "FUNCION";
	private static final String SITIO = "SITIO";
	private static final String LOCALIDAD = "LOCALIDAD";
	private static final String FILA_SILLA = "FILA_SILLA";
	private static final String COLUMNA_SILLA = "COLUMNA_SILLA";
	private static final String CLIENTE = "CLIENTE";
	
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
	public DAOBoletas() {
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
	
	public boolean sillaDisponible(Boleta boleta) throws SQLException{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_BOLETAS;
		sql += " WHERE "+FUNCION+" = "+boleta.getIdFuncion();
		sql += " AND "+SITIO+" = "+boleta.getIdSitio();
		sql += " AND "+LOCALIDAD+" = "+boleta.getLocalidad();
		sql += " AND "+FILA_SILLA+" = "+boleta.getFilaSilla();
		sql += " AND "+COLUMNA_SILLA+" = "+boleta.getColumnaSilla();
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		return !rs.next();
	}
	
	public int cantidadBoletas() throws SQLException{
		String sql = "SELECT COUNT(*) AS CANTIDAD FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_BOLETAS;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		rs.next();
		return rs.getInt("CANTIDAD");
	}
	
	public Boleta registrarCompra(Boleta boleta) throws SQLException{
		String sql = "INSERT INTO "+FestivAndesMaster.ESQUEMA+"."+TABLA_BOLETAS+" VALUES (";
		int idBoleta = cantidadBoletas()+1;
		sql += ID+" = "+idBoleta+",";
		sql += FUNCION+" = "+boleta.getIdFuncion()+",";
		sql += SITIO+" = "+boleta.getIdSitio()+",";
		sql += LOCALIDAD+" = "+boleta.getLocalidad()+",";
		sql += CLIENTE+" = "+boleta.getIdCliente();
		
		if(boleta.getFilaSilla() != null && boleta.getColumnaSilla() != null){
			sql += ","+FILA_SILLA+" = "+boleta.getFilaSilla();
			sql += COLUMNA_SILLA+" = "+boleta.getColumnaSilla();
		}
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		boleta.setId(idBoleta);
		return boleta;
	}
	
	public int darBoletasVendidasNoRegistrados(int idFuncion) throws SQLException{
		String sql = "SELECT COUNT(*) AS CANTIDAD FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_BOLETAS;
		sql += " WHERE "+FUNCION+" = "+idFuncion;
		sql += " AND "+CLIENTE+" IS NULL";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		return rs.getInt("CANTIDAD");
	}
	
	public int darBoletasVendidasClientes(int idFuncion) throws SQLException{
		String sql = "SELECT COUNT(*) AS CANTIDAD FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_BOLETAS;
		sql += " WHERE "+FUNCION+" = "+idFuncion;
		sql += " AND "+CLIENTE+" IS NOT NULL";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		return rs.getInt("CANTIDAD");
	}
	
	
	
	
	
	
	
}
