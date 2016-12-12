package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import se.ladok.schemas.utbildningsinformation.PeriodID;
import se.ladok.schemas.utbildningsinformation.StudietaktID;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Utbildningstyp;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class UtbildningsinformationITCase {
	private static final Integer STATUS_PABORJAD = 2;

	private static Log log = LogFactory.getLog(UtbildningsinformationITCase.class);

	private static final String utbildningsmallUtbildningsinstansUID = "55555555-2007-0001-0001-000024000036";
	private static final String utbildningsmallUtbildningstillfalleUID = "55555555-2007-0004-0002-000052000036";
	private static final String utbildningsmallModulUID = "55555555-2007-0005-0001-000004000036";
	private static final String utbildningstillfalleUID = "68616ef5-8e12-11e6-9c62-ab9879144e80";
	private static final String utbildningstillfalleInstansUID = "1d5d97eb-8e11-11e6-9c62-ab9879144e80";
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

	private String getOrganisationUID() {
		return properties.getProperty("rest.utbildningsinformation.organisation.uid");
	}

	private static int getPeriodID() {
		return Integer.parseInt(properties.getProperty("rest.utbildningsinformation.period.id"));
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
	public void testHamtaUtbildningsttypID() throws Exception {
		Utbildningstyp utbildningstyp = ui
				.hamtaUtbildningsttypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD);
		assertNotNull(utbildningstyp);
	}

	@Test
	public void testHamtaUtbildningstillfalle() throws Exception {

		Utbildningstillfalle utbildningstillfalle = ui.hamtaUtbildningstillfalleViaUtbildningsUtbildningstillfalleUID(utbildningstillfalleUID);

		assertEquals(utbildningstillfalleUID, utbildningstillfalle.getUid());
		assertEquals(utbildningstillfalleInstansUID, utbildningstillfalle.getUtbildningsinstansUID());
	}

	@Test
	public void testHamtaUtbildningsinstansViaUtbildningsinstansUID() {
		Utbildningsinstans utbildningsinstans = ui.hamtaUtbildningsinstansViaUtbildningsinstansUID(utbildningstillfalleInstansUID);

		assertTrue(utbildningstillfalleInstansUID.equalsIgnoreCase(utbildningsinstans.getUid()));

		for (Benamning benamn : utbildningsinstans.getBenamningar().getBenamning()) {
			if (benamn.getSprakkod().equals("en")) {
				assertEquals("Additive manufacturing", benamn.getText());
			} else if (benamn.getSprakkod().equals("sv")) {
				assertEquals("Additiv tillverkning", benamn.getText());
			}
		}
	}

	@Test
	public void testSkapaUtbildningsinstans() {
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
		uiToSave.setUtbildningsmallUID(utbildningsmallUtbildningsinstansUID);

		Utbildningsinstans savedIu = ui.skapaUtbildningsinstans(uiToSave);
		assertNotNull(savedIu);
		assertEquals(uiToSave.getUtbildningskod(), savedIu.getUtbildningskod());
	}

	@Test
	public void testSkapaKurs() {
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
		uiToSave.setUtbildningsmallUID("55555555-2007-0001-0005-000022000036");

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
		uiToSave.setUtbildningsmallUID(utbildningsmallModulUID);

		ui.skapaUnderliggandeUtbildningsinstans(uiToSave, utbildningstillfalleInstansUID);
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
		utToSave.setUtbildningstypID(getUtbildningstypID(UTBILDNINGSTYP_2007_KURSTILLFÄLLE));
		utToSave.setTillfalleskod("12345");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(getPeriodID());
		vInfo.setGiltigFranPeriodID(pid);

		utToSave.setUtbildningsmallUID(utbildningsmallUtbildningstillfalleUID);

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
		uiToSave.setOrganisationUID("ea459c31-b235-11e6-a17b-fa6452a340a2");
		uiToSave.setStatus(2);
		uiToSave.setUtbildningstypID(getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD));
		uiToSave.setUtbildningskod("OST66");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setAnteckning("Hej");
		vInfo.setArSenasteVersion(true);
		PeriodID pid = new PeriodID();
		pid.setValue(104302);
		vInfo.setGiltigFranPeriodID(pid);

		uiToSave.setVersionsinformation(vInfo);
		uiToSave.setUtbildningsmallUID("55555555-2007-0001-0005-000022000036");

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
}
