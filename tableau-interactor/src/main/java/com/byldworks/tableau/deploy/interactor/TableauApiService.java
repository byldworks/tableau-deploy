package com.byldworks.tableau.deploy.interactor;

import com.byldworks.tableau.deploy.api.rest.bindings.*;

import java.io.File;
import java.util.Map;

/**
 * Interface used by Tableau Interactor.
 * <p>
 * Created by suraj on 04/07/2020
 */
public interface TableauApiService
{

	/**
	 * Returns an instance of the Tableau API matching the given credentials and content URL.
	 *
	 * @return
	 */
	public static TableauApiService getInstance(String username, String password, String contentUrl, Map<String, String> envVariables)
	{
		return TableauApiImpl.getInstance(username, password, contentUrl, envVariables);
	}

	/**
	 * Method to sign out of Tableau Server.
	 */
	public void invokeSignOut();

	/**
	 * Method to get a list of sites on Tableau Server for which the user has access.
	 *
	 * @return
	 */
	public SiteListType invokeQuerySites();

	/**
	 * Method to get a list of projects in a given site for which the user has access.
	 *
	 * @param siteId
	 * @return
	 */
	public ProjectListType invokeQueryProjects(String siteId);

	/**
	 * Method to get a list of workbooks on a site for which the user has read access.
	 *
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public WorkbookListType invokeQueryWorkbooks(String siteId, String userId);

	/**
	 * Method to publish a workbook to the target site.
	 *
	 * @param siteId
	 * @param projectId
	 * @param workbookName
	 * @param workbookFile
	 * @param overwrite
	 * @return
	 */
	public JobType invokePublishWorkbook(String siteId, String projectId, String workbookName, File workbookFile, boolean overwrite);

	/**
	 * Method to delete a workbook from Tableau Server.
	 *
	 * @param siteId
	 * @param projectId
	 * @param workbookId
	 */
	public void invokeDeleteWorkbook(String siteId, String projectId, String workbookId);

	/**
	 * Method to download a Tableau workbook from Tableau Server.
	 *
	 * @param siteId
	 * @param workbookId
	 * @param targetFileName
	 * @return
	 */
	public File invokeDownloadWorkbook(String siteId, String workbookId, String targetFileName, boolean includeExtracts);

	/**
	 * This method refreshes the specified workbook, with no need to associate the workbook refresh
	 * with a scheduled task. This method is the equivalent of selecting a workbook using the Tableau
	 * Server UI, and then selecting Refresh Extracts from the menu (also known as a "manual refresh").
	 *
	 * @param siteId
	 * @param workbookId
	 * @return
	 */
	public JobType invokeUpdateWorkbookNow(String siteId, String workbookId);

	/**
	 * Publishes a data source on the specified site.
	 *
	 * @param siteId
	 * @param projectId
	 * @param dataSourceFile
	 * @param overwrite
	 * @return
	 */
	public DataSourceType invokePublishDataSource(String siteId, String projectId, String dataSourceName, File dataSourceFile, boolean overwrite);

	/**
	 * Create an extract for a data source in a site.
	 *
	 * @param siteId
	 * @param dataSourceId
	 * @return
	 */
	public JobType invokeCreateExtract(String siteId, String dataSourceId);

	/**
	 * Returns status information about an asynchronous process that is tracked using a job.
	 *
	 * @param siteId
	 * @param jobId
	 * @return
	 */
	public JobType invokeQueryJob(String siteId, String jobId);

	/**
	 * Returns a list of flows, extract and subscription schedules.
	 * For each schedule the API returns the name, frequency, priority and other information.
	 *
	 * @return
	 */
	public ScheduleListType invokeQuerySchedules();

	/**
	 * Adds a task to refresh a workbook to an existing schedule.
	 *
	 * @param siteId
	 * @param scheduleId
	 * @param workbookId
	 * @param incrementalRefresh
	 * @return
	 */
	public TaskType invokeScheduleWorkbookRefresh(String siteId, String scheduleId, String workbookId, Boolean incrementalRefresh);

	/**
	 * Returns the Tableau credentials object.
	 *
	 * @return
	 */
	TableauCredentialsType getTableauCredentialsType();

}
