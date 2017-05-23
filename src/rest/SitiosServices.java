package rest;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dtm.FestivAndesDistributed;
import tm.FestivAndesMaster;
import vos.ReporteSitioC2;
import vos.ListaReporteRentabilidadC5;
import vos.ReporteRentabilidadC5;
import jms.AllRentabilidadMDB;

@Path("sitios")
public class SitiosServices {

	@Context
	private ServletContext context;
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	
	
	
	
	@GET
	@Path("/{idSitio}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reporteFuncion(@PathParam("idSitio") int idSitio){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ReporteSitioC2 reporteSitioC2;
		try {
			reporteSitioC2 = tm.reporteSitio(idSitio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporteSitioC2).build();
	}
	
	
	@GET
	@Path("rentabilidades")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response darRentabilidadesSitio(@QueryParam("IDSITIO")int idSitio){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ReporteRentabilidadC5 reporteSitioC2;
		try {
			reporteSitioC2 = tm.darRentabilidadesLocal(idSitio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporteSitioC2).build();
	}
	
//	@GET
//	@Path("distri")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response darRentabilidadesSitioDistribuido(@QueryParam("factory") TopicConnectionFactory factory,
//			@QueryParam("ctx") InitialContext ctx) throws JMSException, NamingException{
//			
//		AllRentabilidadMDB jms = new AllRentabilidadMDB(factory, ctx);
//		ListaReporteRentabilidadC5 reporteSitioC2;
//		try {
//			reporteSitioC2 = jms.getRemoteReporteRentabilidadC5();
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(reporteSitioC2).build();
//	}
	
	
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
			
			
			int numberApps = Integer.parseInt(prop.getProperty("numberApps"));
			
			manager.setUpJMSManager(numberApps, myQueue, topicAllRentabilidad);	
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
