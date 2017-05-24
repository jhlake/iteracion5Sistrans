package rest;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dtm.FestivAndesDistributed;
import tm.FestivAndesMaster;
import vos.Funcion;
import vos.ListaFunciones;
import vos.ReporteFuncion;
import vos.Usuario;

@Path("funciones")
public class FuncionesServices{

	@Context
	private ServletContext context;
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@PUT
	@Path("/registrarRealizacion/{idFuncion}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarRealizacionFuncion(@PathParam("idFuncion") int idFuncion){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.registrarRealizacionFuncion(idFuncion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idFuncion).build();
	}
	
	@GET
	@Path("/reportes/{idFuncion}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reporteFuncion(@PathParam("idFuncion") int idFuncion){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ReporteFuncion reporteFuncion;
		try {
			reporteFuncion = tm.reporteFuncion(idFuncion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporteFuncion).build();
	}
	
	
	//TODO pruebas
	@GET
	@Path("prueba")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pruebaDarFecha(){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Date reporteFuncion;
		try {
			reporteFuncion = tm.darFechaFuncion(2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporteFuncion).build();
	}
	
	
	/**
	 * Método que expone servicio REST usando GET que da todos los objetos de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/FestivAndes/rest/usuarios
	 * @return Json con todos los objetos de la base de datos O json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFunciones() {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaFunciones lista;
		try {
			lista = tm.darFuncionesLocal();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lista).build();
	}
	
	/**
	 * Método que expone servicio REST usando POST para inicializar los servicios de JMS
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/admin/jmsInit
	 */
	@POST
	@Path("/jmsInit")
	public Response initApp() {
		try {
			FestivAndesDistributed manager = FestivAndesDistributed.getInstance(new FestivAndesMaster(getPath()));
			initDataFromFile(manager);
			System.out.println("InitApp1");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	/**
	 * Método que inicializa los atributos basicos de JMSManager
	 * 
	 */
	public void initDataFromFile(FestivAndesDistributed manager) {
		try {
			String contextPathP = context.getRealPath("WEB-INF/ConnectionData");
			String connectionDataPath = contextPathP + FestivAndesMaster.CONNECTION_DATA_FILE_NAME_REMOTE;
			
			File arch = new File(connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			String myQueue = prop.getProperty("myQueue");
//			String topicAllRentabilidad = prop.getProperty("topicAllRentabilidad");	
//			String topicAllRentabilidad2 = prop.getProperty("topicAllRentabilidad2");
			String topicAllFunciones = prop.getProperty("topicAllFunciones");
//			String topicAllAbonamientos = prop.getProperty("topicAllAbonamientos");
//			String topicAllRetirarCompania = prop.getProperty("topicAllRetirarCompania");
//			
			int numberApps = Integer.parseInt(prop.getProperty("numberApps"));
			
//			manager.setUpJMSManager(numberApps, myQueue, topicAllRentabilidad);	
//			manager.setUpJMSManager(numberApps, myQueue, topicAllRentabilidad2);
			manager.setUpJMSManager(numberApps, myQueue, topicAllFunciones);
//			manager.setUpJMSManager(numberApps, myQueue, topicAllAbonamientos);
//			manager.setUpJMSManager(numberApps, myQueue, topicAllRetirarCompania);	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
