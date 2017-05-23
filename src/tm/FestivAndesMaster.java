package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import dao.DAOBoletas;
import dao.DAOCompaniasDeTeatro;
import dao.DAOEspectaculos;
import dao.DAOFunciones;
import dao.DAOLocalidades;
import dao.DAOPreferenciasClientes;
import dao.DAOSitios;
import dao.DAOUsuarios;
import dtm.FestivAndesDistributed;
import vos.Abonamiento;
import vos.Boleta;
import vos.Categoria;
import vos.CompaniaDeTeatro;
import vos.Espectaculo;
import vos.EspectaculoConsulta;
import vos.Funcion;
import vos.ListaAbonamientos;
import vos.ListaCompanias;
import vos.ListaFunciones;
import vos.ListaReporteRentabilidad2C5;
import vos.ListaReporteRentabilidadC5;
import vos.PublicoObjetivo;
import vos.ReporteEspectaculo;
import vos.ReporteFuncion;
import vos.ReporteFuncionC2;
import vos.ReporteFuncionC4;
import vos.ReporteLocalidadC2;
import vos.ReporteRentabilidad2C5;
import vos.ReporteRentabilidadC5;
import vos.ReporteSitioC2;
import vos.Sitio;
import vos.Usuario;

public class FestivAndesMaster {
	

	public static final String ESQUEMA = "ISIS2304B071710";
	
	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	public static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";
	

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
	
