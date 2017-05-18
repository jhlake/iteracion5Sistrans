package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.Espectaculo;
import vos.EspectaculoConsulta;
import vos.ReporteEspectaculo;

@Path("espectaculos")
public class EspectaculosServices{

	@Context
	private ServletContext context;
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Path("/reportes/{idEspectaculo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reporteFuncion(@PathParam("idEspectaculo") int idEspectaculo){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ReporteEspectaculo reporteEspectaculo;
		try {
			reporteEspectaculo = tm.reporteEspectaculo(idEspectaculo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporteEspectaculo).build();
	}
	
	@GET
	@Path("/multicriterio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaMulticriterioEspectaculo(EspectaculoConsulta espectaculo){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		List<Espectaculo> espectaculos;
		try {
			espectaculos = tm.darEspectaculosPorCriterios(espectaculo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espectaculos).build();
	}
}
