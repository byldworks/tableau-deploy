package com.byldworks.tableau.deploy.interactor;

import com.byldworks.tableau.deploy.api.rest.bindings.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Tableau REST API Implementation of TableauApiService interface.
 * <p>
 * Created by suraj on 04/07/2020
 */
public class TableauApiImpl implements TableauApiService
{

	private static final Logger logger = LogManager.getLogger(TableauApiImpl.class);

	private static class TableauApiImpl_Key
	{
		private final String username;
		private final String password;
		private final String contentUrl;

		TableauApiImpl_Key(String username, String password, String contentUrl)
		{
			this.username = username;
			this.password = password;
			this.contentUrl = contentUrl;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			TableauApiImpl_Key that = (TableauApiImpl_Key) o;
			return username.equals(that.username) && password.equals(that.password) && contentUrl.equals(that.contentUrl);
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(username, password, contentUrl);
		}

		@Override
		public String toString()
		{
			return "TableauApiImpl_Key{" + "username='" + username + '\'' + ", password='" + "masked-length:" + password.length() + '\'' + ", contentUrl='" + contentUrl + '\'' + '}';
		}
	}

	private static final ReentrantLock _lock = new ReentrantLock();
	private static final Map<TableauApiImpl_Key, TableauApiImpl> _instances = new ConcurrentHashMap<>();

	static TableauApiImpl getInstance(
			final String username, final String password, final String contentUrl, final Map<String, String> envVariable)
	{
		final TableauApiImpl_Key key = new TableauApiImpl_Key(username, password, contentUrl);
		TableauApiImpl tableauApi = _instances.get(key);
		if (tableauApi != null)
			return tableauApi;

		try
		{
			_lock.lock();
			tableauApi = _instances.get(key);
			if (tableauApi != null)
				return tableauApi;
			tableauApi = new TableauApiImpl(key, envVariable);
			_instances.put(key, tableauApi);
			return tableauApi;
		} finally
		{
			_lock.unlock();
		}
	}

	public static final String SERVER_HOST_ENV_KEY = "server.host";
	public static final String SERVER_API_VERSION_ENV_KEY = "server.api.version";

	private final String TABLEAU_AUTH_HEADER = "X-Tableau-Auth";

	private final String XSD_CLASS_PATH = "ts-api_3_8.xsd";

	private final TableauApiImpl_Key key;
	private final Map<String, String> envProperties;

	private final SchemaFactory schemaFactory;
	private final Schema schema;

	private final JAXBContext jaxbContext;
	private final ObjectFactory m_objectFactory;
	private final Marshaller s_jaxbMarshaller;
	private final Unmarshaller s_jaxbUnmarshaller;

	private final TableauCredentialsType tableauCredentials;

	private final String urlBase;

	private TableauApiImpl(TableauApiImpl_Key key, Map<String, String> envProperties)
	{
		this.key = key;
		this.envProperties = envProperties;

		try
		{
			jaxbContext = JAXBContext.newInstance(TsRequest.class, TsResponse.class);
			schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			InputStream ins = TableauApiImpl.class.getClassLoader().getResourceAsStream(XSD_CLASS_PATH);
			if (ins == null)
			{
				throw new TableauApiServiceException("Expected to find the " + XSD_CLASS_PATH + " - in the classpath. Is your project set up correctly.");
			}
			schema = schemaFactory.newSchema(new StreamSource(ins));
			m_objectFactory = new ObjectFactory();
			s_jaxbMarshaller = jaxbContext.createMarshaller();
			s_jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			s_jaxbMarshaller.setSchema(schema);
			s_jaxbUnmarshaller.setSchema(schema);
		} catch (JAXBException | SAXException e)
		{
			String msg = "Unable to initilise the tableau API - got exception : " + e;
			logger.error(msg, e);
			throw new RuntimeException(msg, e);
		}

		if (envProperties.containsKey(SERVER_HOST_ENV_KEY) == false)
			throw new IllegalArgumentException("You must supply the environment variable key - " + SERVER_HOST_ENV_KEY);
		if (envProperties.containsKey(SERVER_API_VERSION_ENV_KEY) == false)
			throw new IllegalArgumentException("You must supply the environment variable key - " + SERVER_API_VERSION_ENV_KEY);

		urlBase = envProperties.get(SERVER_HOST_ENV_KEY) + envProperties.get(SERVER_API_VERSION_ENV_KEY);

		logger.info("Tableau URLBase is : " + urlBase);
		logger.info("Attempt to log into Tableau with : " + key);
		tableauCredentials = invokeSignIn();
	}

