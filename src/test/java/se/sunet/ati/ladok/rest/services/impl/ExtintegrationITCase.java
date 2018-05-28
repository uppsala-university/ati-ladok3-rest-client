package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import se.ladok.schemas.Benamning;
import se.ladok.schemas.Benamningar;
import se.ladok.schemas.Organisation;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.*;
import se.sunet.ati.ladok.rest.services.Extintegration;
import se.sunet.ati.ladok.rest.services.LadokRestClientException;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

import javax.ws.rs.BadRequestException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.*;

import static org.junit.Assert.*;

public class ExtintegrationITCase {
	private static Log log = LogFactory.getLog(ExtintegrationITCase.class);

	private static Extintegration ext;

	@BeforeClass
	public static void beforeClass() throws Exception {
		ext = new ExtintegrationImpl();
	}
	
	@Test
	public void testUtannonseraUtbildningstillfalle() {
		String utbildningstillfalleUID = "2b9d23c8-31cf-11e8-8f26-9c84a82f4e3b";
		Utbildningstillfalle utbildningstillfalle = ext.utannonseraUtbildningstillfalle(utbildningstillfalleUID);	
		assertNotNull(utbildningstillfalle);
	}
	
}
