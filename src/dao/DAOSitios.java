package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tm.FestivAndesMaster;
import vos.ReporteRentabilidadC5;
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

	public ReporteRentabilidadC5 darRentabilidades(int idSitio) throws Exception {
		//NUMERO BOLETAS
		
//		String sql = "SELECT COUNT(*) as numBol FROM "+FestivAndesMaster.ESQUEMA+".BOLETAS boleta, "+FestivAndesMaster.ESQUEMA+".SITIOS sitio WHERE boleta.IDSITIO=sitio.ID AND sitio.ID=" + idSitio + ";";
//		 
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//		
//		if(!rs.next()){
//			throw new Exception("No se encontro ningun sitio con el id "+idSitio);
//		}
//		int numBol = rs.getInt("numBol");
//		rs.close();
		 //NUMERO ASISTENTES y capacidad
		String sql = "SELECT funcion.ID,COUNT(*) as numAsis FROM "+FestivAndesMaster.ESQUEMA+".FUNCIONES funcion, "+FestivAndesMaster.ESQUEMA+".SITIOS sitio, "+FestivAndesMaster.ESQUEMA+".BOLETAS boleta "
				+ "WHERE sitio.ID=" + idSitio + " AND boleta.IDSITIO=sitio.ID AND funcion.IDSITIO=sitio.ID GROUP BY funcion.ID";
		 
		PreparedStatement prepStmt2 = conn.prepareStatement(sql);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		
		if(!rs2.next()){
			throw new Exception("No se encontro ningun sitio con el id "+idSitio);
		}
		
		int numAsis = rs2.getInt("numAsis");
		rs2.close();
		
		sql = "SELECT sitio.nombre as nombre, sitio.capacidad as capacidad FROM " + FestivAndesMaster.ESQUEMA +".SITIOS sitio WHERE sitio.id = " + idSitio;
		
		PreparedStatement prepStmt4 = conn.prepareStatement(sql);
		recursos.add(prepStmt4);
		ResultSet rs4 = prepStmt4.executeQuery();
		
		if(!rs4.next()){
			throw new Exception("No se encontro ningun sitio con el id "+idSitio);
		}
		int capacidad = rs4.getInt("capacidad");
		String nombreSitio = rs4.getString("nombre");
		rs4.close();
		if(capacidad == 0)
		{
			new Exception("No se puede tener una capadidad de 0");
		}
		
		int prop = numAsis/capacidad * 100;
		
		sql= "SELECT bol.IDLOCALIDAD as localidad FROM "+ FestivAndesMaster.ESQUEMA+".BOLETAS bol, "+ FestivAndesMaster.ESQUEMA+".SITIOS sitio WHERE sitio.id = "+ idSitio+ " AND bol.idSitio = sitio.id";
		
		PreparedStatement prepStmt3 = conn.prepareStatement(sql);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		
		if(!rs3.next()){
			throw new Exception("No se encontro ningun sitio con el id "+idSitio);
		}
		
		int countPrecio = 0;
		int numBol = 0;
		while(rs3.next())
		{
			int locComp = rs3.getInt("localidad");
			if( locComp == 1)
				countPrecio+= 300;
			else if(locComp == 2)
				countPrecio+=50;
			else
				countPrecio+=600;
			numBol++;
		}
		return new ReporteRentabilidadC5(nombreSitio, numBol, numAsis, prop, countPrecio);
	}
	
	
	
	
	
	
	
}
