package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.BadRequestException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.Benamning;
import se.ladok.schemas.Benamningar;
import se.ladok.schemas.Organisation;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.Kurs2007GrundAvancerad;
import se.ladok.schemas.utbildningsinformation.LokalUtbildningsmall;
import se.ladok.schemas.utbildningsinformation.NivaInomStudieordning;
import se.ladok.schemas.utbildningsinformation.NivaerInomStudieordning;
import se.ladok.schemas.utbildningsinformation.PeriodID;
import se.ladok.schemas.utbildningsinformation.StudietaktID;
import se.ladok.schemas.utbildningsinformation.UtbildningProjektion;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Utbildningstyp;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class UtbildningsinformationITCase {
	private static Log log = LogFactory.getLog(UtbildningsinformationITCase.class);

	private static final Integer STATUS_PABORJAD = 2;
	private static final int STUDIEORDNING_ID = 1;

	// Nationellt beslutade koder som ska vara samma för alla lärosäten
	private static final String UTBILDNINGSTYP_2007_KURS_AVANCERAD_KOD = "2007AKURS";
	private static final String UTBILDNINGSTYP_2007_KURS_GRUND_KOD = "2007GKURS";
	private static final String UTBILDNINGSTYP_2007_KURSTILLFÄLLE = "2007KTF";
	private static final String UTBILDNINGSTYP_2007_MODUL_MED_OMFATTNING = "2007MOD";

	private static Properties properties = null;

	private Utbildningsinformation ui;

	@Before
	public void setUp() {
		ui = new UtbildningsinformationImpl();
	}

	@BeforeClass
	public static void beforeClass() throws IOException {
		properties = TestUtil.getProperties();
	}

	private String getUtbildningsinstansKod() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsinstans.kod");
	}

	private String getOrganisationUID() {
		return properties.getProperty("rest.utbildningsinformation.organisation.uid");
	}

	private static int getPeriodID() {
		return Integer.parseInt(properties.getProperty("rest.utbildningsinformation.period.id"));
	}

	private String getUtbildningsinstansUID() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsinstans.uid");
	}

	private String getUtbildningsmallUIDModul() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsmall.uid.modul");
	}

	private String getUtbildningsmallUIDKursAvancerad() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsmall.uid.kurs.avancerad");
	}

	private String getUtbildningsmallUIDKursGrund() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsmall.uid.kurs.grund");
	}

	private String getUtbildningsmallUIDUtbildningstillfalle() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsmall.uid.utbildningstillfalle");
	}

	private String getUtbildningstillfalleUID() {
		return properties.getProperty("rest.utbildningsinformation.utbildningstillfalle.uid");
	}

	private int getUtbildningstypID(String utbildningstypKod) {
		Utbildningstyp utbildningstyp = ui
				.hamtaUtbildningsttypID(utbildningstypKod);
		log.info("Hämtade utbildningstypID " + utbildningstyp.getID() + " för koden " + utbildningstypKod);
		return Integer.parseInt(utbildningstyp.getID());
	}

	@Test
	public void testSokAllaOrganisationer() {
		Organisationslista organisationer = ui.sokAllaOrganisationer();
		String benamnEn = properties.getProperty("rest.utbildningsinformation.organisation.benamn.en");
		String benamnSv = properties.getProperty("rest.utbildningsinformation.organisation.benamn.sv");
		String kod = properties.getProperty("rest.utbildningsinformation.organisation.kod");
		for (Organisation organisation : organisationer.getOrganisation()) {
			log.debug("UID=" + organisation.getUid() + "  Namn='" + organisation.getBenamningar().getBenamning().get(0).getText() + "'");
			if (kod.equals(organisation.getKod())) {
				for (Benamning benamn : organisation.getBenamningar().getBenamning()) {
					if (benamn.getSprakkod().equals("en")) {
						assertEquals(benamnEn, benamn.getText());
					} else if (benamn.getSprakkod().equals("sv")) {
						assertEquals(benamnSv, benamn.getText());
					}
				}
				assertEquals(getOrganisationUID(), organisation.getUid());
			}
		}
	}

	@Test
	public void testHamtaLokalUtbildningsmallKursAvancerad() throws Exception {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_AVANCERAD_KOD);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, "2016-01-01");
		assertNotNull(lokalUtbildningsmall);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaLokalUtbildningsmallKursGrund() throws Exception {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, "2016-01-01");
		assertNotNull(lokalUtbildningsmall);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaLokalUtbildningsmallKurstillfalle() throws Exception {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_KURSTILLFÄLLE);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, "2016-01-01");
		assertNotNull(lokalUtbildningsmall);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaLokalUtbildningsmallModulMedOmfattning() throws Exception {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_MODUL_MED_OMFATTNING);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, "2016-01-01");
		assertNotNull(lokalUtbildningsmall);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaUtbildningsttypID() throws Exception {
		Utbildningstyp utbildningstyp = ui
				.hamtaUtbildningsttypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD);
		assertNotNull(utbildningstyp);
	}

	@Test
	public void testHamtaUtbildningstillfalle() throws Exception {

		Utbildningstillfalle utbildningstillfalle = ui.hamtaUtbildningstillfalleViaUtbildningstillfalleUID(getUtbildningstillfalleUID());

		assertEquals(getUtbildningstillfalleUID(), utbildningstillfalle.getUid());
		assertEquals(getUtbildningsinstansUID(), utbildningstillfalle.getUtbildningsinstansUID());
	}

	@Test
	public void testHamtaUtbildningsinstansViaKod() {
		String utbildningskod = getUtbildningsinstansKod();
		List<UtbildningProjektion> utbildningProjektioner = ui.hamtaUtbildningsinstansViaKod(utbildningskod,
												     STUDIEORDNING_ID,
												     getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD));
		log.info("Hämtade " + utbildningProjektioner.size() + " utbildningsinstanser för utbildningskod " + utbildningskod);

		UtbildningProjektion utbildningsinstans = utbildningProjektioner.get(0);
		assertTrue(getUtbildningsinstansUID().equalsIgnoreCase(utbildningsinstans.getUid()));
		log.info("Hämtade utbildningsinstans med UID " + utbildningsinstans.getUid() + " för utbildningskod " + utbildningskod);

		for (Benamning benamn : utbildningsinstans.getBenamningar().getBenamning()) {
			if (benamn.getSprakkod().equals("en")) {
				assertEquals("Additive manufacturing", benamn.getText());
			} else if (benamn.getSprakkod().equals("sv")) {
				assertEquals("Additiv tillverkning", benamn.getText());
			}
		}
	}

	@Test
	public void testHamtaUtbildningsinstansViaUtbildningsinstansUID() {
		Utbildningsinstans utbildningsinstans = ui.hamtaUtbildningsinstansViaUtbildningsinstansUID(getUtbildningsinstansUID());

		assertTrue(getUtbildningsinstansUID().equalsIgnoreCase(utbildningsinstans.getUid()));

		for (Benamning benamn : utbildningsinstans.getBenamningar().getBenamning()) {
			if (benamn.getSprakkod().equals("en")) {
				assertEquals("Additive manufacturing", benamn.getText());
			} else if (benamn.getSprakkod().equals("sv")) {
				assertEquals("Additiv tillverkning", benamn.getText());
			}
		}
	}

	@Test
	public void testSkapaKursAvancerad() {
		Utbildningsinstans uiToSave = new Utbildningsinstans();
		Benamningar benamningar = new Benamningar();
		Benamning svenska = new Benamning();
		svenska.setSprakkod("sv");
		svenska.setText("TEST_SVENSKA");
		benamningar.getBenamning().add(svenska);
		uiToSave.setBenamningar(benamningar);
		uiToSave.setOmfattning("7.5");
		uiToSave.setOrganisationUID(getOrganisationUID());
		uiToSave.setStatus(1);
		uiToSave.setUtbildningstypID(getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_AVANCERAD_KOD));
		uiToSave.setUtbildningskod("TEST");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(getPeriodID());
		vInfo.setGiltigFranPeriodID(pid);

		uiToSave.setVersionsinformation(vInfo);
		uiToSave.setUtbildningsmallUID(getUtbildningsmallUIDKursAvancerad());

		Utbildningsinstans savedIu = ui.skapaUtbildningsinstans(uiToSave);
		assertNotNull(savedIu);
		assertEquals(uiToSave.getUtbildningskod(), savedIu.getUtbildningskod());
	}

	@Test
	public void testSkapaKursGrund() {
		Utbildningsinstans uiToSave = new Utbildningsinstans();
		Benamningar benamningar = new Benamningar();
		Benamning svenska = new Benamning();
		svenska.setSprakkod("sv");
		svenska.setText("TEST_SVENSKA");
		benamningar.getBenamning().add(svenska);
		uiToSave.setBenamningar(benamningar);
		uiToSave.setOmfattning("7.5");
		uiToSave.setOrganisationUID(getOrganisationUID());
		uiToSave.setStatus(1);
		uiToSave.setUtbildningstypID(getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD));
		uiToSave.setUtbildningskod("TEST");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(getPeriodID());
		vInfo.setGiltigFranPeriodID(pid);

		uiToSave.setVersionsinformation(vInfo);
		uiToSave.setUtbildningsmallUID(getUtbildningsmallUIDKursGrund());

		Utbildningsinstans savedIu = ui.skapaUtbildningsinstans(uiToSave);
		assertNotNull(savedIu);
		assertEquals(uiToSave.getUtbildningskod(), savedIu.getUtbildningskod());
	}

	/**
	* Test för att skapa en underliggande Utbildningsinstans.
	* Kräver att den överliggande utbildningsinstansen inte är i komplett status (3).
	* Förväntat resultat är då att anropet mot {@link Utbildningsinformation#skapaUnderliggandeUtbildningsinstans} kastar {@link BadRequestException}.
	 *
	 * @throws Exception
	 */
	@Test(expected = BadRequestException.class)
	public void testSkapaUnderliggandeUtbildningsinstans() throws Exception {
		Utbildningsinstans uiToSave = new Utbildningsinstans();
		Benamningar benamningar = new Benamningar();
		Benamning svenska = new Benamning();
		svenska.setSprakkod("sv");
		svenska.setText("TEST_SVENSKA");
		benamningar.getBenamning().add(svenska);
		uiToSave.setBenamningar(benamningar);
		uiToSave.setOmfattning("1.0");
		uiToSave.setOrganisationUID(getOrganisationUID());
		uiToSave.setStatus(1);
		uiToSave.setUtbildningstypID(getUtbildningstypID(UTBILDNINGSTYP_2007_MODUL_MED_OMFATTNING));
		uiToSave.setUtbildningskod("TEST");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(getPeriodID());
		vInfo.setGiltigFranPeriodID(pid);

		uiToSave.setVersionsinformation(vInfo);
		uiToSave.setUtbildningsmallUID(getUtbildningsmallUIDModul());

		ui.skapaUnderliggandeUtbildningsinstans(uiToSave, getUtbildningsinstansUID());
	}

	/**
	 * Test för att skapa en Utbildningsinstans.
	 * @throws Exception
	 */
	@Test
	public void testSkapaUtbildningstillfalle() throws Exception {
		Utbildningstillfalle utToSave = new Utbildningstillfalle();
		StudietaktID studietakt = new StudietaktID();
		// Studietakt på halvfart
		studietakt.setValue(4);
		utToSave.setStudietaktID(studietakt);
		utToSave.setOrganisationUID(getOrganisationUID());
		utToSave.setStatus(1);
		utToSave.setUtbildningsinstansUID(getUtbildningsinstansUID());
		utToSave.setUtbildningstypID(getUtbildningstypID(UTBILDNINGSTYP_2007_KURSTILLFÄLLE));
		utToSave.setTillfalleskod("12345");

		PeriodID pid = new PeriodID();
		pid.setValue(getPeriodID());
		utToSave.setStartperiodID(pid);
		utToSave.setUtbildningsmallUID(getUtbildningsmallUIDUtbildningstillfalle());

		Utbildningstillfalle utbildningstillfalle = ui.skapaUtbildningstillfalle(utToSave);
		assertNotNull(utbildningstillfalle);
	}

	@Test
	public void testSkapaUtbildningsinstansOchNyVersion() {
		Kurs2007GrundAvancerad uiToSave = new Kurs2007GrundAvancerad();
		Benamningar benamningar = new Benamningar();
		Benamning svenska = new Benamning();
		svenska.setSprakkod("sv");
		svenska.setText("TEST_SVENSKA");
		Benamning engelska = new Benamning();
		engelska.setSprakkod("en");
		engelska.setText("TEST_ENGLISH");
		benamningar.getBenamning().add(svenska);
		benamningar.getBenamning().add(engelska);
		uiToSave.setBenamningar(benamningar);
		uiToSave.setOmfattning("7.5");
		uiToSave.setOrganisationUID(getOrganisationUID());
		uiToSave.setStatus(2);
		uiToSave.setUtbildningstypID(getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD));
		uiToSave.setUtbildningskod("OST66");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setAnteckning("Hej");
		vInfo.setArSenasteVersion(true);
		PeriodID pid = new PeriodID();
		pid.setValue(getPeriodID());
		vInfo.setGiltigFranPeriodID(pid);

		uiToSave.setVersionsinformation(vInfo);
		uiToSave.setUtbildningsmallUID(getUtbildningsmallUIDKursGrund());

		Utbildningsinstans savedIuVer1 = ui.skapaUtbildningsinstans(uiToSave);
		assertNotNull(savedIuVer1);
		assertEquals(uiToSave.getUtbildningskod(), savedIuVer1.getUtbildningskod());
		assertEquals(Integer.valueOf(1), savedIuVer1.getVersionsinformation().getVersionsnummer());

		Utbildningsinstans savedIuVer2 = ui.skapaNyVersionUtbildningsinstans(savedIuVer1.getUid(), vInfo);
		assertNotNull(savedIuVer2);
		assertEquals(uiToSave.getUtbildningskod(), savedIuVer2.getUtbildningskod());
		assertEquals(Integer.valueOf(2), savedIuVer2.getVersionsinformation().getVersionsnummer());

		for (Benamning b : savedIuVer2.getBenamningar().getBenamning()) {
				b.setText(b.getText() + "2");
		}

		Utbildningsinstans updatedIuVer2 = ui.uppdateraUtbildningsinstans(savedIuVer2);
		assertNotNull(updatedIuVer2);
		assertEquals(uiToSave.getUtbildningskod(), updatedIuVer2.getUtbildningskod());

		for (Benamning b : updatedIuVer2.getBenamningar().getBenamning()) {
			if (engelska.getSprakkod().equals(b.getSprakkod())) {
				assertEquals(engelska.getText()+"2", b.getText());
			} else if (svenska.getSprakkod().equals(b.getSprakkod())) {
				assertEquals(svenska.getText()+"2", b.getText());
			}
		}

		Utbildningsinstans statusPaborjadIuVer2 = ui.utbildningsinstansTillStatusPaborjad(updatedIuVer2.getUid());
		assertEquals(STATUS_PABORJAD, statusPaborjadIuVer2.getStatus());
	}

	@Test
	public void testListaNivaerInomStudieordning() throws Exception {
		NivaerInomStudieordning nivaerInomStudieordning = ui.listaNivaerInomStudieordning();
		assertNotNull(nivaerInomStudieordning);
	}

	@Test
	public void testHamtaNivaInomStudieordning() throws Exception {
		String nivaKod = properties.getProperty("rest.utbildningsinformation.nivainomstudieordning.kod");
		NivaInomStudieordning nivaInomStudieordning = ui.hamtaNivaInomStudieordning(nivaKod);
		assertNotNull(nivaInomStudieordning);
	}
}