	private FestivAndesDistributed dtm;


	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public FestivAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		System.out.println("Instancing DTM...");
		dtm = FestivAndesDistributed.getInstance(this);
		System.out.println("Done!");
	}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
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

	/**
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
	 */
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
			//////TransacciÃ³n
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
	
	
	public ListaFunciones darVideosLocal() throws Exception {
		ArrayList<Funcion> videos;
		DAOFunciones daoVideos = new DAOFunciones();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVideos.setConn(conn);
			videos = daoVideos.darFunciones();

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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(videos);
	}

	public ListaReporteRentabilidadC5 darRentabilidadesLocal() throws SQLException {
		ArrayList<ReporteRentabilidadC5> rents;
		DAOSitios dao = new DAOSitios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			rents = dao.darRentabilidades();

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
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReporteRentabilidadC5(rents);
	}
	
	public ListaReporteRentabilidad2C5 darRentabilidades2Local() throws SQLException {
		ArrayList<ReporteRentabilidad2C5> rents;
		DAOEspectaculos dao = new DAOEspectaculos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			rents = dao.darRentabilidades();

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
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReporteRentabilidad2C5(rents);
	}

	public ListaCompanias retirarCompaniasLocal() throws SQLException, Exception {
		ArrayList<CompaniaDeTeatro> com;
		DAOCompaniasDeTeatro dao = new DAOCompaniasDeTeatro();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			com = dao.eliminarCompania();

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
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCompanias(com);
	}

	public ListaFunciones darFuncionesLocal() throws SQLException, Exception {
	
		ArrayList<Funcion> fun;
		DAOFunciones dao = new DAOFunciones();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			fun = dao.darFunciones();

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
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(fun);
	}

	public ListaAbonamientos darAbonamientosLocal() throws SQLException, Exception {
		ArrayList<Abonamiento> abo;
		DAOUsuarios dao = new DAOUsuarios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			abo = dao.darAbonamientos();

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
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAbonamientos(abo);
	}

	public ArrayList<Usuario> darUsuarios() throws SQLException {
		ArrayList<Usuario> abo;
		DAOUsuarios dao = new DAOUsuarios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			abo = dao.darUsuarios();

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
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return abo;
	}
	
	
	//TODO
	//----------------------------------
	
		public Date darFechaFuncion(int idFuncion) throws SQLException, Exception {
			Date resp;
			DAOFunciones dao = new DAOFunciones();
			try 
			{
				//////Transacci�n
				this.conn = darConexion();
				dao.setConn(conn);
				resp = dao.darDateFuncion(idFuncion);

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
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return resp;
		}
		
		/**
		 * RF15. REGISTRAR COMPRA DE UN ABONAMIENTO
		 * @return
		 * @throws SQLException
		 * @throws Exception
		 */
		public Abonamiento registrarCompraAbonamiento(int idEspectador, int idFuncion, int idLocalidad) throws SQLException, Exception {
			Abonamiento abo=null;
			DAOUsuarios dao = new DAOUsuarios();
			try 
			{
				//////Transacci�n
				this.conn = darConexion();
				dao.setConn(conn);
				Date fecha = darFechaFuncion(idFuncion);
				long DAY_IN_MS = 1000 * 60 * 60 * 24;
				//Válida si se realiza con 3 semanas de anticipación
				Date thatDay = new Date(System.currentTimeMillis() + (7 * 3 * DAY_IN_MS));
				
			if(fecha.after(thatDay)) abo = dao.registrarCompraAbonamiento(idEspectador, idFuncion, idLocalidad);
			else throw new Exception("No se puede comprar el abonamiento pues tiene que ser con 3 semanas de anticipacion");

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
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return abo;
		}
		
		
		
		/**
		 * RF16. RETIRAR COMPAÑIA
		 * @return
		 * @throws SQLException
		 * @throws Exception
		 */
		public CompaniaDeTeatro retirarCompania(int idUsuario, int idCompania) throws SQLException, Exception {
			CompaniaDeTeatro abo=null;
			DAOUsuarios dao = new DAOUsuarios();
			try 
			{
				//////Transacci�n
				this.conn = darConexion();
				dao.setConn(conn);
				//Usuario usuario = dao.darUsuarioPorId(idUsuario);
				//if(usuario.getRol().equals(Usuario.ADMINISTRADOR)) abo = dao.retirarCompania(idCompania);
				//else throw new Exception("Únicamente el administrador tiene permisos de realizar esta operación.");

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
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return abo;
		}
//
//		/**
//		 * RFC13. CONSULTAR FUNCIONES DISPONIBLES EN FESTIVANDES
//		 * @return
//		 * @throws SQLException
//		 * @throws Exception
//		 */
//		public ReporteRetiro consultarFuncionesDisponibles() throws SQLException, Exception {
//			ReporteRetiro abo=null;
//			DAOUsuarios dao = new DAOUsuarios();
//			try 
//			{
//				//////Transacci�n
//				this.conn = darConexion();
//				dao.setConn(conn);
////				Usuario usuario = dao.darUsuarioPorId(idUsuario);
////				if(usuario.getRol().equals(Usuario.ADMINISTRADOR)) abo = dao.retirarCompania(idCompania);
////				else throw new Exception("Únicamente el administrador tiene permisos de realizar esta operación.");
//
//			} catch (SQLException e) {
//				System.err.println("SQLException:" + e.getMessage());
//				e.printStackTrace();
//				throw e;
//			} catch (Exception e) {
//				System.err.println("GeneralException:" + e.getMessage());
//				e.printStackTrace();
//				throw e;
//			} finally {
//				try {
//					dao.cerrarRecursos();
//					if(this.conn!=null)
//						this.conn.close();
//				} catch (SQLException exception) {
//					System.err.println("SQLException closing resources:" + exception.getMessage());
//					exception.printStackTrace();
//					throw exception;
//				}
//			}
//			return abo;
//		}
		/**
		 * RFC14. CONSULTAR LA RENTABILIDAD DE LAS COMPANIAS
		 * @return
		 * @throws SQLException
		 * @throws Exception
		 */
//		public ReporteRetiro consultarRentabilidad() throws SQLException, Exception {
//			ReporteRetiro abo=null;
//			DAOUsuarios dao = new DAOUsuarios();
//			try 
//			{
//				//////Transacci�n
//				this.conn = darConexion();
//				dao.setConn(conn);
////				Usuario usuario = dao.darUsuarioPorId(idUsuario);
////				if(usuario.getRol().equals(Usuario.ADMINISTRADOR)) abo = dao.retirarCompania(idCompania);
////				else throw new Exception("Únicamente el administrador tiene permisos de realizar esta operación.");
//
//			} catch (SQLException e) {
//				System.err.println("SQLException:" + e.getMessage());
//				e.printStackTrace();
//				throw e;
//			} catch (Exception e) {
//				System.err.println("GeneralException:" + e.getMessage());
//				e.printStackTrace();
//				throw e;
//			} finally {
//				try {
//					dao.cerrarRecursos();
//					if(this.conn!=null)
//						this.conn.close();
//				} catch (SQLException exception) {
//					System.err.println("SQLException closing resources:" + exception.getMessage());
//					exception.printStackTrace();
//					throw exception;
//				}
//			}
//			return abo;
//		}

	
}
