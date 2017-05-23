package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tm.FestivAndesMaster;
import vos.Abonamiento;
import vos.Usuario;

public class DAOUsuarios{
	
	private static final String TABLA_USUARIOS = "USUARIOS";
	private static final String ID = "ID";
	private static final String ROL = "ROL";
	
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
	public DAOUsuarios() {
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
	
	public boolean esCliente(int idUsuario) throws SQLException{
		
		String sql = "SELECT COUNT(*) AS CANTIDAD FROM "+FestivAndesMaster.ESQUEMA+".USUARIOS";
		sql += " WHERE "+ID+" = "+idUsuario;
		sql += " AND "+ROL+" = '"+Usuario.CLIENTE+"'";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		boolean s = rs.next();
		int cant = rs.getInt("CANTIDAD");

		return cant == 1;
	}

	public ArrayList<Abonamiento> darAbonamientos() {
		// TODO Crear sentencia SQL	
		return null;
	}

	public ArrayList<Usuario> darUsuarios() throws SQLException {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+".CLIENTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			int id = Integer.parseInt(rs.getString("IDENTIFICACION"));
			String correo = rs.getString("EMAIL");			
			String rol = rs.getString("ROL");
			
			usuarios.add(new Usuario(id, nombre, correo, rol));
		}
			
		return usuarios;
	}

	public Abonamiento registrarCompraAbonamiento(int idEspectador, int idFuncion, int idLocalidad) throws SQLException {
		
		int id = (int) Math.random();
		int idAbono =(int) Math.random();
		Abonamiento abonamiento = getAbono(idAbono);
		
		if(abonamiento==null) abonamiento = crearAbono(idAbono);
		
		String sql = "INSERT INTO BOLETAS VALUES(" + id + "," + idLocalidad + "," + idEspectador +"," + idFuncion + "," + idAbono + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		
		String sql1 = "UPDATE SILLAS SET IDBOLETA=" + id + "WHERE COD="	+ 3;
		
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		
		return abonamiento;
	}
	
	public Abonamiento crearAbono(int idAbono) throws SQLException{
		Date hoy = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String ans=dateFormat.format(hoy);
		
		String sql = "INSERT INTO "+FestivAndesMaster.ESQUEMA+".ABONAMIENTOS VALUES (";
		sql += idAbono + ",";
		sql += "(TO_DATE('"+ans+"', 'dd/MM/yyyy')))";

		System.out.println("SQL stmt:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);

		return new Abonamiento(idAbono, hoy);

	}
	
	public Abonamiento getAbono(int id){
		Abonamiento abonamiento=null;
		try{
		String sql = "SELECT * FROM "+FestivAndesMaster.ESQUEMA+".ABONAMIENTOS WHERE IDABONO="+id;
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		abonamiento = new Abonamiento(rs.getInt("IDABONO"),rs.getDate("FECHA"));
		}catch(Exception e){return abonamiento;}
		return abonamiento;
	}
	
	
	
}
