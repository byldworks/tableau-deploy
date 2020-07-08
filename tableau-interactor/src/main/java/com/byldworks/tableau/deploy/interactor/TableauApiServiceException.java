package com.byldworks.tableau.deploy.interactor;

/**
 * A runtime exception encapsulating some debugging details for the
 * exception thrown from the Tableau service
 */
public class TableauApiServiceException extends RuntimeException
{

	public TableauApiServiceException(String message)
	{
		super(message);
	}

	public TableauApiServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public TableauApiServiceException(Throwable cause)
	{
		super(cause);
	}
}
