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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

/**
 * Tableau REST API Implementation of TableauApiService interface.
 * <p>
 * Created by suraj on 04/07/2020
 */
public class TableauApiImpl implements TableauApiService {

    private static final Logger logger = LogManager.getLogger(TableauApiImpl.class);

    private static TableauApiImpl INSTANCE = null;

    private static Properties m_properties = new Properties();

    private ObjectFactory m_objectFactory = new ObjectFactory();

    private static Marshaller s_jaxbMarshaller;
    private static Unmarshaller s_jaxbUnmarshaller;

    public static TableauApiImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TableauApiImpl();
            initialize();
        }

        return INSTANCE;

    }

    private static void initialize() {

        try {
            m_properties.load(new FileInputStream("src/main/resources/config.properties"));
            JAXBContext jaxbContext = JAXBContext.newInstance(TsRequest.class, TsResponse.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(m_properties.getProperty("server.schema.location")));
            s_jaxbMarshaller = jaxbContext.createMarshaller();
            s_jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            s_jaxbMarshaller.setSchema(schema);
            s_jaxbUnmarshaller.setSchema(schema);
        } catch (SAXException | JAXBException | IOException e) {
            throw new IllegalStateException("Failed to initialize Tableau Interactor.");
        }

    }

    private final String TABLEAU_AUTH_HEADER = "X-Tableau-Auth";

    @Override
    public TableauCredentialsType invokeSignIn(String username, String password, String contentUrl) {

        logger.info("Signing in to Tableau Server");

        String url = m_properties.getProperty("server.host") + m_properties.getProperty("server.api.version") + "auth/signin";

        TsRequest payload = createPayloadForSignin(username, password, contentUrl);

        TsResponse response = post(url, "", payload);

        if (response.getCredentials() != null) {
            logger.info("Sign in successful");
            return response.getCredentials();
        }

        return null;

    }

    @Override
    public void invokeSignOut(TableauCredentialsType credential) {

        logger.info("Signing out of Tableau Server");

        String url = m_properties.getProperty("server.host") + m_properties.getProperty("server.api.version") + "auth/signout";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(TABLEAU_AUTH_HEADER, credential.getToken())
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 204) {
            logger.info("Successfully signed out of Tableau Server");
        } else {
            logger.error("Failed to sign out of Tableau Server");
        }

    }

    @Override
    public SiteListType invokeQuerySites(TableauCredentialsType credential) {

        logger.info("Querying sites on Tableau Server");

        String url = m_properties.getProperty("server.host") + m_properties.getProperty("server.api.version") + "sites";

        TsResponse response = get(url, credential.getToken());

        if (response.getSites() != null) {
            logger.info("Succesfully queried sites.");
            return response.getSites();
        } else {
            logger.error("There was a problem querying sites.");
        }

        return null;

    }

    @Override
    public ProjectListType invokeQueryProjects(TableauCredentialsType credential, String siteId) {

        logger.info("Querying projects on site " + siteId);

        String url = m_properties.getProperty("server.host") + m_properties.getProperty("server.api.version") + "sites/" + siteId + "/projects";

        TsResponse response = get(url,credential.getToken());

        if (response.getProjects() != null) {
            logger.info("Succesfully queried projects.");
            return response.getProjects();
        } else {
            logger.error("There was a problem querying projects.");
        }

        return null;

    }

    @Override
    public WorkbookListType invokeQueryWorkbooks(TableauCredentialsType credential, String siteId, String userId) {

        logger.info("Querying workbooks on site " + siteId);

        String url = m_properties.getProperty("server.host") + m_properties.getProperty("server.api.version") + "sites/" + siteId + "/users/" + userId + "/workbooks";

        TsResponse response = get(url,credential.getToken());

        if (response.getWorkbooks() != null) {
            logger.info("Succesfully queried workbooks.");
            return response.getWorkbooks();
        } else {
            logger.error("There was a problem querying workbooks.");
        }

        return null;

    }

    private TsRequest createPayloadForSignin(String username, String password, String contentUrl) {

        TsRequest requestPayload = m_objectFactory.createTsRequest();

        TableauCredentialsType signInCredentials = m_objectFactory.createTableauCredentialsType();

        SiteType site = m_objectFactory.createSiteType();
        site.setContentUrl(contentUrl);

        signInCredentials.setSite(site);
        signInCredentials.setName(username);
        signInCredentials.setPassword(password);

        requestPayload.setCredentials(signInCredentials);

        return requestPayload;

    }

    private TsResponse get(String url, String authToken) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(TABLEAU_AUTH_HEADER, authToken)
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    private TsResponse post(String url, String authToken, TsRequest requestPayload) {

        StringWriter writer = new StringWriter();

        if (requestPayload != null) {
            try {
                s_jaxbMarshaller.marshal(requestPayload, writer);
            } catch (JAXBException ex) {
                logger.error("There was a problem marshalling the payload: " + ex);
            }
        }

        String payload = writer.toString();

        logger.debug("Input payload is: \n" + payload);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(TABLEAU_AUTH_HEADER, authToken)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String responseXML = response != null ? response.body() : null;

        logger.debug("Response: \n" + responseXML);

        return unmarshalResponse(responseXML);

    }

    private TsResponse unmarshalResponse(String responseXML) {

        TsResponse tsResponse = m_objectFactory.createTsResponse();

        try {
            StringReader reader = new StringReader(responseXML);
            tsResponse = s_jaxbUnmarshaller.unmarshal(new StreamSource(reader), TsResponse.class).getValue();
        } catch (JAXBException ex) {
            logger.error("Failed to parse response from server due to:");
            ex.printStackTrace();
        }

        return tsResponse;

    }

}
