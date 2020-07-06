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

}
