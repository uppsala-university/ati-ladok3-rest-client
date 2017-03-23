package se.sunet.ati.ladok.rest.services.impl;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.ws.rs.BadRequestException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.Benamning;
import se.ladok.schemas.Benamningar;
import se.ladok.schemas.Organisation;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.*;
import se.sunet.ati.ladok.rest.services.LadokRestClientException;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

import static org.junit.Assert.*;

public class UtbildningsinformationITCase {
	private static Log log = LogFactory.getLog(UtbildningsinformationITCase.class);

	private static final String DATUM = "2016-01-01";
	private static final Integer STATUS_PABORJAD = 2;
	private static final int STUDIEORDNING_ID = 1;

	// Nationellt beslutade koder som ska vara samma för alla lärosäten
	private static final String UTBILDNINGSTYP_2007_KURS_AVANCERAD_KOD = "2007AKURS";
	private static final String UTBILDNINGSTYP_2007_KURS_GRUND_KOD = "2007GKURS";
	private static final String UTBILDNINGSTYP_2007_KURSTILLFÄLLE = "2007KTF";
	private static final String UTBILDNINGSTYP_2007_MODUL_MED_OMFATTNING = "2007MOD";

	private static Organisation organisation;
	private static Period period;
	private static Properties properties = null;
	private static Utbildningsinformation ui;
	private static UtbildningProjektion utbildningsinstans;

	@BeforeClass
	public static void beforeClass() throws Exception {
		properties = TestUtil.getProperties();
		ui = new UtbildningsinformationImpl();
		organisation = ui.sokOrganisationer(properties.getProperty("rest.utbildningsinformation.organisation.kod")).getOrganisation().get(0);
		if (organisation == null) {
			throw new Exception("Kunde inte läsa in organisation");
		}
		period = ui.hamtaPeriodViaKod(properties.getProperty("rest.utbildningsinformation.grunddata.period.kod"));
		if (period == null) {
			throw new Exception("Kunde inte läsa in period");
		}
		utbildningsinstans = ui.hamtaUtbildningsinstansViaKod(properties.getProperty("rest.utbildningsinformation.utbildningsinstans.kod")).get(0);
		if (utbildningsinstans == null) {
			throw new Exception("Kunde inte läsa in utbildningsinstans");
		}
		log.info("Har hämtat grundinformation för testerna");
	}

	private String getOrganisationUID() {
		return organisation.getUid();
	}

	private static int getPeriodID() {
		return Integer.parseInt(period.getID());
	}

	private static String getPeriodKod() {
		return properties.getProperty("rest.utbildningsinformation.grunddata.period.kod");
	}

