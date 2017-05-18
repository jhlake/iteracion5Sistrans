package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.ReporteFuncion;

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
}
