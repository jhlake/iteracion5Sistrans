package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DAOBoletas;
import dao.DAOEspectaculos;
import dao.DAOFunciones;
import dao.DAOLocalidades;
import dao.DAOPreferenciasClientes;
import dao.DAOSitios;
import dao.DAOUsuarios;
import vos.Boleta;
import vos.Categoria;
import vos.Espectaculo;
import vos.EspectaculoConsulta;
import vos.PublicoObjetivo;
import vos.ReporteEspectaculo;
import vos.ReporteFuncion;
import vos.ReporteFuncionC2;
import vos.ReporteFuncionC4;
import vos.ReporteLocalidadC2;
import vos.ReporteSitioC2;
import vos.Sitio;

public class FestivAndesMaster {
	
	public static final String ESQUEMA = "ISIS2304B161710";
	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * Conexión a la base de datos
	 */
	private Connection conn;
	
	public FestivAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}
	
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}
	
	/*
	public boolean esCliente(int idUsuario) throws SQLException{
		DAOUsuarios daoUsuarios = new DAOUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			boolean esCliente = daoUsuarios.esCliente(idUsuario);
			conn.commit();
			return esCliente;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void registrarPreferenciaCategoria(int idCliente, Categoria categoria) throws Exception {
		DAOPreferenciasClientes daoPreferenciasClientes = new DAOPreferenciasClientes();
		try 
		{
			this.conn = darConexion();
			daoPreferenciasClientes.setConn(conn);
			
			if(!esCliente(idCliente)){
				throw new Exception("El id no corresponde a ningun cliente");
			}
			
			daoPreferenciasClientes.registrarPreferenciaCategoria(idCliente, categoria);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferenciasClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	*/
	
	public ReporteSitioC2 reporteSitio(int idSitio) throws Exception{
		DAOFunciones daoFunciones = new DAOFunciones(); 
		DAOLocalidades daoLocalidades = new DAOLocalidades();
		DAOEspectaculos daoEspectaculos = new DAOEspectaculos();
		DAOSitios daoSitios = new DAOSitios();
		try 
		{
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			daoLocalidades.setConn(conn);
			daoEspectaculos.setConn(conn);
			daoSitios.setConn(conn);
			List<Integer> idsFunciones = daoFunciones.idsFuncionesSitio(idSitio);
			List<ReporteFuncionC2> reportesFuncionesC2 = new ArrayList<>();
			for(int idFuncion : idsFunciones){
				String fecha = daoFunciones.darFechaFuncion(idFuncion);
				String nombreEspectaculo = daoEspectaculos.darNombreEspectaculo(daoFunciones.darIdEspectaculoFuncion(idFuncion));
				List<ReporteLocalidadC2> reportesLocalidadesC2 = daoLocalidades.reporteLocalidadesFuncionC2(idFuncion);
				reportesFuncionesC2.add(new ReporteFuncionC2(fecha, nombreEspectaculo, reportesLocalidadesC2));
			}
			Sitio sitio = daoSitios.darSitio(idSitio);
			ReporteSitioC2 reporteSitioC2 = new ReporteSitioC2(sitio.getId(),
					sitio.getNombre(),
					sitio.getCapacidad(),
					sitio.isAptoDiscapacidades(),
					sitio.getHorario(),
					sitio.getTipoSilleteria(),
					sitio.isProteccionClima(), reportesFuncionesC2);
			conn.commit();
			return reporteSitioC2;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				daoLocalidades.cerrarRecursos();
				daoEspectaculos.cerrarRecursos();
				daoSitios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public List<Espectaculo> darEspectaculosPorCriterios(EspectaculoConsulta espectaculo) throws Exception{
		DAOEspectaculos daoEspectaculos = new DAOEspectaculos();
		try 
		{
			this.conn = darConexion();
			daoEspectaculos.setConn(conn);
			List<String> atributos = new ArrayList<>();
			List<String> valores = new ArrayList<>();
			if(espectaculo.getNombre() != null){
				atributos.add(DAOEspectaculos.NOMBRE);
				valores.add(espectaculo.getNombre());
			}
			if(espectaculo.getDuracion() != null){
				atributos.add(DAOEspectaculos.DURACION);
				valores.add(""+espectaculo.getDuracion());
			}
			if(espectaculo.getIdioma() != null){
				atributos.add(DAOEspectaculos.IDIOMA);
				valores.add(espectaculo.getIdioma());
			}
			if(espectaculo.getOfreceTraduccion() != null){
				atributos.add(DAOEspectaculos.OFRECE_TRADUCCION);
				valores.add(espectaculo.getOfreceTraduccion());
			}
			List<Espectaculo> espectaculos = daoEspectaculos.darEspectaculosPorCriterios(atributos, valores);
			conn.commit();
			return espectaculos;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoEspectaculos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ReporteEspectaculo reporteEspectaculo(int idEspectaculo) throws Exception{
		DAOFunciones daoFunciones = new DAOFunciones();
		DAOEspectaculos daoEspectaculos = new DAOEspectaculos();
		DAOSitios daoSitios = new DAOSitios();
		try 
		{
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			daoEspectaculos.setConn(conn);
			daoSitios.setConn(conn);
			List<Integer> idsFunciones = daoFunciones.idsFuncionesEspectaculo(idEspectaculo);
			String nombreEspectaculo = daoEspectaculos.darNombreEspectaculo(idEspectaculo);
			List<ReporteFuncionC4> reportesFuncionesC4 = new ArrayList<>();
			List<ReporteFuncion> reporteFunciones = new ArrayList<>();
			int boletasVendidas = 0;
			double dineroRecaudado = 0;
			int boletasVendidasNoReg = 0;
			int boletasVendidasClientes = 0;
			for(int idFuncion : idsFunciones){
				reporteFunciones.add(reporteFuncion(idFuncion));
			}
			for(ReporteFuncion reporteFuncion : reporteFunciones){
				String fecha = reporteFuncion.getFecha();
				int boletasVendidasFuncion = reporteFuncion.getBoletasVendidas();
				double dineroRecaudadoFuncion = reporteFuncion.getDineroRecaudado();
				int idSitioFuncion = daoFunciones.darIdSitioFuncion(reporteFuncion.getId());
				double porcentajeOcupacion = (((double)daoSitios.darCapacidadSitio(idSitioFuncion))/daoSitios.darCapacidadSitio(idSitioFuncion))*100;
				ReporteFuncionC4 reporteFuncionC4 = new ReporteFuncionC4(fecha, boletasVendidasFuncion, dineroRecaudadoFuncion, porcentajeOcupacion);
				reportesFuncionesC4.add(reporteFuncionC4);
				boletasVendidas += boletasVendidasFuncion;
				dineroRecaudado += dineroRecaudadoFuncion;
				boletasVendidasNoReg += reporteFuncion.getBoletasVendidasNoReg();
				boletasVendidasClientes += reporteFuncion.getBoletasVendidaClientes();
			}
			ReporteEspectaculo reporteEspectaculo = new ReporteEspectaculo(nombreEspectaculo, boletasVendidas, dineroRecaudado, boletasVendidasNoReg, boletasVendidasClientes, reportesFuncionesC4);
			conn.commit();
			return reporteEspectaculo;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				daoEspectaculos.cerrarRecursos();
				daoSitios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ReporteFuncion reporteFuncion(int idFuncion) throws Exception {
		DAOLocalidades daoLocalidades = new DAOLocalidades();
		DAOBoletas daoBoletas = new DAOBoletas();
		DAOFunciones daoFunciones = new DAOFunciones();
		try 
		{
			this.conn = darConexion();
			daoLocalidades.setConn(conn);
			daoBoletas.setConn(conn);
			daoFunciones.setConn(conn);
			ReporteFuncion reporteFuncion = daoLocalidades.reporteFuncion(idFuncion);
			reporteFuncion.setBoletasVendidaClientes(daoBoletas.darBoletasVendidasClientes(idFuncion));
			reporteFuncion.setBoletasVendidasNoReg(daoBoletas.darBoletasVendidasNoRegistrados(idFuncion));
			reporteFuncion.setFecha(daoFunciones.darFechaFuncion(idFuncion));
			reporteFuncion.setId(idFuncion);
			conn.commit();
			return reporteFuncion;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoLocalidades.cerrarRecursos();
				daoBoletas.cerrarRecursos();
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void registrarPreferenciaCategoria(int idCliente, Categoria categoria) throws Exception {
		DAOUsuarios daoUsuarios = new DAOUsuarios();
		DAOPreferenciasClientes daoPreferenciasClientes = new DAOPreferenciasClientes();
		try 
		{
			this.conn = darConexion();
			daoPreferenciasClientes.setConn(conn);
			daoUsuarios.setConn(conn);
			if(!daoUsuarios.esCliente(idCliente)){
				throw new Exception("El id no corresponde a ningun cliente");
			}
			daoPreferenciasClientes.registrarPreferenciaCategoria(idCliente, categoria);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferenciasClientes.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void eliminarPreferenciaCategoria(int idCliente, Categoria categoria) throws Exception {
		DAOUsuarios daoUsuarios = new DAOUsuarios();
		DAOPreferenciasClientes daoPreferenciasClientes = new DAOPreferenciasClientes();
		try 
		{
			this.conn = darConexion();
			daoPreferenciasClientes.setConn(conn);
			daoUsuarios.setConn(conn);
			if(!daoUsuarios.esCliente(idCliente)){
				throw new Exception("El id no corresponde a ningun cliente");
			}
			daoPreferenciasClientes.eliminarPreferenciaCategoria(idCliente, categoria);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferenciasClientes.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void registrarPreferenciaPublicoObjetivo(int idCliente, PublicoObjetivo publicoObjetivo) throws Exception {
		DAOUsuarios daoUsuarios = new DAOUsuarios();
		DAOPreferenciasClientes daoPreferenciasClientes = new DAOPreferenciasClientes();
		try 
		{
			this.conn = darConexion();
			daoPreferenciasClientes.setConn(conn);
			daoUsuarios.setConn(conn);
			if(!daoUsuarios.esCliente(idCliente)){
				throw new Exception("El id no corresponde a ningun cliente");
			}
			daoPreferenciasClientes.registrarPreferenciaPublicoObjetivo(idCliente, publicoObjetivo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferenciasClientes.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void eliminarPreferenciaPublicoObjetivo(int idCliente, PublicoObjetivo publicoObjetivo) throws Exception {
		DAOUsuarios daoUsuarios = new DAOUsuarios();
		DAOPreferenciasClientes daoPreferenciasClientes = new DAOPreferenciasClientes();
		try 
		{
			this.conn = darConexion();
			daoPreferenciasClientes.setConn(conn);
			daoUsuarios.setConn(conn);
			if(!daoUsuarios.esCliente(idCliente)){
				throw new Exception("El id no corresponde a ningun cliente");
			}
			daoPreferenciasClientes.eliminarPreferenciaPublicoObjetivo(idCliente, publicoObjetivo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferenciasClientes.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public Boleta registrarCompra(Boleta boleta) throws Exception{
		DAOLocalidades daoLocalidades = new DAOLocalidades();
		DAOBoletas daoBoletas = new DAOBoletas();
		DAOUsuarios daoUsuarios = new DAOUsuarios();
		try 
		{
			this.conn = darConexion();
			daoLocalidades.setConn(conn);
			daoBoletas.setConn(conn);
			daoUsuarios.setConn(conn);
			if(!daoLocalidades.haySillasDisponibles(boleta)){
				throw new Exception("No hay cupo");
			}
			if(daoLocalidades.localidadEsEnumerada(boleta)){
				if(boleta.getFilaSilla() == null || boleta.getColumnaSilla() == null){
					throw new Exception("Se necesita la ubicacion completa de la silla");
				}
				if(!daoBoletas.sillaDisponible(boleta)){
					throw new Exception("La silla ya se encuentra ocupada");
				}
			}
			if(boleta.getIdCliente() != null && !daoUsuarios.esCliente(boleta.getIdCliente())){
				throw new Exception("El cliente no se encuentra en el sistema");
			}
			daoLocalidades.registrarCompra(boleta);
			Boleta boletaAgregada = daoBoletas.registrarCompra(boleta);
			conn.commit();
			return boletaAgregada;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoLocalidades.cerrarRecursos();
				daoBoletas.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void registrarRealizacionFuncion(int idFuncion) throws SQLException{
		DAOFunciones daoFunciones = new DAOFunciones();
		try 
		{
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			daoFunciones.registrarRealizacion(idFuncion);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/*
	public void agregarCompaniaDeTeatro(CompaniaDeTeatro compania) throws Exception {
		DAOCompaniasDeTeatro daoCompanias = new DAOCompaniasDeTeatro();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCompanias.setConn(conn);
			daoCompanias.agregarCompaniaDeTeatro(compania);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCompanias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	*/
}
