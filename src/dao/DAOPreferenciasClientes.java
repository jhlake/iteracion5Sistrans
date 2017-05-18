package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import tm.FestivAndesMaster;
import vos.Categoria;
import vos.PublicoObjetivo;

public class DAOPreferenciasClientes{
	
	private static final String TABLA_CATEGORIAS = "PREFIERECATEGORIA";
	private static final String TABLA_PUBLICO_OBJETIVO = "PREFIEREPUBLICOOBJETIVO";
	private static final String CLIENTE = "CLIENTE";
	private static final String CATEGORIA = "CATEGORIA";
	private static final String PUBLICO_OBJETIVO = "PUBLICOOBJETIVO";
	
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
	public DAOPreferenciasClientes() {
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
	
	public void registrarPreferenciaCategoria(int idCliente, Categoria categoria) throws Exception{
		String sql = "INSERT INTO "+FestivAndesMaster.ESQUEMA+"."+TABLA_CATEGORIAS+" VALUES (";
		sql += idCliente + ",";
		sql += "'" + categoria.getNombre() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void eliminarPreferenciaCategoria(int idCliente, Categoria categoria) throws Exception{
		String sql = "DELETE FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_CATEGORIAS;
		sql += " WHERE "+CLIENTE+" = "+idCliente;
		sql += " AND "+CATEGORIA+" = '"+categoria.getNombre()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void registrarPreferenciaPublicoObjetivo(int idCliente, PublicoObjetivo publicoObjetivo) throws Exception{
		String sql = "INSERT INTO "+FestivAndesMaster.ESQUEMA+"."+TABLA_PUBLICO_OBJETIVO+" VALUES (";
		sql += idCliente + ",";
		sql += "'" + publicoObjetivo.getNombre() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void eliminarPreferenciaPublicoObjetivo(int idCliente, PublicoObjetivo publicoObjetivo) throws Exception{
		String sql = "DELETE FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_PUBLICO_OBJETIVO;
		sql += " WHERE "+CLIENTE+" = "+idCliente;
		sql += " AND "+PUBLICO_OBJETIVO+" = '"+publicoObjetivo.getNombre()+"'";
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
