package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.Student;
import se.ladok.schemas.studiedeltagande.SokresultatDeltagare;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurstillfalleQuery;
import se.sunet.ati.ladok.rest.services.Studiedeltagande;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class StudiedeltagandeUUDemoITCase {

	static final String TEST_DATA_FILE = "restclient.testdata.uudemo.properties"; 
	static Properties properties = null;

	@BeforeClass
	public static void beforeClass() throws IOException {
		properties = TestUtil.getProperties(TEST_DATA_FILE);
	}

	@Test
	public void testHamtaStudentViaPersonnummer() throws Exception {
		Studiedeltagande st = new StudiedeltagandeImpl();
		Student student = st.hamtaStudentViaPersonnummer(properties.getProperty(
				"rest.studiedeltagande.student.personnummer"));
		System.out.println("Student: " + student.getFornamn());
		assertNotNull(student);
	}

	@Test
	public void testSokDeltagareKurstillfalle() throws Exception {
		String kurstillfalleUID = properties.getProperty(
				"rest.studiedeltagande.kurstillfalle.uid");

		SokDeltagareKurstillfalleQuery sokDeltagareKurstillfalleQuery = SokDeltagareKurstillfalleQuery.builder()
				.kurstillfalleUID(kurstillfalleUID)
				.build();

		Studiedeltagande sd = new StudiedeltagandeImpl();
		SokresultatDeltagare deltagare = sd.sokDeltagareKurstillfalle(sokDeltagareKurstillfalleQuery);
		System.out.println("Deltagare: " + deltagare.getTotaltAntalPoster());
		assertNotNull(deltagare);
	}
}
