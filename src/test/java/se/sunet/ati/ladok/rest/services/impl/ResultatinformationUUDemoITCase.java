package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import se.ladok.schemas.Benamningar;
import se.ladok.schemas.Datumperiod;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.Student;
import se.ladok.schemas.resultat.*;
import se.sunet.ati.ladok.rest.services.Resultatinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class ResultatinformationUUDemoITCase {
	private static Log log = LogFactory.getLog(ResultatinformationUUDemoITCase.class);
	private static Properties properties = null;
	static final String TEST_DATA_FILE = "restclient.testdata.uudemo.properties"; 
	private static Resultatinformation ri;

	@BeforeClass
	public static void beforeClass() throws IOException {
		ri = new ResultatinformationImpl();
		properties = TestUtil.getProperties(TEST_DATA_FILE);
	}

	//resultat/studieresultat/student/{studentUID}/utbildningstillfalle/{kurstillfalleUID}
	//@Test
	public void testHamtaStudieResultatForStudentPaUtbildningstillfalle() throws Exception {
		String kurstillfalleUID = properties.getProperty(
				"rest.utbildningsinformation.kurstillfalle2.uid");
		String studentUID = properties.getProperty(
				"rest.studiedeltagande.student.uid");
		Studieresultat resultat = ri.hamtaStudieResultatForStudentPaUtbildningstillfalle(studentUID, kurstillfalleUID);

		assertNotNull(resultat);
		System.out.println("kurstillfället: " + resultat.getAktuelltKurstillfalle());

		for(ResultatPaUtbildning resu : resultat.getResultatPaUtbildningar().getResultatPaUtbildning()) {
			if (resu.getSenastAttesteradeResultat()!= null){
				System.out.println("betygsgrad: " +resu.getSenastAttesteradeResultat().getBetygsgrad());
			}
		}
	}

	//resultat/studieresultat/resultat/student/{studentUID}
	//@Test
	public void testHamtaStudentResultatForStudent() throws Exception {
		ResultatLista resultatLista = ri.hamtaStudieResultatForStudent(properties.getProperty("rest.studiedeltagande.student.uid"));		
		assertNotNull(resultatLista);

		for(Resultat resultat : resultatLista.getResultat()) {
			System.out.println("Kurstifalleskod: " + resultat.getKurstillfalleUID());
		}
	}

	//resultat/utbildningsinstans/{utbildningsinstansUID}/moduler
	//@Test
	public void testHamtaModulerForKursistans() throws Exception {
		Utbildningsinstans utbildningsInstans = ri.hamtaModulerForUtbildningsinstans(properties.getProperty("rest.utbildningsinformation.kurstillfalle.uid"));
		assertNotNull(utbildningsInstans);

		List<Utbildningsinstans> utbs = utbildningsInstans.getModuler();
		for (Utbildningsinstans utb : utbs) {
			for (Benamning be : utb.getBenamning()){
				System.out.println("moduls namn: " + be.getText());}
			System.out.println("omfattning: " + utb.getOmfattning());
			System.out.println("enhet: " + utb.getEnhet());
			System.out.println("utbildningsKod: " + utb.getUtbildningskod());

		}
	}

	//POST
	//resultat/studieresultat/{studieresultatUID}/utbildning/{utbildningUID}/resultat
	//@Test
	@Deprecated
	public void testSkaparesultatForStudent() throws Exception {
		SkapaResultat resultat = new SkapaResultat();	
		resultat.setBetygsgrad(Integer.valueOf(2407));
		resultat.setBetygsskalaID(Integer.valueOf(433));
		resultat.getNoteringar();
		System.out.println("res" + resultat);
		String studieresultatUID = properties.getProperty("rest.resultat.studieresultat.uid");
		String utbildningUID = properties.getProperty("rest.utbildningsinformation.utbildning.uid");

		Resultat resu = ri.skapaResultatForStudentPakurs(resultat, studieresultatUID, utbildningUID);
		System.out.println("resultat: " + resu);
		assertNotNull(resu);   
	}

	//POST
	//https://www.mit.ladok.se/gui/proxy/resultat/studieresultat/skapa
	//@Test
	public void testSkaparesultatForStudentPaUtbildningsinstans() throws Exception {
		SkapaFlera resultat = new SkapaFlera();
		SkapaResultat res = new SkapaResultat();
		//String studieresultatUID = properties.getProperty("rest.resultat.studieresultat.uid");
		res.setStudieresultatUID("cd603178-9e38-11e7-b5b7-5910b98d49a4");
		res.setUtbildningsinstansUID("ba30f4e5-5047-11e7-8a70-b0dd5b09425d");
		res.setBetygsgrad(Integer.valueOf(2403));//G
		res.setBetygsskalaID(Integer.valueOf(432));
		res.getNoteringar();
		resultat.getResultat().add(res);
		System.out.println("res" + resultat);

		ResultatLista resu = ri.skapaResultatForStudentPaUtbildningsinstans(resultat);
		assertNotNull(resu);
	}

	//PUT
	//resultat/studieresultat/resultat/{resultatUID}
	//@Test
	@Deprecated
	public void testUpdateraresultatForStudent() throws Exception {
		Resultat resultat = new Resultat();	
		resultat.setUid("057e5180-f8cc-11e6-a7ab-4ba5a8cf40ea");
		resultat.setBetygsgrad(Integer.valueOf(2406));
		resultat.setBetygsskalaID(Integer.valueOf(433));
		resultat.getNoteringar();
		System.out.println("res" + resultat);

		String resultatUID = properties.getProperty("rest.resultat.resultat.uid");
		Resultat resu = ri.updateraResultatForStudentPakurs(resultat, resultatUID);
		System.out.println("resultat: " + resu);
		assertNotNull(resu);
	}

	//PUT
	//resultat/studieresultat/uppdatera
	//@Test
	public void testUpdateraResultatForStudentPaUtbildningsinstans() throws Exception {
		UppdateraFlera resultat = new UppdateraFlera();
		UppdateraResultat res = new UppdateraResultat();
		res.setResultatUID("3254d578-af59-11e7-b27e-48ab1cac4efd");
		res .setBetygsgrad(Integer.valueOf(2402));//VG
		res.setBetygsskalaID(Integer.valueOf(432));
		res.setSenasteResultatandring(new Date());
		res.getNoteringar();
		System.out.println("res" + resultat);
		resultat.getResultat().add(res);
		String resultatUID = properties.getProperty("rest.resultat.resultat.uid");
		ResultatLista resu = ri.uppdateraResultatForStudentPaUtbildningsinstans(resultat);
		System.out.println("resultat: " + resu);
		assertNotNull(resu);
	}

	//PUT
	//resultat/studieresultat/klarmarkera
	//@Test
	public void testKlarmarkeraResultatForStudentPaUtbildningsinstans() throws Exception {
	    KlarmarkeraFlera klarmarkeraFlera = new KlarmarkeraFlera();
	    Klarmarkera klarmarkering = new Klarmarkera();
	    klarmarkering.setKlarmarkeringsdatum(new Date());
		klarmarkering.setResultatetsSenastSparad(new Date());
		klarmarkering.setKlarmarkeradAvUID("");
		klarmarkeraFlera.setKlarmarkering(klarmarkering);
	    klarmarkeraFlera.getResultatUIDer().add("5e3c7157-d6df-11e7-8965-8d9fc5414a76");
	    klarmarkeraFlera.getResultatUIDer().add("eb263447-d6df-11e7-8965-8d9fc5414a76");
		ResultatLista resu = ri.klarmarkeraResultatForStudentPakurs(klarmarkeraFlera);
		System.out.println("resultat: " + resu);
		assertNotNull(resu);

	}
	
	//@Test
	public void testHamtaAktivitetstillfalle() throws Exception {
		SokresultatAktivitetstillfalleResultat sokresultatAktivitetstillfalleResultat
		 = ri.sokAktivitetstillfallen(
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,	
				null,	
				false,
				false,
				"sv",
				1,
				25
		);
		
		assertFalse(sokresultatAktivitetstillfalleResultat.getResultat().isEmpty());
		
		String aktivitetestillfalleUid =
				sokresultatAktivitetstillfalleResultat.getResultat().get(0).getUid();
		
		Aktivitetstillfalle aktivitetstillfalle = 
				ri.hamtaAktivitetstillfalle(aktivitetestillfalleUid);
		
		assertNotNull(aktivitetstillfalle);	
	}
	
	//@Test
	public void testHamtaAktivitetstillfallestyp() throws Exception {
		Aktivitetstillfallestyper aktivitetstillfallestyper = 
				ri.listaAktivitetstillfallestyper();
		
		assertFalse(aktivitetstillfallestyper.getAktivitetstillfallestyp().isEmpty());
		
		int aktivitetstillfallestypId = 
				Integer.parseInt(
						aktivitetstillfallestyper.getAktivitetstillfallestyp().get(0).getID()
				);
				
		Aktivitetstillfallestyp aktivitetstillfallestyp =
				ri.hamtaAktivitetstillfallestyp(aktivitetstillfallestypId);
		
		assertNotNull(aktivitetstillfallestyp);
	}
	
	//@Test
	public void testSokAktivitetstillfallesmojligheter() throws Exception {
				
		SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter =
			ri.sokAktivitetstillfallesmojligheter(
					null, 
					true, //anmalda
					null, 
					null, 
					null, 
					false, 
					false, 
					null,
					1,
					20
			);
		
		assertFalse(sokAktivitetstillfallesmojligheter.getResultat().isEmpty());
	}
	
	//@Test
	public void testSkapaAktivitetstillfalle() throws Exception {
		Aktivitetstillfalle aktivitetstillfalle = new Aktivitetstillfalle();
		
		aktivitetstillfalle.setAktivitetstillfallestypID(
				Integer.parseInt(
						ri.listaAktivitetstillfallestyper().getAktivitetstillfallestyp().get(0).getID()
				)
		);
		
		aktivitetstillfalle.setAnmalan(false);
		aktivitetstillfalle.setAnonymt(true);
		aktivitetstillfalle.setBorttagen(false);
		aktivitetstillfalle.setInstalld(false);
		
		Date now = new Date();
		
		Benamningar benamningar = new Benamningar();
		
		se.ladok.schemas.Benamning benamning = new se.ladok.schemas.Benamning();
		benamning.setSprakkod("sv");
		benamning.setText(properties.getProperty("rest.resultat.aktivitetstillfalle.benaming.sv"));
		benamningar.getBenamning().add(benamning);
		
		benamning = new se.ladok.schemas.Benamning();
		benamning.setSprakkod("en");
		benamning.setText(properties.getProperty("rest.resultat.aktivitetstillfalle.benaming.en"));
		benamningar.getBenamning().add(benamning);
		
		aktivitetstillfalle.setBenamningar(benamningar);
		
		//aktivitetstillfalle.setAnmalningsperiod(value);
		
		Datumperiod datumperiod = new Datumperiod();
		
		datumperiod.setStartdatum(new Date(2017,9,1,12,0,0));
		datumperiod.setSlutdatum(new Date(2017,9,1,17,0,0));
		
		aktivitetstillfalle.setDatumperiod(datumperiod);
		
		//aktivitetstillfalle.setLarosateID(value);
		
		aktivitetstillfalle.setLank("http://www.uu.se");
		aktivitetstillfalle.setOrt("Uppsala");
		aktivitetstillfalle.setOvrigInformation("");
		aktivitetstillfalle.setPlats("Allianshallen");
		
		//aktivitetstillfalle.setSenastAndradAv(value);
		//aktivitetstillfalle.setSenastSparad(value);
		
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		
		gregorianCalendar.setTime(new Date(2017,8,1,12,0,0));
		XMLGregorianCalendar xmlGregorianDateStart = 
				DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		
		gregorianCalendar.setTime(new Date(2017,8,31,12,0,0));
		XMLGregorianCalendar xmlGregorianDateSlut = 
				DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

		aktivitetstillfalle.setStarttid(xmlGregorianDateStart);
		
		aktivitetstillfalle.setSluttid(xmlGregorianDateSlut);
	}
	
	//@Test
	public void testSokAktivitetstillfallen() throws Exception {		
		SokresultatAktivitetstillfalleResultat sokresultatAktivitetstillfalleResultat = 
				ri.sokAktivitetstillfallen(
					null,
					properties.getProperty("rest.resultat.aktivitetstillfalle.benaming.sv"),
					null,
					null,
					null,
					null,
					null,
					null,	
					null,	
					false,
					false,
					"sv",
					1,
					25
				);
		
		assertFalse(sokresultatAktivitetstillfalleResultat.getResultat().isEmpty());
	}
	
	//@Test
	public void testSokAllaStudielokaliseringar() throws Exception {
		Studielokaliseringar studielokaliseringar = 
				ri.sokAllaStudielokaliseringar();
		
		assertFalse(studielokaliseringar.getStudielokalisering().isEmpty());
	}

	//@Test
	public void testSokResultat() throws Exception {
		System.out.println("UTKAST");
		testSokResultat("01010101-2222-3333-0043-000000000949", new String[]{"01010101-2222-3333-0043-000000002094"}, "UTKAST");
	}	
	
	
	public void testSokResultat(String kursinstansUid, String[] kurstillfallen, String filtrering) throws Exception {
		SokresultatStudieresultatResultat s = ri.sokStudieResultat(kursinstansUid,   
				kurstillfallen,
				filtrering,
				"", 
				1, 
				45,
				"EFTERNAMN_ASC");
		System.out.println("totalt antal poster: " +  s.getTotaltAntalPoster());
		assertNotNull(s);
		List<Studieresultat> resultat = s.getResultat();
		System.out.println("längd på Resultatlistan: " + resultat.size());
		for (Studieresultat r : resultat) {
			Student student = r.getStudent();
			System.out.println("Student " + student.getFornamn()  + " " + student.getEfternamn());
			Studieresultat.ResultatPaUtbildningar resultatPaUtbildningar = r.getResultatPaUtbildningar();
			for (ResultatPaUtbildning resultatPaUtbildning : resultatPaUtbildningar.getResultatPaUtbildning()) {
				Resultat arbetsunderlag = resultatPaUtbildning.getArbetsunderlag();
				if (arbetsunderlag == null) {
					System.out.println("   Arbetsunderlag null");
				} else {
					System.out.println("   ProcessStatus: " + arbetsunderlag.getProcessStatus());
				}
			}
		}
	}
	
	//@Test
	public void testAttesteradeSokResultat() throws Exception {
		System.out.println("ATTESTERADE");
		testAttesteradeSokResultat("01010101-2222-3333-0043-000000000949", new String[]{"01010101-2222-3333-0043-000000002094"}, "ATTESTERADE");
	}	
	
	public void testAttesteradeSokResultat(String kursinstansUid, String[] kurstillfallen, String filtrering) throws Exception {
		SokresultatStudieresultatResultat s = ri.sokAttesteradeStudieResultat(kursinstansUid,    
				kurstillfallen,
				filtrering,
				"", 
				1, 
				45,
				"EFTERNAMN_ASC");
		System.out.println("totalt antal poster: " +  s.getTotaltAntalPoster());
		assertNotNull(s);
		List<Studieresultat> resultat = s.getResultat();
		System.out.println("längd på Resultatlistan: " + resultat.size());
		for (Studieresultat r : resultat) {
			Student student = r.getStudent();
			System.out.println("Student " + student.getFornamn()  + " " + student.getEfternamn());
			Studieresultat.ResultatPaUtbildningar resultatPaUtbildningar = r.getResultatPaUtbildningar();
			for (ResultatPaUtbildning resultatPaUtbildning : resultatPaUtbildningar.getResultatPaUtbildning()) {
				Resultat arbetsunderlag = resultatPaUtbildning.getArbetsunderlag();
				if (arbetsunderlag == null) {
					System.out.println("   Arbetsunderlag null");
				} else {
					System.out.println("   ProcessStatus: " + arbetsunderlag.getProcessStatus());
				}
			}
		}
	}
	
	//@Test
	public void testListaOrganisatoriskaDelar() throws Exception {
		Organisationslista organisationslista = ri.listaOrganisatoriskaDelar();
		
		assertFalse(organisationslista.getOrganisation().isEmpty());
	}
	
	//@Test
	public void testSokresultatKurstillfalle() throws Exception {
		SokresultatKurstillfalleResultat sokresultatKurstillfalleResultat =
				ri.sokresultatKurstillfalle(
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						false, 
						false, 
						20, 
						1, 
						null
				);
		
		assertFalse(sokresultatKurstillfalleResultat.getResultat().isEmpty());
	}
	
	//@Test
	public void testSokStudieresultatForResultatuppfoljningAvKurs() throws Exception {
		SokresultatResultatuppfoljning sokresultatResultatuppfoljning = 
				ri.sokStudieresultatForResultatuppfoljningAvKurs(
					null,
					null,
					null,
					TillstandEnum.AVKLARAD.name(),
					1,
					25,
					ResultatuppfoljningOrderByEnum.EFTERNAMN_ASC.name(),
					null,
					null
				);
		
		assertFalse(sokresultatResultatuppfoljning.getResultat().isEmpty());
	}
	
	//@Test
	public void testSokStudieresultatForRapporteringsunderlag() throws Exception {
		SokresultatStudieresultatResultat sokresultatStudieresultatResultat = 
			ri.sokStudieresultatForRapporteringsunderlag(
				null,
				null,
				null,
				"ANMALDA", //FIXME 
				null,
				1,
				20,
				StudieresultatOrderByEnum.EFTERNAMN_ASC.name()
			);
		
		assertFalse(sokresultatStudieresultatResultat.getResultat().isEmpty());
	}
	
	//@Test
	public void testUpdateraAktivitetstillfalle() throws Exception {
		SokresultatAktivitetstillfalleResultat sokresultatAktivitetstillfalleResultat = 
				ri.sokAktivitetstillfallen(
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,	
					null,	
					false,
					false,
					"sv",
					1,
					25
				);
		
		assertFalse(sokresultatAktivitetstillfalleResultat.getResultat().isEmpty());
		
		Aktivitetstillfalle aktivitetstillfalle = 
				sokresultatAktivitetstillfalleResultat.getResultat().get(0);

		assertNotNull(aktivitetstillfalle);
		
		aktivitetstillfalle.setAnmalan(false);
		
		Aktivitetstillfalle updateratAktivitetstillfalle = 
				ri.updateraAktivitetstillfalle(aktivitetstillfalle);
		
		assertNotNull(updateratAktivitetstillfalle);
		
		assertFalse(aktivitetstillfalle.isAnmalan());
		assertFalse(updateratAktivitetstillfalle.isAnmalan());
		
	}
	
	//@Test
	public void testTaBortAktivitetstillfalle() throws Exception {
		SokresultatAktivitetstillfalleResultat sokresultatAktivitetstillfalleResultat = 
				ri.sokAktivitetstillfallen(
						null, 
						properties.getProperty("rest.resultat.aktivitetstillfalle.benaming.sv"), 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						false, 
						false, 
						null, 
						1, 
						20
				);
		
		assertFalse(sokresultatAktivitetstillfalleResultat.getResultat().isEmpty());
		
		Aktivitetstillfalle aktivitetstillfalle = 
				sokresultatAktivitetstillfalleResultat.getResultat().get(0);
		
		ri.taBortAktivitetstillfalle(aktivitetstillfalle.getUid());
		
		aktivitetstillfalle = ri.hamtaAktivitetstillfalle(aktivitetstillfalle.getUid());
		
		log.info(aktivitetstillfalle);
		
		assertNull(aktivitetstillfalle);
	}
	
	//@Test
	public void testStallInAktivitetstillfalle() throws Exception {
		SokresultatAktivitetstillfalleResultat sokresultatAktivitetstillfalleResultat = 
				ri.sokAktivitetstillfallen(
						null, 
						properties.getProperty("rest.resultat.aktivitetstillfalle.benaming.sv"), 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						false, 
						false, 
						null, 
						1, 
						20
				);
		
		assertFalse(sokresultatAktivitetstillfalleResultat.getResultat().isEmpty());
		
		Aktivitetstillfalle aktivitetstillfalle = 
				sokresultatAktivitetstillfalleResultat.getResultat().get(0);
		
		Aktivitetstillfalle installedAktivitetstillfalle = 
				ri.stallInAktivitetstillfalle(aktivitetstillfalle);
		
		
		assertTrue(installedAktivitetstillfalle.isInstalld());		
	}
	
	//@Test
	public void testAktiveraAktivitetstillfalle() throws Exception {
		SokresultatAktivitetstillfalleResultat sokresultatAktivitetstillfalleResultat = 
				ri.sokAktivitetstillfallen(
						null, 
						properties.getProperty("rest.resultat.aktivitetstillfalle.benaming.sv"), 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						false, 
						false, 
						null, 
						1, 
						20
				);
		
		assertFalse(sokresultatAktivitetstillfalleResultat.getResultat().isEmpty());
		
		Aktivitetstillfalle aktivitetstillfalle = 
				sokresultatAktivitetstillfalleResultat.getResultat().get(0);
		
		Aktivitetstillfalle installtAktivitetstillfalle = 
				ri.stallInAktivitetstillfalle(aktivitetstillfalle);
		
		assertTrue(installtAktivitetstillfalle.isInstalld() == true);
		
		Aktivitetstillfalle aktiveratAktivitetstillfalle = 
				ri.aktiveraAktivitetstillfalle(aktivitetstillfalle);
		
		assertTrue(aktiveratAktivitetstillfalle.isInstalld() == false);
	}
	
	//@Test
	public void testAvanmalStudentFranAktivitetstillfalle() throws Exception {
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	//@Test
	public void testAnmalStudentTillAktivitetstillfalle() throws Exception {
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	//@Test
	public void testAvanmalOchTaBortAktivitetstillfallesmojlighet() throws Exception {
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	//@Test
	public void testSkapaAktivitetstillfallesmojlighet() throws Exception {
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	//@Test
	public void testSokStudentidentiteter() throws Exception {
		throw new UnsupportedOperationException("Not implemented.");
	}
}