	@Override
	public TableauCredentialsType getTableauCredentialsType()
	{
		return tableauCredentials;
	}

	private TableauCredentialsType invokeSignIn()
	{

		logger.info("Signing in to Tableau Server");

		String url = urlBase + "auth/signin";

		TsRequest payload = createPayloadForSignin();
		TsResponse response = post(url, "", payload);

		if (response.getCredentials() != null)
		{
			logger.info("Sign in successful");
			return response.getCredentials();
		}

		throw new TableauApiServiceException("Unable to sign into Tableau - response did not contain credentials");

	}

	@Override
	public void invokeSignOut()
	{
		logger.info("Signing out of Tableau Server");

		String url = urlBase + "auth/signout";

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(TABLEAU_AUTH_HEADER, tableauCredentials.getToken()).POST(HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = null;
		try
		{
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e)
		{
			String msg = "Unable to invoke Tableau - " + url + " - got : " + e;
			throw new TableauApiServiceException(msg, e);
		}

		if (response.statusCode() == 204)
		{
			logger.info("Successfully signed out of Tableau Server");
		} else
		{
			logger.error("Failed to sign out of Tableau Server");
		}

	}

	@Override
	public SiteListType invokeQuerySites()
	{

		logger.info("Querying sites on Tableau Server");

		String url = urlBase + "sites";

		TsResponse response = get(url, tableauCredentials.getToken());

		if (response.getSites() != null)
		{
			logger.info("Succesfully queried sites.");
			return response.getSites();
		} else
		{
			logger.error("There was a problem querying sites.");
		}

		return null;

	}

	@Override
	public ProjectListType invokeQueryProjects(String siteId)
	{

		logger.info("Querying projects on site " + siteId);

		String url = urlBase + "sites/" + siteId + "/projects";

		TsResponse response = get(url, tableauCredentials.getToken());

		if (response.getProjects() != null)
		{
			logger.info("Succesfully queried projects.");
			return response.getProjects();
		} else
		{
			logger.error("There was a problem querying projects.");
		}

		return null;

	}

	@Override
	public WorkbookListType invokeQueryWorkbooks(String siteId, String userId)
	{

		logger.info("Querying workbooks on site " + siteId);

		String url = urlBase + "sites/" + siteId + "/users/" + userId + "/workbooks";

		TsResponse response = get(url, tableauCredentials.getToken());

		if (response.getWorkbooks() != null)
		{
			logger.info("Succesfully queried workbooks.");
			return response.getWorkbooks();
		} else
		{
			logger.error("There was a problem querying workbooks.");
		}

		return null;

	}

	@Override
	public void invokeDeleteWorkbook(String siteId, String projectId, String workbookId)
	{

		logger.warn("About to delete workbook " + workbookId);

		String url = urlBase + "sites/" + siteId + "/workbooks/" + workbookId;

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(TABLEAU_AUTH_HEADER, tableauCredentials.getToken()).DELETE().build();
		HttpResponse<String> response = null;
		try
		{
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e)
		{
			String msg = "Unable to invoke Tableau - " + url + " - got : " + e;
			throw new TableauApiServiceException(msg, e);
		}

		if (response.statusCode() == 204)
		{
			logger.info("Successfully deleted workbook");
		} else
		{
			logger.error("Failed to delete workbook");
		}

	}

	@Override
	public JobType invokePublishWorkbook(String siteId, String projectId, String workbookName, File workbookFile, boolean overwrite)
	{

		logger.info("Publishing workbook " + workbookName + " on site " + siteId);

		String url = urlBase + "sites/" + siteId + "/workbooks?overwrite=" + overwrite + "&asJob=true";

		ConnectionCredentialsType connCredentials = new ConnectionCredentialsType();
		connCredentials.setName(key.username);
		connCredentials.setPassword(key.username);

		TsRequest payload = createPayloadToPublishWorkbook(workbookName, projectId, connCredentials);

		TsResponse response = postMultiPart(url, tableauCredentials.getToken(), payload, workbookFile, "workbook");

		if (response.getJob() != null)
		{
			logger.info("Successfully created workbook publishing job: " + response.getJob().getId());
			return response.getJob();
		} else
		{
			logger.error("Failed to publish workbook.");
		}

		throw new TableauApiServiceException("Invoked - " + url + " - but did not get back anything from response.getWorkbook()  - hence failing");

	}

	@Override
	public File invokeDownloadWorkbook(String siteId, String workbookId, String targetFileName, boolean includeExtracts)
	{

		logger.info("About to download workbook: " + workbookId);

		String url = urlBase + "sites/" + siteId + "/workbooks/" + workbookId + "/content?includeExtract=" + includeExtracts;

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(TABLEAU_AUTH_HEADER, tableauCredentials.getToken()).build();
		HttpResponse<Path> responseOfFile = null;
		try
		{
			responseOfFile = client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of(targetFileName)));
		} catch (IOException | InterruptedException e)
		{
			String msg = "Unable to invoke Tableau - " + url + " - got : " + e;
			throw new TableauApiServiceException(msg, e);
		}

