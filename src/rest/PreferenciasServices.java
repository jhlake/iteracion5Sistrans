package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.Categoria;
import vos.PublicoObjetivo;

@Path("preferencias")
public class PreferenciasServices{

	@Context
	private ServletContext context;
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@POST
	@Path("/{idCliente}/categorias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarPreferenciaCategoria(@PathParam("idCliente") int idCliente, Categoria categoria){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.registrarPreferenciaCategoria(idCliente, categoria);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(categoria).build();
	}
	
	@DELETE
	@Path("/{idCliente}/categorias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarPreferenciaCategoria(@PathParam("idCliente") int idCliente, Categoria categoria){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.eliminarPreferenciaCategoria(idCliente, categoria);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(categoria).build();
	}
	
	@POST
	@Path("/{idCliente}/publicoObjetivo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarPreferenciaPublicoObjetivo(@PathParam("idCliente") int idCliente, PublicoObjetivo publicoObjetivo){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.registrarPreferenciaPublicoObjetivo(idCliente, publicoObjetivo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(publicoObjetivo).build();
	}
	
	@DELETE
	@Path("/{idCliente}/publicoObjetivo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarPreferenciaPublicoObjetivo(@PathParam("idCliente") int idCliente, PublicoObjetivo publicoObjetivo){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.eliminarPreferenciaPublicoObjetivo(idCliente, publicoObjetivo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(publicoObjetivo).build();
	}
}
