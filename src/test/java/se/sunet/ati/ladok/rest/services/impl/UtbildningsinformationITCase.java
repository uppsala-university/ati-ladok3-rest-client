package se.sunet.ati.ladok.rest.services.impl;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import se.ladok.schemas.Benamning;
import se.ladok.schemas.Benamningar;
import se.ladok.schemas.Organisation;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.PeriodID;
import se.ladok.schemas.utbildningsinformation.StudietaktID;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;

public class UtbildningsinformationITCase {

	private static Log log = LogFactory.getLog(UtbildningsinformationITCase.class);

	private static final String organisationUID = "05c81ef6-9232-11e6-8ca9-ef169e22488c";
	private static final String utbildningsmallUtbildningsinstansUID = "55555555-2007-0001-0001-000024000036";
	private static final String utbildningsmallUtbildningstillfalleUID = "55555555-2007-0004-0002-000052000036";
	private static final String utbildningsmallModulUID = "55555555-2007-0005-0001-000004000036";
	private static final String utbildningstillfalleUID = "68616ef5-8e12-11e6-9c62-ab9879144e80";
	private static final String utbildningstillfalleInstansUID = "1d5d97eb-8e11-11e6-9c62-ab9879144e80";
	private static final int periodID = 174050; // HT16


	Utbildningsinformation ui;

	@Before
	public void setUp() {
		ui = new UtbildningsinformationImpl();
	}

	@Test
	public void testSokAllaOrganisationer() {
		Organisationslista organisationer = ui.sokAllaOrganisationer();
		for (Organisation organisation : organisationer.getOrganisation()) {
			if ("ITS".equals(organisation.getKod())) {
				for (Benamning benamn : organisation.getBenamningar().getBenamning()) {
					if (benamn.getSprakkod().equals("en")) {
						assertTrue(benamn.getText().equals("- No translation available -"));
					} else if (benamn.getSprakkod().equals("sv")) {
						assertTrue(benamn.getText().equals("IT-sektionen, MDH"));
					}
				}
				assertTrue(organisationUID.equals(organisation.getUid()));
			}
		}
	}

	@Test
	public void testHamtaUtbildningstillfalle() throws Exception {

		Utbildningstillfalle utbildningstillfalle = ui
				.hamtaUtbildningstillfalleViaUtbildningsUtbildningstillfalleUID(utbildningstillfalleUID);

		assertTrue(utbildningstillfalleUID.equals(utbildningstillfalle.getUid()));
		assertTrue(utbildningstillfalleInstansUID.equals(utbildningstillfalle.getUtbildningsinstansUID()));
	}

	@Test
	public void testHamtaUtbildningsinstansViaUtbildningsinstansUID(){
		Utbildningsinstans utbildningsinstans = ui
				.hamtaUtbildningsinstansViaUtbildningsinstansUID(utbildningstillfalleInstansUID);

		assertTrue(utbildningstillfalleInstansUID.equalsIgnoreCase(utbildningsinstans.getUid()));

		for (Benamning benamn : utbildningsinstans.getBenamningar().getBenamning()) {
			if (benamn.getSprakkod().equals("en")) {
				assertTrue(benamn.getText().equals("Additive manufacturing"));
			} else if (benamn.getSprakkod().equals("sv")) {
				assertTrue(benamn.getText().equals("Additiv tillverkning"));
			}
		}
	}

	@Test
	public void testSkapaUtbildningsinstans(){
		Utbildningsinstans uiToSave = new Utbildningsinstans();
		Benamningar benamningar = new Benamningar();
		Benamning svenska = new Benamning();
		svenska.setSprakkod("sv");
		svenska.setText("TEST_SVENSKA");
		benamningar.getBenamning().add(svenska);
		uiToSave.setBenamningar(benamningar);
		uiToSave.setOmfattning("7.5");
		uiToSave.setOrganisationUID(organisationUID);
		uiToSave.setStatus(1);
		uiToSave.setUtbildningstypID(24);
		uiToSave.setUtbildningskod("TEST");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(periodID);
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
		uiToSave.setOrganisationUID(organisationUID);
		uiToSave.setStatus(1);
		// Kurs på grundnivå enl. 2007 förordn.
		uiToSave.setUtbildningstypID(22);
		uiToSave.setUtbildningskod("TEST");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(periodID);
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
		uiToSave.setOrganisationUID(organisationUID);
		uiToSave.setStatus(1);
		uiToSave.setUtbildningstypID(4);
		uiToSave.setUtbildningskod("TEST");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(periodID);
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
		utToSave.setOrganisationUID(organisationUID);
		utToSave.setStatus(1);
		// Kurstillfälle enl. 2007 förordn.
		utToSave.setUtbildningstypID(52);
		utToSave.setTillfalleskod("12345");

		Versionsinformation vInfo = new Versionsinformation();
		vInfo.setArSenasteVersion(true);
		vInfo.setVersionsnummer(1);
		PeriodID pid = new PeriodID();
		pid.setValue(periodID);
		vInfo.setGiltigFranPeriodID(pid);

		utToSave.setUtbildningsmallUID(utbildningsmallUtbildningstillfalleUID);

		Utbildningstillfalle utbildningstillfalle = ui.skapaUtbildningstillfalle(utToSave);
		assertNotNull(utbildningstillfalle);
	}
}
