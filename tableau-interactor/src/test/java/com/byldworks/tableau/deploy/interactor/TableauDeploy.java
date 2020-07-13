package com.byldworks.tableau.deploy.interactor;

import com.byldworks.tableau.deploy.api.rest.bindings.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main class to test TableauApiImpl.
 * <p>
 * Created by suraj on 04/07/2020
 */
public class TableauDeploy
{

	private static final Logger logger = LogManager.getLogger(TableauDeploy.class);

	private static Properties s_properties = new Properties();
	private static Map<String, String> envProperties = new ConcurrentHashMap<>();

	static
	{
		try
		{
			s_properties.load(new FileInputStream("src/main/resources/config.properties"));
			Enumeration<Object> keys = s_properties.keys();
			while (keys.hasMoreElements())
			{
				String key = (String) keys.nextElement();
				envProperties.put(key, s_properties.getProperty(key));
			}

		} catch (IOException e)
		{
			logger.error("Failed to load configuration files.");
		}
	}

	public static void main(String[] args)
	{
		final String username = s_properties.getProperty("user.name");
		final String password = s_properties.getProperty("user.password");
		final String siteContentUrl = s_properties.getProperty("site.default.contentUrl");

		TableauApiService impl = TableauApiService.getInstance(username, password, siteContentUrl, envProperties);

		TableauCredentialsType credential = impl.getTableauCredentialsType();
		String currentSiteId = credential.getSite().getId();
		String currentUserId = credential.getUser().getId();

		logger.info("Current SiteID: " + currentSiteId);
		logger.info("Current User: " + currentUserId);

		ProjectType defaultProject = null;
		ProjectListType projects = impl.invokeQueryProjects(currentSiteId);
		for (ProjectType project : projects.getProject())
		{
			if (project.getName().equals("default") || project.getName().equals("Default"))
			{
				defaultProject = project;
				logger.info(String.format("Default project found: %s", defaultProject.getId()));
			}
		}

		/**
		 * Publish a workbook
		 */
		File hwWorkbook = new File("../tableau-files/workbooks/HelloWorld.twb");
		WorkbookType hwPublish = impl.invokePublishWorkbook(currentSiteId, defaultProject.getId(), "HelloWorld", hwWorkbook, false, true);

		/**
		 * Publish a packaged workbook
		 */
		File workbookFile = new File("../tableau-files/packaged-workbooks/Demo.twbx");
		WorkbookType publishWorkbook = impl.invokePublishWorkbook(currentSiteId, defaultProject.getId(), "Test", workbookFile, false, true);
		WorkbookListType workbookListType = impl.invokeQueryWorkbooks(currentSiteId, currentUserId);
		for (WorkbookType workbook : workbookListType.getWorkbook())
		{
			logger.info("Found workbook: " + workbook.getId() + " | " + workbook.getName());
		}
		String workbookId = publishWorkbook.getId();

		logger.info("About to download the workbook we just published.");

		File downloadedWorkbook = impl.invokeDownloadWorkbook(currentSiteId, workbookId, "../tableau-files/packaged-workbooks/Test_Download.twbx", false);

		// Refresh workbook does not work due to mismatch between API return type and XSD.  Raised to Tableau Support.
		//logger.info("Refreshing workbook.");
		//JobType jobType = impl.invokeUpdateWorkbookNow(credential, currentSiteId, workbookId);

		logger.info("Now that we have successfully published two workbooks, we're going to delete them.");
		// impl.invokeDeleteWorkbook(credential, currentSiteId, defaultProject.getId(), hwPublish.getId());
		// impl.invokeDeleteWorkbook(credential, currentSiteId, defaultProject.getId(), workbookId);

		impl.invokeSignOut();

	}

}

