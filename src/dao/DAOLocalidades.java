package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tm.FestivAndesMaster;
import vos.Boleta;
import vos.ReporteFuncion;
import vos.ReporteFuncionC2;
import vos.ReporteLocalidad;
import vos.ReporteLocalidadC2;

public class DAOLocalidades{

	private static final String TABLA_LOCALIDADES = "LOCALIDADES";
	private static final String FUNCION = "FUNCION";
	private static final String SITIO = "SITIO";
	private static final String LOCALIDAD = "LOCALIDAD";
	private static final String TARIFA = "PRECIO";
	private static final String CAPACIDAD = "CAPACIDAD";
	private static final String SILLAS_DISPONIBLES = "SILLASDISPONIBLES";
	private static final String SILLAS_OCUPADAS = "SILLASOCUPADAS";
	private static final String ES_ENUMERADA = "ESNUMERADA";
	
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
	public DAOLocalidades() {
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
	
	public ReporteFuncion reporteFuncion(int idFuncion) throws SQLException{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_LOCALIDADES;
		sql += " WHERE "+FUNCION+" = "+idFuncion;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		int totalBoletasVendidas = 0;
		double totalDineroRecaudado = 0;
		List<ReporteLocalidad> reportesLocalidades = new ArrayList<>();
		while(rs.next()){
			String localidadActual = rs.getString(LOCALIDAD);
			int boletasVendidasLocalidad = rs.getInt(SILLAS_OCUPADAS);
			double dineroRecaudadoLocalidad = boletasVendidasLocalidad*rs.getDouble(TARIFA);
			ReporteLocalidad reporteLocalidad = new ReporteLocalidad(localidadActual, boletasVendidasLocalidad, dineroRecaudadoLocalidad);
			reportesLocalidades.add(reporteLocalidad);
			totalBoletasVendidas += boletasVendidasLocalidad;
			totalDineroRecaudado += dineroRecaudadoLocalidad;
		}
		return new ReporteFuncion(idFuncion, "", totalBoletasVendidas, totalDineroRecaudado, -1, -1, reportesLocalidades);	
	}
	
	public List<ReporteLocalidadC2> reporteLocalidadesFuncionC2(int idFuncion) throws SQLException{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_LOCALIDADES;
		sql += " WHERE "+FUNCION+" = "+idFuncion;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		List<ReporteLocalidadC2> reportesLocalidadesC2 = new ArrayList<>();
		while(rs.next()){
			String nombre = rs.getString(LOCALIDAD);
			double tarifa = rs.getDouble(TARIFA);
			int boleteriaDisp = rs.getInt(SILLAS_DISPONIBLES);
			reportesLocalidadesC2.add(new ReporteLocalidadC2(nombre, tarifa, boleteriaDisp));
		}
		return reportesLocalidadesC2;
	}
	
	public boolean haySillasDisponibles(Boleta boleta) throws Exception{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_LOCALIDADES;
		sql += " WHERE "+FUNCION+" = "+boleta.getIdFuncion();
		sql += " AND "+SITIO+" = "+boleta.getIdSitio();
		sql += " AND "+LOCALIDAD+" = "+boleta.getLocalidad();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro la localidad de la boleta");
		}
		
		int sillasDisponibles = rs.getInt(SILLAS_DISPONIBLES);
		return sillasDisponibles > 0;
	}
	
	public boolean localidadEsEnumerada(Boleta boleta) throws Exception{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+"."+TABLA_LOCALIDADES;
		sql += " WHERE "+FUNCION+" = "+boleta.getIdFuncion();
		sql += " AND "+SITIO+" = "+boleta.getIdSitio();
		sql += " AND "+LOCALIDAD+" = "+boleta.getLocalidad();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next()){
			throw new Exception("No se encontro la localidad de la boleta");
		}
		
		return rs.getString(ES_ENUMERADA).equals("S");
	}
	
	public void registrarCompra(Boleta boleta) throws SQLException{
		String sql = "UPDATE "+FestivAndesMaster.ESQUEMA+"."+TABLA_LOCALIDADES+" SET ";
		sql += SILLAS_DISPONIBLES+" = "+SILLAS_DISPONIBLES+" - 1,";
		sql += SILLAS_OCUPADAS+" = "+SILLAS_OCUPADAS+" + 1";
		sql += " WHERE "+FUNCION+" = "+boleta.getIdFuncion();
		sql += " AND "+SITIO+" = "+boleta.getIdSitio();
		sql += " AND "+LOCALIDAD+" = "+boleta.getLocalidad();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	
	
	
	
	
	
}