		if (responseOfFile.statusCode() == 200)
		{
			logger.info("Successfully downloaded workbook to " + responseOfFile.body().getFileName());
			return responseOfFile.body().toFile();
		} else
		{
			logger.error("Failed to download workbook");
		}

		throw new TableauApiServiceException("Invoked - " + url + " - but got back the response code : " + responseOfFile.statusCode() + " - hence failing");

	}

	@Override
	public JobType invokeUpdateWorkbookNow(String siteId, String workbookId)
	{

		logger.info("About to refresh extracts of workbook: " + workbookId);

		String url = urlBase + "sites/" + siteId + "/workbooks/" + workbookId + "/refresh";

		TsRequest requestPayload = m_objectFactory.createTsRequest();
		requestPayload.setCredentials(tableauCredentials);

		TsResponse response = post(url, tableauCredentials.getToken(), requestPayload);

		if (response.getJob() != null)
		{
			logger.info("Successfully invoked a refresh of workbook " + workbookId);
			return response.getJob();
		} else
		{
			logger.error("Failed to refresh workbook.");
		}

		throw new TableauApiServiceException("Invoked - " + url + " - but did not get back anything from response.getJob()  - hence failing");

	}

	@Override
	public DataSourceType invokePublishDataSource(String siteId, String projectId, String dataSourceName, File dataSourceFile, boolean overwrite)
	{

		logger.info("About to publish DataSource " + dataSourceName + " from file " + dataSourceFile.getName());

		String url = urlBase + "sites/" + siteId + "/datasources?overwrite=" + overwrite;

		TsRequest payload = createPayloadToPublishDataSource(dataSourceName, projectId);

		TsResponse response = postMultiPart(url, tableauCredentials.getToken(), payload, dataSourceFile, "datssource");

		if (response.getDatasource() != null)
		{
			logger.info("Successfully published datasource");
			return response.getDatasource();
		} else
		{
			logger.error("Failed to publish datasource");
		}

		throw new TableauApiServiceException("Invoked - " + url + " - but did not get back anything from response.getDatasource()  - hence failing");

	}

	@Override
	public JobType invokeCreateExtract(String siteId, String dataSourceId)
	{

		logger.info("About to create an extract for DataSource " + dataSourceId);

		String url = urlBase + "sites/" + siteId + "/datasources/" + dataSourceId + "/createExtract";

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(TABLEAU_AUTH_HEADER, tableauCredentials.getToken()).POST(HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = null;
		try
		{
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e)
		{
			String msg = "Unable to invoke Tableau - " + url + " - got : " + e;
			throw new TableauApiServiceException(msg, e);
		}

		String responseXML = response != null ? response.body() : null;

		logger.debug("Response: \n" + responseXML);

		if (response.statusCode() == 200)
		{
			logger.info("Successfully created an extract.");
			return unmarshalResponse(responseXML).getJob();
		} else
		{
			logger.error("Failed to sign out of Tableau Server");
			return null;
		}

	}

	@Override
	public JobType invokeQueryJob(String siteId, String jobId)
	{

		logger.info("Querying job: " + jobId);

		String url = urlBase + "sites/" + siteId + "/jobs/" + jobId;

		TsResponse response = get(url, tableauCredentials.getToken());

		if (response.getJob() != null)
		{
			return response.getJob();
		} else
		{
			logger.error("There was a problem querying the job.");
		}

		return null;

	}

	@Override
	public ScheduleListType invokeQuerySchedules()
	{

		logger.info("Querying schedules");

		String url = urlBase + "schedules";

		TsResponse response = get(url, tableauCredentials.getToken());

		if (response.getSchedules() != null)
		{
			return response.getSchedules();
		} else
		{
			logger.error("There was a problem getting schedules.");
		}

		return null;

	}

	@Override
	public TaskType invokeScheduleWorkbookRefresh(String siteId, String scheduleId, String workbookId)
	{

		logger.info("About to add refresh task for workbook " + workbookId + " to schedule " + scheduleId);

		String url = urlBase + "sites/" + siteId + "/schedules/" + scheduleId + "/workbooks";

		TsRequest payload = createPayloadToScheduleWorkbookRefresh(workbookId);

		TsResponse response = post(url, tableauCredentials.getToken(), payload);

		if (response.getTask() != null)
		{
			logger.info("Successfully added workbook refresh task to schedule");
			return response.getTask();
		} else
		{
			logger.error("Failed to add workbook refresh task to schedule");
		}

		throw new TableauApiServiceException("Invoked - " + url + " - but did not get back anything from response.getTask()  - hence failing");


	}

	private TsRequest createPayloadToScheduleWorkbookRefresh(String workbookId)
	{
		TsRequest requestPayload = m_objectFactory.createTsRequest();
		TaskType task = m_objectFactory.createTaskType();
		TaskExtractRefreshType taskExtractRefresh = m_objectFactory.createTaskExtractRefreshType();
		WorkbookType workbook = m_objectFactory.createWorkbookType();
		workbook.setId(workbookId);
		taskExtractRefresh.setWorkbook(workbook);
		task.setExtractRefresh(taskExtractRefresh);
		requestPayload.setTask(task);
		return requestPayload;

	}

	private TsRequest createPayloadToPublishDataSource(String dataSourceName, String projectId)
	{

		TsRequest requestPayload = m_objectFactory.createTsRequest();
		DataSourceType dataSource = m_objectFactory.createDataSourceType();
		ProjectType project = m_objectFactory.createProjectType();
		project.setId(projectId);
		dataSource.setName(dataSourceName);
		dataSource.setProject(project);
		requestPayload.setDatasource(dataSource);
		return requestPayload;

	}

	private TsRequest createPayloadForSignin()
	{

		TsRequest requestPayload = m_objectFactory.createTsRequest();

		TableauCredentialsType signInCredentials = m_objectFactory.createTableauCredentialsType();

		SiteType site = m_objectFactory.createSiteType();
		site.setContentUrl(key.contentUrl);

		signInCredentials.setSite(site);
		signInCredentials.setName(key.username);
		signInCredentials.setPassword(key.password);

		requestPayload.setCredentials(signInCredentials);

		return requestPayload;

	}

	private TsRequest createPayloadToPublishWorkbook(String workbookName, String projectId, ConnectionCredentialsType connCredentials)
	{

		TsRequest requestPayload = m_objectFactory.createTsRequest();
		WorkbookType workbook = m_objectFactory.createWorkbookType();
		ProjectType project = m_objectFactory.createProjectType();
		ConnectionListType connList = m_objectFactory.createConnectionListType();
		ConnectionType conn = m_objectFactory.createConnectionType();

		conn.setServerAddress(envProperties.get(SERVER_HOST_ENV_KEY));
		conn.setConnectionCredentials(connCredentials);
		connList.getConnection().add(conn);
		project.setId(projectId);
		workbook.setName(workbookName);
		workbook.setProject(project);
		workbook.setConnections(connList);
		requestPayload.setWorkbook(workbook);

		return requestPayload;

	}

	private TsResponse get(String url, String authToken)
	{

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(TABLEAU_AUTH_HEADER, authToken).build();
		HttpResponse<String> response = null;
		try
		{
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e)
		{
			String msg = "Unable to invoke Tableau - " + url + " - got : " + e;
			throw new TableauApiServiceException(msg, e);
		}

		String responseXML = response != null ? response.body() : null;

		logger.debug("Response: \n" + responseXML);

		return unmarshalResponse(responseXML);

	}

	/**
	 * Creates a POST request using the specified URL and a request payload
	 *
	 * @param url
	 * @param authToken
	 * @param requestPayload
	 * @return
	 */
	private TsResponse post(String url, String authToken, TsRequest requestPayload)
	{

		StringWriter writer = new StringWriter();

		if (requestPayload != null)
		{
			try
			{
				s_jaxbMarshaller.marshal(requestPayload, writer);
			} catch (JAXBException ex)
			{
				logger.error("There was a problem marshalling the payload: " + ex);
			}
		}

		String payload = writer.toString();

		logger.debug("Input payload is: \n" + payload);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(TABLEAU_AUTH_HEADER, authToken).POST(HttpRequest.BodyPublishers.ofString(payload)).build();

		HttpResponse<String> response = null;
		try
		{
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e)
		{
			String msg = "Unable to invoke Tableau - " + url + " - got : " + e;
			throw new TableauApiServiceException(msg, e);
		}

		String responseXML = response != null ? response.body() : null;

		logger.debug("Response: \n" + responseXML);

		return unmarshalResponse(responseXML);

	}

	/**
	 * Creates a multi-part POST request using the specified URL and a request payload
	 *
	 * @param url
	 * @param authToken
	 * @param requestPayload
	 * @param file
	 * @param fileType
	 * @return
	 */
	private TsResponse postMultiPart(String url, String authToken, TsRequest requestPayload, File file, String fileType)
	{

		StringWriter writer = new StringWriter();

		if (requestPayload != null)
		{
			try
			{
				s_jaxbMarshaller.marshal(requestPayload, writer);
			} catch (JAXBException ex)
			{
				logger.error("There was a problem marshalling the payload: " + ex);
			}
		}

		String payloadType = null;
		if (fileType.equals("workbook"))
		{
			payloadType = "tableau_workbook";
		} else if (fileType.equals("datasource"))
		{
			payloadType = "tableau_datasource";
		} else
		{
			logger.error("No file type set. Publish will fail");
			return null;
		}

		String payload = writer.toString();
		logger.debug("Input payload: \n" + payload);

		Map<Object, Object> data = new LinkedHashMap<>();
		data.put("request_payload", payload);
		Path path = file.toPath();
		data.put(payloadType, path);
		String boundary = new BigInteger(256, new Random()).toString();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = null;
		try
		{
			request = HttpRequest.newBuilder().uri(URI.create(url)).header(TABLEAU_AUTH_HEADER, authToken).header("Content-Type", "multipart/mixed;boundary=" + boundary).POST(ofMimeMultipartData(data, boundary)).build();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		HttpResponse<String> response = null;
		try
		{
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		String responseXML = response != null ? response.body() : null;

		logger.debug("Response: \n" + responseXML);

		return unmarshalResponse(responseXML);

	}

	private TsResponse unmarshalResponse(String responseXML)
	{

		TsResponse tsResponse = m_objectFactory.createTsResponse();

		try
		{
			StringReader reader = new StringReader(responseXML);
			tsResponse = s_jaxbUnmarshaller.unmarshal(new StreamSource(reader), TsResponse.class).getValue();
		} catch (JAXBException ex)
		{
			logger.error("Failed to parse response from server due to:");
			ex.printStackTrace();
		}

		return tsResponse;

	}

	public static HttpRequest.BodyPublisher ofMimeMultipartData(
			Map<Object, Object> data, String boundary) throws IOException
	{
		var byteArrays = new ArrayList<byte[]>();
		byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);
		for (Map.Entry<Object, Object> entry : data.entrySet())
		{
			byteArrays.add(separator);

			if (entry.getValue() instanceof Path)
			{
				var path = (Path) entry.getValue();
				String mimeType = Files.probeContentType(path);
				byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName() + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
				byteArrays.add(Files.readAllBytes(path));
				byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
			} else
			{
				byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n").getBytes(StandardCharsets.UTF_8));
			}
		}
		byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
		return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
	}

}
