package com.byldworks.tableau.deploy.interactor;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Some utilities for dealing with TableauXML files.
 */
public class TableauXMLHelper
{
	private static final Logger logger = LogManager.getLogger(TableauXMLHelper.class);

	static final DocumentBuilderFactory docFactory;
	static final DocumentBuilder builder;

	static
	{
		try
		{
			docFactory = DocumentBuilderFactory.newInstance();
			builder = docFactory.newDocumentBuilder();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Unable to create the document factories. Is there something wrong with your setup? " + e, e );
		}
	}

	private final Path tableauXML;
	private final Document tableauDocument;

	public static TableauXMLHelper getInstance(Path tableauXML)
	{
		return new TableauXMLHelper(tableauXML);
	}

	private TableauXMLHelper(Path tableauXML)
	{
		this.tableauXML = tableauXML;

		try(InputStream ins = Files.newInputStream(tableauXML))
		{
			double start = System.nanoTime();
			tableauDocument = builder.parse(ins);
			logger.info("Successfully parsed - " + tableauXML + " - in " + ( (System.nanoTime() - start) /1_000_000.0 ) + " ms" );
		}
		catch(Exception e)
		{
			throw new TableauApiServiceException("Unable to parse the file : " + tableauXML + " | " + e, e);
		}

	}






}
