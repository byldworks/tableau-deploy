package com.byldworks.tableau.deploy.interactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

/**
 * Some test-cases to get going with Tableau
 */
public class TableauXMLHelperTest
{
	private static final Logger logger = LogManager.getLogger(TableauXMLHelperTest.class);

	@Test
	public void testValidateWorkbook()
	{
		TableauXMLHelper helper = TableauXMLHelper.getInstance(Paths.get("../tableau-files/workbooks/HelloWorld.twb"));
		Assertions.assertNotNull(helper);
	}


}

