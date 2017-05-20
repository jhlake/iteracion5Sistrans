package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.AllAbonamientosMDB;
import jms.AllFuncionesMDB;
import jms.AllRentabilidad2MDB;
import jms.AllRentabilidadMDB;
import jms.AllRetirarCompaniaMDB;
import jms.NonReplyException;
import tm.FestivAndesMaster;
import vos.CompaniaDeTeatro;
import vos.Funcion;
import vos.ListaAbonamientos;
import vos.ListaCompanias;
import vos.ListaFunciones;
import vos.ListaReporteRentabilidadC5;
import vos.ListaReporteRentabilidad2C5;


public class FestivAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static FestivAndesDistributed instance;
	
	private FestivAndesMaster tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private AllRentabilidadMDB allRentabilidadMQ;
	
	private AllRentabilidad2MDB allRentabilidad2MQ;
	
	private AllFuncionesMDB allFuncionesMQ;
	
	private AllAbonamientosMDB allAbonamientosMQ;
	
	private AllRetirarCompaniaMDB allRetirarCompaniaMQ;
	
	private static String path;


	private FestivAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allRentabilidadMQ = new AllRentabilidadMDB(factory, ctx);
		allRentabilidad2MQ = new AllRentabilidad2MDB(factory, ctx);
		allFuncionesMQ = new AllFuncionesMDB(factory, ctx);
		allAbonamientosMQ = new AllAbonamientosMDB(factory, ctx);
		allRetirarCompaniaMQ = new AllRetirarCompaniaMDB(factory, ctx);
		
		allRentabilidadMQ.start();
		allRentabilidad2MQ.start();
		allFuncionesMQ.start();
		allAbonamientosMQ.start();
		allRetirarCompaniaMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		allRentabilidadMQ.close();
		allRentabilidad2MQ.close();
		allFuncionesMQ.close();
		allAbonamientosMQ.close();
		allRetirarCompaniaMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(FestivAndesMaster tm)
	{
	   this.tm = tm;
	}
	
	private static FestivAndesDistributed getInst()
	{
		return instance;
	}
	
	public static FestivAndesDistributed getInstance(FestivAndesMaster tm)
	{
		if(instance == null)
		{
			try {
				instance = new FestivAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static FestivAndesDistributed getInstance()
	{
		if(instance == null)
		{
			FestivAndesMaster tm = new FestivAndesMaster(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		FestivAndesMaster tm = new FestivAndesMaster(path);
		return getInstance(tm);
	}
	
	/**
	public ListaVideos getLocalVideos() throws Exception
	{
		return tm.darVideosLocal();
	}
	
	public List<Videos> getRemoteVideos() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allVideosMQ.getRemoteVideos();
	}
	
	*/

	public ListaReporteRentabilidadC5 getLocalRentabilidades() throws SQLException {
		return tm.darRentabilidadesLocal();
	}
	
	public ListaReporteRentabilidad2C5 getLocalRentabilidades2() throws SQLException {
		return tm.darRentabilidades2Local();
	}

	public ListaCompanias deleteLocalCompanias() throws SQLException, Exception{
	
		return tm.retirarCompaniasLocal();
	}

	public ListaFunciones getLocalFunciones() throws SQLException, Exception{

		return tm.darFuncionesLocal();
	}

	public ListaAbonamientos getLocalAbonamientos() throws SQLException, Exception{

		return tm.darAbonamientosLocal();
	}
	
}
