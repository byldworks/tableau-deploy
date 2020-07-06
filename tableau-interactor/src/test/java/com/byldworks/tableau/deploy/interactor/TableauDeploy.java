package com.byldworks.tableau.deploy.interactor;

import com.byldworks.tableau.deploy.api.rest.bindings.ProjectListType;
import com.byldworks.tableau.deploy.api.rest.bindings.ProjectType;
import com.byldworks.tableau.deploy.api.rest.bindings.TableauCredentialsType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Main class to test TableauApiImpl.
 *
 * Created by suraj on 04/07/2020
 */
public class TableauDeploy {

    private static final Logger logger = LogManager.getLogger(TableauDeploy.class);
    private static Properties s_properties = new Properties();
    private static final TableauApiImpl s_tableauApiImpl = TableauApiImpl.getInstance();

    static {
        try {
            s_properties.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            logger.error("Failed to load configuration files.");
        }
    }

    public static void main(String[] args) {

        TableauApiImpl impl = new TableauApiImpl();
        logger.info("Invoking sign in");
        TableauCredentialsType credential = impl.invokeSignIn(
                s_properties.getProperty("user.name"),
                s_properties.getProperty("user.password"),
                s_properties.getProperty("site.default.contentUrl"));
        String currentSiteId = credential.getSite().getId();
        String currentUserId = credential.getUser().getId();
        logger.info("Current SiteID: " + currentSiteId);
        logger.info("Current User: " + currentUserId);

        ProjectType defaultProject;
        ProjectListType projects = impl.invokeQueryProjects(credential, currentSiteId);
        for (ProjectType project : projects.getProject()) {
            if (project.getName().equals("default") || project.getName().equals("Default")) {
                defaultProject = project;
                logger.info(String.format("Default project found: %s", defaultProject.getId()));
            } else {
                logger.error("Failed to find Default project.");
            }
        }

        impl.invokeSignOut(credential);

    }

}
