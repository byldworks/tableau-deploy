package com.byldworks.tableau.deploy.interactor;

import com.byldworks.tableau.deploy.api.rest.bindings.*;

import java.io.File;

/**
 * Interface used by Tableau Interactor.
 * <p>
 * Created by suraj on 04/07/2020
 */
public interface TableauApiService {

    /**
     * Method to sign in to Tableau Server.
     *
     * @param username
     * @param password
     * @param contentUrl
     * @return
     */
    public TableauCredentialsType invokeSignIn(String username, String password, String contentUrl);

    /**
     * Method to sign out of Tableau Server.
     *
     * @param credential
     */
    public void invokeSignOut(TableauCredentialsType credential);

    /**
     * Method to get a list of sites on Tableau Server for which the user has access.
     *
     * @param credential
     * @return
     */
    public SiteListType invokeQuerySites(TableauCredentialsType credential);

    /**
     * Method to get a list of projects in a given site for which the user has access.
     *
     * @param credential
     * @param siteId
     * @return
     */
    public ProjectListType invokeQueryProjects(TableauCredentialsType credential, String siteId);

    /**
     * Method to get a list of workbooks on a site for which the user has read access.
     *
     * @param credential
     * @param siteId
     * @param userId
     * @return
     */
    public WorkbookListType invokeQueryWorkbooks(TableauCredentialsType credential, String siteId, String userId);

    /**
     * Method to publish a workbook to the target site.
     *
     * @param credential
     * @param siteId
     * @param projectId
     * @param workbookName
     * @param workbookFile
     * @param chunkedPublish
     * @param overwrite
     * @return
     */
    public WorkbookType invokePublishWorkbook(TableauCredentialsType credential, String siteId, String projectId, String workbookName, File workbookFile, boolean chunkedPublish, boolean overwrite);

    /**
     * Method to delete a workbook from Tableau Server.
     *
     * @param credential
     * @param siteId
     * @param projectId
     * @param workbookId
     */
    public void invokeDeleteWorkbook(TableauCredentialsType credential, String siteId, String projectId, String workbookId);

    /**
     * Method to download a Tableau workbook from Tableau Server.
     *
     * @param credential
     * @param siteId
     * @param workbookId
     * @param targetFileName
     * @return
     */
    public File invokeDownloadWorkbook(TableauCredentialsType credential, String siteId, String workbookId, String targetFileName, boolean includeExtracts);

    /**
     * This method refreshes the specified workbook, with no need to associate the workbook refresh
     * with a scheduled task. This method is the equivalent of selecting a workbook using the Tableau
     * Server UI, and then selecting Refresh Extracts from the menu (also known as a "manual refresh").
     *
     * @param credential
     * @param siteId
     * @param workbookId
     * @return
     */
    public JobType invokeUpdateWorkbookNow(TableauCredentialsType credential, String siteId, String workbookId);

    /**
     * Publishes a data source on the specified site.
     *
     * @param credential
     * @param siteId
     * @param projectId
     * @param datSourceName
     * @param dataSourceFile
     * @param overwrite
     * @return
     */
    public DataSourceType invokePublishDataSource(TableauCredentialsType credential, String siteId, String projectId, String dataSourceName, File dataSourceFile, boolean overwrite);

}
