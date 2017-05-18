package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.CompaniaDeTeatro;

public class DAOCompaniasDeTeatro {

	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	public DAOCompaniasDeTeatro(){
		recursos = new ArrayList<>();
	}
	
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
	
	public void setConn(Connection con){
		this.conn = con;
	}
	
	public void agregarCompaniaDeTeatro(CompaniaDeTeatro compania) throws SQLException, Exception{
		String sql = "INSERT INTO ISIS2304B201710.COMPANIAS_DE_TEATRO VALUES (";
		sql += compania.getId() + ",'";
		sql += compania.getNombre() + "','";
		sql += compania.getRepresentante() + "','";
		sql += compania.getPaisOrigen() + "','";
		sql += compania.getPaginaWeb() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
