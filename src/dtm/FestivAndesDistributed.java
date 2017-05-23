package dtm;

import java.io.IOException;
import java.lang.annotation.IncompleteAnnotationException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
import vos.ReporteRentabilidadC5;
import vos.ListaReporteRentabilidad2C5;


public class FestivAndesDistributed implements MessageListener, ExceptionListener
{
	
	/**
	 * Atributo tipo ListaVideos que va guardando todos los videos que llegan como respuesta de las otra aplicaciones 
	 */
	private ListaReporteRentabilidadC5 respuesta;
	
	/**
	 * Atributo tipo TopicSession que se usa para la conexión a los topics 
	 */
	private TopicSession topicSession;
	
	/**
	 * Atributo tipo Topic que maneja la conexión al topic de dar todos los videos 
	 */
	private Topic topic;
	
	/**
	 * ArrayList que guarda todos los recursos que se usan para la conexión a las colas y topics
	 */
	private ArrayList<Object> recursos;

	/**
	 * boolean que dice si la aplicación esta a la espera de respuestas de las otras aplicaciones
	 */
	private boolean waiting;

	/**
	 * Numero de aplicaciones que hay en todo el sistema aparte de la propia. 
	 * Esto se hace para saber cuantas respuestas se esperan 
	 */
	private int numberAppsTotal;

	/**
	 * Numero de aplicaciones/respuestas que han llegado
	 */
	private int numberApps;

	/**
	 * Referencia a la clase principal VideoAndesMaster para su uso.
	 * Se usa para responder a requerimientos que llegan de otras aplicaciones
	 */
	private FestivAndesMaster master;

	/////Queues:
	
	/**
	 * Atributo que representa la cola personal de esta aplicación
	 */
	private String myQueue;

	/////Topics:

	/**
	 * Atributo que representa el topic: topicAllVideos
	 */
	private String topicAllRentabilidad;

	/////Protocol

	/**
	 * Atributo que representa el time out de el requerimiento de dar todos los videos
	 * 10 segundos
	 */
	public final static int TIME_OUT = 10;

	/**
	 * Ruta para la conexión al Remote Connection Factory
	 */
	public final static String REMOTE_CONNECTION_FACTORY = "java:jboss/exported/jms/RemoteConnectionFactory";	

	/**
	 * Atributo que representa, dentro del mensaje, la solicitud de todos los videos de manera distribuida
	 */
	public final static String GET_ALL_VIDEO_ASK = "GETALLVIDEOS";

	/**
	 * Atributo que representa, dentro del mensaje, la respuesta del requerimiento dar todos los videos.
	 */
	public final static String GET_ALL_VIDEO_REPLY = "GETALLVIDEOSRES";

	/**
	 * Atributo que representa, dentro del mensaje, el conector para el formateo de todos los mensajes
	 */
	public final static String CONNECTOR = "@@@";

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		

