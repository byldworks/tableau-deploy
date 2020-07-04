package com.byldworks.tableau.deploy.interactor;

import com.byldworks.tableau.deploy.api.rest.bindings.TableauCredentialsType;

/**
 * Interface used by Tableau Interactor.
 *
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

}
