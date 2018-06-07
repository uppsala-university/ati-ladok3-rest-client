package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.extintegration.UtannonseringLista;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.sunet.ati.ladok.rest.services.Extintegration;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class ExtintegrationITCase {
	
	private static Extintegration ext;
	static final String TEST_DATA_FILE = "restclient.testdata.uudemo.properties";

	private static String utbildningstillfalleUID = null;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		ext = new ExtintegrationImpl();
		Properties properties = TestUtil.getProperties(TEST_DATA_FILE);
		utbildningstillfalleUID = properties.getProperty("rest.extintgration.utbildning.uid");
	}
	
	@Test
	public void testUtannonseraUtbildningstillfalle() {	
		Utbildningstillfalle utbildningstillfalle = ext.utannonseraUtbildningstillfalle(utbildningstillfalleUID);	
		assertNotNull(utbildningstillfalle);
	}
	
	@Test
	public void testIsUtannonseraUtbildningstillfalle() {	
		UtannonseringLista utbildningstillfalle = ext.isUtbildningstillfalleUtannonserat(utbildningstillfalleUID);
		assertNotNull(utbildningstillfalle);
	}
	
}