	private String getUtbildningsinstansBenamningEn() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsinstans.benamn.en");
	}

	private String getUtbildningsinstansBenamningSv() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsinstans.benamn.sv");
	}

	private String getUtbildningsinstansKod() {
		return properties.getProperty("rest.utbildningsinformation.utbildningsinstans.kod");
	}

	private String getUtbildningsinstansUID() {
		return utbildningsinstans.getUid();
	}

	private String getUtbildningsmallUID(String utbildningstypKod) {
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(getUtbildningstypID(utbildningstypKod), DATUM);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypKod);
		return lokalUtbildningsmall.getUid();
	}

	private String getUtbildningsmallUIDModul() {
		return getUtbildningsmallUID(UTBILDNINGSTYP_2007_MODUL_MED_OMFATTNING);
	}

	private String getUtbildningsmallUIDKursAvancerad() {
		return getUtbildningsmallUID(UTBILDNINGSTYP_2007_KURS_AVANCERAD_KOD);
	}

	private String getUtbildningsmallUIDKursGrund() {
		return getUtbildningsmallUID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD);
	}

	private String getUtbildningsmallUIDUtbildningstillfalle() {
		return getUtbildningsmallUID(UTBILDNINGSTYP_2007_KURSTILLFÄLLE);
	}

	private String getUtbildningstillfalleUID() {
		return properties.getProperty("rest.utbildningsinformation.utbildningstillfalle.uid");
	}

	private int getUtbildningstypID(String utbildningstypKod) {
		Utbildningstyp utbildningstyp = ui
				.hamtaUtbildningstypViaKod(utbildningstypKod);
		log.info("Hämtade utbildningstypID " + utbildningstyp.getID() + " för koden " + utbildningstypKod);
		return Integer.parseInt(utbildningstyp.getID());
	}

	@Test
	public void testHamtaLokalUtbildningsmallKursAvancerad() {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_AVANCERAD_KOD);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, DATUM);
		assertNotNull(lokalUtbildningsmall);
		assertNotNull(ui.hamtaLokalUtbildningsmall(lokalUtbildningsmall.getUid()));
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaLokalUtbildningsmallKursGrund() {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, DATUM);
		assertNotNull(lokalUtbildningsmall);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaLokalUtbildningsmallKurstillfalle() {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_KURSTILLFÄLLE);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, DATUM);
		assertNotNull(lokalUtbildningsmall);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaLokalUtbildningsmallModulMedOmfattning() {
		int utbildningstypID = getUtbildningstypID(UTBILDNINGSTYP_2007_MODUL_MED_OMFATTNING);
		LokalUtbildningsmall lokalUtbildningsmall = ui
				.hamtaLokalUtbildningsmall(utbildningstypID, DATUM);
		assertNotNull(lokalUtbildningsmall);
		log.info("Hämtade lokal utbildningsmall med UID " + lokalUtbildningsmall.getUid() + " för utbildningstyp " + utbildningstypID);
	}

	@Test
	public void testHamtaNivaerInomStudieordning() {
		NivaerInomStudieordning nivaerInomStudieordning = ui.hamtaNivaerInomStudieordning();
		assertNotNull(nivaerInomStudieordning);
	}

	@Test
	public void testHamtaNivaInomStudieordning() {
		String nivaKod = properties.getProperty("rest.utbildningsinformation.nivainomstudieordning.kod");
		NivaInomStudieordning nivaInomStudieordning = ui.hamtaNivaInomStudieordning(nivaKod);
		assertNotNull(nivaInomStudieordning);
	}

	@Test
	public void testHamtaPerioder() {
		List<Period> perioder = ui.hamtaPerioder();
		assertNotNull(perioder);
		log.info("Hämtade " + perioder.size() + " perioder");
		assertTrue(perioder.size() > 0);
		for(Period period : perioder) {
			log.debug("Hämtade perioden " + period.getKod() + " (" + period.getID() + ") - " + period.getBenamningar().getBenamning().get(0).getText());
		}
	}

	@Test
	public void testHamtaPeriodViaKod() {
		Period period = ui.hamtaPeriodViaKod(getPeriodKod());
		assertNotNull(period);
		log.info("Hämtade perioden " + period.getKod() + " (" + period.getID() + ") - " + period.getBenamningar().getBenamning().get(0).getText());
		assertEquals(getPeriodKod(), period.getKod());
	}

	@Test
	public void testHamtaUtbildningsinstansViaKod() {
		String utbildningskod = getUtbildningsinstansKod();
		List<UtbildningProjektion> utbildningProjektioner = ui.hamtaUtbildningsinstansViaKod(utbildningskod,
												     STUDIEORDNING_ID,
												     getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD));
		log.info("Hämtade " + utbildningProjektioner.size() + " utbildningsinstanser för utbildningskod " + utbildningskod);

		UtbildningProjektion utbildningsinstans = utbildningProjektioner.get(0);
		log.info("Hämtade utbildningsinstans med UID " + utbildningsinstans.getUid() + " för utbildningskod " + utbildningskod);