		allRentabilidadMQ.start();
	


	}

	public void stop() throws JMSException
	{
		allRentabilidadMQ.close();	       
		

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
	 * @throws Exception 
	
	*/

	public ReporteRentabilidadC5 getLocalRentabilidades(int idSitio) throws Exception {
		return tm.darRentabilidadesLocal(idSitio);
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
	
	
	
	
	//TODO
	
	
	/**
	 * Método que inicializa la clase JMSManager 
	 * @param numerApps - numero de aplicaciones totales 
	 * @param myQueue - ruta de la cola personal de la aplicación
	 * @param topicAllVideos - ruta del topic topicAllVideos
	 * <b>post: </b> se han inicializado los atributos de la clase y 
	 * se han generado la suscripciones y publicaciones a las colas y topics
	 */
	public void setUpJMSManager(int numerApps, String myQueue, String topicAllRentabilidad){
		try {
			this.numberAppsTotal = numerApps - 1;
			this.myQueue = myQueue;
			this.topicAllRentabilidad = topicAllRentabilidad;
			setupMyQueue();
			setupSubscriptions();
			waiting = false;
			respuesta = new ListaReporteRentabilidadC5(null);
			this.recursos = new ArrayList<Object>();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que retorna la respuesta a requerimiento de dar todos los videos.
	 * @return ListaVideos - Objeto ListaVideos con los videos de la respuesta del requerimiento
	 * @throws IncompleteReplyException - Caso de IncompleteReplyException
	 * @throws NonReplyException - Caso de NonReplyException
	 * @throws JMSException - Caso de JMSException
	 */
	public ListaReporteRentabilidadC5 getResponse() throws IncompleteAnnotationException, JMSException, NamingException, InterruptedException, NonReplyException {
		sendMessage(); // manda el mensaje de solicitud del requerimiento al topic
		waiting = true; // Lo hace para< indicar que si esta esperando respuestas
		this.numberApps = 0; // Pone en 0 el numero de respuestas que han llegado

		int count = 0;
		while(TIME_OUT != count && this.numberApps != this.numberAppsTotal){
			TimeUnit.SECONDS.sleep(1); // espera activa que termina cuando se ha cumplido el time out o cuando han llegado todas las respuestas esperadas
			count++;
		}
		
		if(count == TIME_OUT){ // Verifica si se cumplió el time out 
			if(this.respuesta.getRentabilidades().isEmpty()){
				waiting = false;
				this.numberApps = 0;
				throw new NonReplyException("Time Out - No Reply"); // Exception que indica que se cumplido el time out y nadie respondido 
			}
			waiting = false;
			this.numberApps = 0;
		//	throw new IncompleteReplyException("Time out ",respuesta); // Exception que indica que se cumplido el time out pero algunos respondieron
		}
		waiting = false;
		this.numberApps = 0;
		if(respuesta.getRentabilidades().isEmpty())
			throw new NonReplyException("Got all responses but no videos were detected"); // Exception que indica que todos respondieron pero no llegaron videos
		ListaReporteRentabilidadC5 res = respuesta;
		respuesta = new ListaReporteRentabilidadC5(null);
		return res; // Retorna con la respuesta completa de todas las aplicaciones
	}

	/**
	 * Método que inicializa todas las suscripciones a los topics.
	 * En este caso solo al topic de topicAllVideos
	 * <b>post: </b> se han suscito al topic de dar todos los videos
	 */
	public void setupSubscriptions()
	{
		// init Topic para consumir donde llegan las peticiones
		try {
			InitialContext ctx = new InitialContext();
			this.topic = (Topic) ctx.lookup(topicAllRentabilidad);
			TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.lookup(REMOTE_CONNECTION_FACTORY);
			TopicConnection topicConn = connFactory.createTopicConnection();
			this.topicSession = topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
			topicSubscriber.setMessageListener(this);
			topicConn.setExceptionListener(this);
			topicConn.start();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}                                                                      
	}

	/**
	 * Método que manda el mensaje para solicitar el requerimiento de las todos los videos
	 * <b>post: </b> se han mandado todos los mensaje
	 * @throws JMSException - Caso de JMSException
	 * @throws NamingException - Caso de NamingException
	 */
	public void sendMessage() throws JMSException, NamingException{
		// conecta al Topic para mandar la petición
		TopicPublisher topicPublisher = this.topicSession.createPublisher(this.topic);
		topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		TextMessage message = topicSession.createTextMessage();
		message.setText(GET_ALL_VIDEO_ASK + CONNECTOR + this.myQueue);
		topicPublisher.publish(message);
		System.out.println("published: " + message.getText());
	}

	/**
	 * Método que publica y suscribe la cola personal de la aplicación
	 * <b>post: </b> se han publicado y suscrito a la cola personal
	 */
	public void setupMyQueue() throws NamingException, JMSException{
		// conecta a la cola para respuestas propias.
		InitialContext ctx = new InitialContext();
		Queue queue = (Queue) ctx.lookup(this.myQueue);
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx. lookup(REMOTE_CONNECTION_FACTORY);
		QueueConnection queueConn = connFactory.createQueueConnection();
		QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueReceiver queueReceiver = queueSession.createReceiver(queue);
		queueReceiver.setMessageListener(this);
		queueConn.setExceptionListener(this);
		queueConn.start();
	}

	/**
	 * Método que manda el mensaje que llega como parámetro a la cola que llega como parámetro 
	 * @param queueName - ruta de la cola 
	 * @param response - mensaje a mandar 
	 * <b>post: </b> se ha mandado el mensaje a la cola
	 */
	public void doResponseToQueue(String queueName , String response) throws NamingException, JMSException{
		// conecta a la cola de respuestas del que pidio
		InitialContext ctx = new InitialContext();
		Queue queue = (Queue) ctx.lookup(queueName);
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup(REMOTE_CONNECTION_FACTORY);
		QueueConnection queueConn = connFactory.createQueueConnection();
		QueueSession queueSession = queueConn.createQueueSession(false,Session.DUPS_OK_ACKNOWLEDGE);
		QueueSender queueSender = queueSession.createSender(queue);
		queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		TextMessage message = queueSession.createTextMessage(response);
		queueSender.send(message);
		System.out.println("sent: " + message.getText());
		queueConn.close();
	}

	/**
	 * Método listener que recibe los mensajes, lo formatea y  hace lo que corresponde en cada caso: 
	 * - Caso 1: a[0].equals(GET_ALL_VIDEO_ASK): llega la petición del requerimiento dar todos los video por lo 
	 * que pide todos los videos a la clase principal y manda un mensaje con la respuesta 
	 * - Caso 2: a[0].equals(GET_ALL_VIDEO_REPLY): llega la respuesta de la petición del requerimiento por lo tanto
	 * Coge la respuesta y la guarda en respuesta 
	 * @param message - mensaje que llego 
	 * <b>post: </b> se ha mandado el mensaje a la cola
	 */
	public void onMessage(Message message)
	{
		TextMessage msg = (TextMessage) message;
		try 
		{
			String mes = msg.getText();
			System.out.println("received: " + mes);
			String[] a = mes.split(CONNECTOR);
			if(a[0].equals(GET_ALL_VIDEO_ASK) && !a[1].equals(this.myQueue)){
				ReporteRentabilidadC5 videos = this.master.darRentabilidadesLocal(1);
				ObjectMapper mapper = new ObjectMapper();
				String jsonString = mapper.writeValueAsString(videos);
				doResponseToQueue(a[1], GET_ALL_VIDEO_REPLY + CONNECTOR + jsonString);
			}
			else if(a[0].equals(GET_ALL_VIDEO_REPLY)){
				ObjectMapper mapper = new ObjectMapper();
				if(waiting){
					ListaReporteRentabilidadC5 obj = mapper.readValue(a[1], ListaReporteRentabilidadC5.class);
					this.respuesta.addRentabilidad(obj);
					this.numberApps++;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Método que se llama con el error cuando hay un error de JMS
	 */
	public void onException(JMSException exception)
	{
		System.err.println("something bad happended: " + exception);
	}
}
