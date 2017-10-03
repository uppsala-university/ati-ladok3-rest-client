package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.Student;
import se.ladok.schemas.studiedeltagande.SokresultatDeltagare;
import se.ladok.schemas.studiedeltagande.Tillfallesdeltagande;
import se.ladok.schemas.studiedeltagande.TillfallesdeltagandeLista;
import se.ladok.schemas.utbildningsinformation.UtbildningstillfalleProjektion;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurstillfalleQuery;
import se.sunet.ati.ladok.rest.services.Studiedeltagande;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class StudiedeltagandeITCase {
	private static Log log = LogFactory.getLog(StudiedeltagandeITCase.class);
	private static Properties properties = null;
	private static Utbildningsinformation ui;
	private static UtbildningstillfalleProjektion utbildningstillfalle;

	@BeforeClass
	public static void beforeClass() throws Exception {
		properties = TestUtil.getProperties();
		ui = new UtbildningsinformationImpl();
		utbildningstillfalle = ui.sokUtbildningstillfallen(null, null, null,
								   properties.getProperty("rest.utbildningsinformation.utbildningstillfalle.utbildningstillfalleskod"),
								   properties.getProperty("rest.utbildningsinformation.utbildningsinstans.kod"),
								   null, null, null,
								   properties.getProperty("rest.utbildningsinformation.utbildningstillfalle.studieperiod"),
								   0,0,true, false, null).getResultat().get(0);
		if (utbildningstillfalle == null) {
			throw new Exception("Kunde inte läsa in utbildningstillfälle");
		}
		log.info("Har hämtat grundinformation för testerna");
	}

	private String getUtbildningstillfalleUID() {
		return utbildningstillfalle.getUid();
	}

	/**
	 * @todo This test currently fails with a 404 Not Found
	 */
//	@Test
	public void testHamtaPaborjadeKurser() throws Exception {
		Studiedeltagande st = new StudiedeltagandeImpl();
		Student student = st.hamtaStudentViaPersonnummer(properties.getProperty("rest.studiedeltagande.student.personnummer"));
		TillfallesdeltagandeLista tillfallesdeltagandeLista = st.hamtaPaborjadeKurser(student.getUid());
		assertNotNull(tillfallesdeltagandeLista);

		for(Tillfallesdeltagande tillfallesdeltagande : tillfallesdeltagandeLista.getTillfallesdeltaganden().getTillfallesdeltagande()) {
			System.out.println("Utbildningskod: " + tillfallesdeltagande.getUtbildningsinformation().getUtbildningskod());
		}
	}

	/**
	 * @todo This test currently fails with a 404 Not Found
	 */
//	@Test
	public void testHamtaStudentViaPersonnummer() throws Exception {
		Studiedeltagande st = new StudiedeltagandeImpl();
		Student student = st.hamtaStudentViaPersonnummer(properties.getProperty("rest.studiedeltagande.student.personnummer"));
		assertNotNull(student);

//		assertNotNull("");
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		JsonParser jp = new JsonParser();
//		JsonElement je = jp.parse(st.hamtaStudentViaPersonnummer("197209128672"));
//		String prettyJsonString = gson.toJson(je);
//
//		System.out.println(prettyJsonString);

//		assertNotNull("");
//		Source xmlInput = new StreamSource(new StringReader(st.hamtaStudentViaPersonnummer("194502051230")));
//		StringWriter stringWriter = new StringWriter();
//		StreamResult xmlOutput = new StreamResult(stringWriter);
//		TransformerFactory transformerFactory = TransformerFactory
//				.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//		transformer.setOutputProperty(
//				"{http://xml.apache.org/xslt}indent-amount",
//				String.valueOf("4"));
//		transformer.transform(xmlInput, xmlOutput);
//		System.out.println(xmlOutput.getWriter().toString());
	}

	@Test
	public void testSokDeltagareKurstillfalle() throws Exception {
		String kurstillfalleUID = getUtbildningstillfalleUID();

		SokDeltagareKurstillfalleQuery sokDeltagareKurstillfalleQuery = SokDeltagareKurstillfalleQuery.builder()
				.kurstillfalleUID(kurstillfalleUID)
				.build();

		Studiedeltagande sd = new StudiedeltagandeImpl();
		SokresultatDeltagare sokresultatDeltagare = sd.sokDeltagareKurstillfalle(sokDeltagareKurstillfalleQuery);
		assertNotNull(sokresultatDeltagare);
	}
}