//		assertTrue(getUtbildningsinstansUID().equalsIgnoreCase(utbildningsinstans.getUid()));

		log.info("Utbildningsinstansens (språkoberoende) benämning: " + utbildningsinstans.getBenamning());
		for (Benamning benamn : utbildningsinstans.getBenamningar().getBenamning()) {
			if (benamn.getSprakkod().equals("en")) {
				log.info("Utbildningsinstansens engelska benämning: " + benamn.getText());
				assertEquals(getUtbildningsinstansBenamningEn(), benamn.getText());
			} else if (benamn.getSprakkod().equals("sv")) {
				log.info("Utbildningsinstansens svenska benämning: " + benamn.getText());
				assertEquals(getUtbildningsinstansBenamningSv(), benamn.getText());
			}
		}
	}

	@Test
	public void testHamtaUtbildningsinstansViaEndastKod() {
		String utbildningskod = getUtbildningsinstansKod();
		List<UtbildningProjektion> utbildningProjektioner = ui.hamtaUtbildningsinstansViaKod(utbildningskod);
		log.info("Hämtade " + utbildningProjektioner.size() + " utbildningsinstanser för utbildningskod " + utbildningskod);

		UtbildningProjektion utbildningsinstans = utbildningProjektioner.get(0);
		log.info("Hämtade utbildningsinstans med UID " + utbildningsinstans.getUid() + " för utbildningskod " + utbildningskod);
		assertTrue(getUtbildningsinstansUID().equalsIgnoreCase(utbildningsinstans.getUid()));

		for (Benamning benamn : utbildningsinstans.getBenamningar().getBenamning()) {
			if (benamn.getSprakkod().equals("en")) {
				assertEquals(getUtbildningsinstansBenamningEn(), benamn.getText());
			} else if (benamn.getSprakkod().equals("sv")) {
				assertEquals(getUtbildningsinstansBenamningSv(), benamn.getText());
			}
		}
	}


	@Test
	public void testHamtaUtbildningsinstansViaUID() {
		Utbildningsinstans utbildningsinstans = ui.hamtaUtbildningsinstansViaUID(getUtbildningsinstansUID());

		assertTrue(getUtbildningsinstansUID().equalsIgnoreCase(utbildningsinstans.getUid()));

		for (Benamning benamn : utbildningsinstans.getBenamningar().getBenamning()) {
			if (benamn.getSprakkod().equals("en")) {
				assertEquals(getUtbildningsinstansBenamningEn(), benamn.getText());
			} else if (benamn.getSprakkod().equals("sv")) {
				assertEquals(getUtbildningsinstansBenamningSv(), benamn.getText());
			}
		}
	}

	@Test
	public void testHamtaUtbildningstillfalleViaUID() {

		Utbildningstillfalle utbildningstillfalle = ui.hamtaUtbildningstillfalleViaUID(getUtbildningstillfalleUID());

		assertEquals(getUtbildningstillfalleUID(), utbildningstillfalle.getUid());
		assertEquals(getUtbildningsinstansUID(), utbildningstillfalle.getUtbildningsinstansUID());
	}

	@Test
	public void testHamtaUtbildningstypViaKod() {
		Utbildningstyp utbildningstyp = ui
				.hamtaUtbildningstypViaKod(UTBILDNINGSTYP_2007_KURS_GRUND_KOD);
		assertNotNull(utbildningstyp);
	}

	@Test
	public void testSkapaUtbildningsinstansKursAvancerad() {
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
		uiToSave.setUtbildningskod(getRandomUtbildningskod());

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
	public void testSkapaUtbildningsinstansKursGrund() {
		Utbildningsinstans uiToSave = generateKursGrund();
		Utbildningsinstans savedIu = ui.skapaUtbildningsinstans(uiToSave);
		assertNotNull(savedIu);
		assertEquals(uiToSave.getUtbildningskod(), savedIu.getUtbildningskod());
	}

	@Test
	public void testSkapaOchAvvecklaKursGrund() throws DatatypeConfigurationException {
		Utbildningsinstans uiToSave = generateKursGrund();
		Utbildningsinstans savedIu = ui.skapaUtbildningsinstans(uiToSave);
		assertNotNull(savedIu);
		assertEquals(uiToSave.getUtbildningskod(), savedIu.getUtbildningskod());

		String utbildningID = savedIu.getUtbildningUID();
		Beslut beslut = new Beslut();
		beslut.setBeslutstypID(4);
		beslut.setBeslutsfattare("Integrationstest");
		beslut.setAnteckning("Integrationstest avvecklar kurs");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis());
		beslut.setBeslutsdatum(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));

		ui.avvecklaUtbildning(utbildningID, beslut);
		Utbildningsinstans ladokKopia = ui.hamtaUtbildningsinstansViaUID(savedIu.getUid());
		assertTrue(ladokKopia.isAvvecklad());
	}

	private Utbildningsinstans generateKursGrund() {
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
		uiToSave.setUtbildningskod(getRandomUtbildningskod());

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(getPeriodID());
		vInfo.setGiltigFranPeriodID(pid);

		uiToSave.setVersionsinformation(vInfo);
		uiToSave.setUtbildningsmallUID(getUtbildningsmallUIDKursGrund());
		return uiToSave;
	}

	@Test
	public void testSkapaUtbildningsinstansOchNyVersion() {
		Utbildningsinstans uiToSave = new Utbildningsinstans();
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
		uiToSave.setUtbildningskod(getRandomUtbildningskod());

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

		Utbildningsinstans savedIuVer2 = ui.skapaUtbildningsinstansNyVersion(savedIuVer1.getUid(), vInfo);
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

	/**
	* Test för att skapa en underliggande Utbildningsinstans.
	* Kräver att den överliggande utbildningsinstansen inte är i komplett status (3).
	* Förväntat resultat är då att anropet mot {@link Utbildningsinformation#skapaUtbildningsinstansUnderliggande} kastar {@link BadRequestException}.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSkapaUtbildningsinstansUnderliggande() {
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

		try {
			ui.skapaUtbildningsinstansUnderliggande(uiToSave, getUtbildningsinstansUID());
		} catch (LadokRestClientException e) {
			assertEquals(400, e.getHttpStatusCode());
			assertNotNull(e.getLadokException());
		}
	}

	/**
	 * Test för att skapa en Utbildningsinstans.
	 * @throws Exception
	 */
	@Test
	public void testSkapaUtbildningstillfalle() {
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
	public void testSokOrganisationer() {
		String kod = properties.getProperty("rest.utbildningsinformation.organisation.kod");
		String benamnEn = properties.getProperty("rest.utbildningsinformation.organisation.benamn.en");
		String benamnSv = properties.getProperty("rest.utbildningsinformation.organisation.benamn.sv");
		Organisationslista organisationer = ui.sokOrganisationer(kod);
		assertFalse(organisationer.getOrganisation().isEmpty());
		assertEquals(1, organisationer.getOrganisation().size());

		Organisation organisation = organisationer.getOrganisation().get(0);
		log.info("UID=" + organisation.getUid() + "  Namn='" + organisation.getBenamningar().getBenamning().get(0).getText() + "'");
		assertEquals(kod, organisation.getKod());
		for (Benamning benamning : organisation.getBenamningar().getBenamning()) {
			if (benamning.getSprakkod().equals("en")) {
				assertEquals(benamnEn, benamning.getText());
			} else if (benamning.getSprakkod().equals("sv")) {
				assertEquals(benamnSv, benamning.getText());
			}
		}
	}

	@Test
	public void testSokUtbildningstillfallen() {
		SokresultatUtbildningstillfalleProjektion resultat =
			ui.sokUtbildningstillfallen("", "", "", "", "", "", "", "3", "2016-08-29_2017-01-15", 1, 20, true, true, "");
		assertNotNull(resultat);
	}

	@Test
	public void hamtaHuvudomraden() {
		Huvudomraden huvudomraden = ui.hamtaHuvudamraden();
		assertNotNull(huvudomraden);
	}

	@Test
	public void testHamtaAttributdefinitioner() {
		final List<Attributdefinition> attributdefinitioner = ui.hamtaAttributdefinitionerViaUtbildningstyp(getUtbildningstypID(UTBILDNINGSTYP_2007_KURS_GRUND_KOD));
		log.info("Hämtade attributdefinitioner för utbildningstyp " + UTBILDNINGSTYP_2007_KURS_GRUND_KOD);
		assertNotNull(attributdefinitioner);
		assertFalse(attributdefinitioner.isEmpty());
	}

	/**
	 * Slumpar fram en utbildningskod för de tester som behöver en unik kod. Metoden garanterar ej att det blir unikt,
	 * men chansen för duplikat är liten med 59652323 kombinationer.
	 *
	 * @return En slumpvis vald utbildningskod på max 6 tecken och börjar med 'Z'
	 */
	private String getRandomUtbildningskod() {
		Random random = new Random();
		String randomKod = ("Z" + Integer.toString(Math.abs(random.nextInt(Integer.MAX_VALUE / 36)), 36)).toUpperCase();
		return randomKod;
	}
}
