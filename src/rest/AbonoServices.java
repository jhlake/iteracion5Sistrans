/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package rest;


import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dtm.FestivAndesDistributed;
import tm.FestivAndesMaster;
import vos.Abonamiento;
import vos.ListaAbonamientos;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/Iteracion5Sisttans/rest/admin/...
 */
@Path("abonos")
public class AbonoServices {

	// Servicios REST tipo GET:
	


	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	/**
	 * Método que retorna el error formateo para responder el Json en caso de Exception
	 * @return String con el Json del error
	 */
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
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
			String topicAllRentabilidad = prop.getProperty("topicAllRentabilidad");	
			String topicAllRentabilidad2 = prop.getProperty("topicAllRentabilidad2");
			String topicAllFunciones = prop.getProperty("topicAllFunciones");
			String topicAllAbonamientos = prop.getProperty("topicAllAbonamientos");
			String topicAllRetirarCompania = prop.getProperty("topicAllRetirarCompania");
			
			int numberApps = Integer.parseInt(prop.getProperty("numberApps"));
			
			manager.setUpJMSManager(numberApps, myQueue, topicAllRentabilidad);	
			manager.setUpJMSManager(numberApps, myQueue, topicAllRentabilidad2);
			manager.setUpJMSManager(numberApps, myQueue, topicAllFunciones);
			manager.setUpJMSManager(numberApps, myQueue, topicAllAbonamientos);
			manager.setUpJMSManager(numberApps, myQueue, topicAllRetirarCompania);	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response darAbonamientos(){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaAbonamientos reporte;
		try {
			reporte = tm.darAbonamientosLocal();
					} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporte).build();
	}

	
	
	//TODO pruebas
		@GET
		@Path("comprar")
		@Produces(MediaType.APPLICATION_JSON)
		public Response comprarAbonamiento(@QueryParam("idEspectador") int idEspectador,
				@QueryParam("idFuncion") int idFuncion, @QueryParam("idLocalidad") int idLocalidad,
				@QueryParam("idSitio") int idSitio){
			FestivAndesMaster tm = new FestivAndesMaster(getPath());
			Abonamiento reporte;
			try {
				reporte = tm.registrarCompraAbonamiento(idEspectador, idFuncion, idLocalidad, idSitio);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(reporte).build();
		}
			
	

}
