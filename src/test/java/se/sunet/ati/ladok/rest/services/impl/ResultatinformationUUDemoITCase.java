package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import se.ladok.schemas.Benamningar;
import se.ladok.schemas.Datumperiod;
import se.ladok.schemas.Hinder;
import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.Student;
import se.ladok.schemas.dap.RelationLink;
import se.ladok.schemas.resultat.Aktivitetstillfalle;
import se.ladok.schemas.resultat.Aktivitetstillfallestyp;
import se.ladok.schemas.resultat.Aktivitetstillfallestyper;
import se.ladok.schemas.resultat.Benamning;
import se.ladok.schemas.resultat.Klarmarkera;
import se.ladok.schemas.resultat.KlarmarkeraFlera;
import se.ladok.schemas.resultat.Resultat;
import se.ladok.schemas.resultat.ResultatLista;
import se.ladok.schemas.resultat.ResultatPaUtbildning;
import se.ladok.schemas.resultat.ResultatuppfoljningOrderByEnum;
import se.ladok.schemas.resultat.SkapaFlera;
import se.ladok.schemas.resultat.SkapaResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfalleResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfallesmojlighetResultat;
import se.ladok.schemas.resultat.SokresultatKurstillfalleResultat;
import se.ladok.schemas.resultat.SokresultatResultatuppfoljning;
import se.ladok.schemas.resultat.SokresultatStudieresultatResultat;
import se.ladok.schemas.resultat.Studielokaliseringar;
import se.ladok.schemas.resultat.Studieresultat;
import se.ladok.schemas.resultat.StudieresultatForRapporteringSokVarden;
import se.ladok.schemas.resultat.StudieresultatOrderByEnum;
import se.ladok.schemas.resultat.StudieresultatTillstandVidRapporteringEnum;
import se.ladok.schemas.resultat.TillstandEnum;
import se.ladok.schemas.resultat.UppdateraFlera;
import se.ladok.schemas.resultat.UppdateraResultat;
import se.ladok.schemas.resultat.Utbildningsinstans;
import se.sunet.ati.ladok.rest.services.Resultatinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ResultatinformationUUDemoITCase {
	private static Log log = LogFactory.getLog(ResultatinformationUUDemoITCase.class);
	private static Properties properties = null;
	static final String TEST_DATA_FILE = "restclient.testdata.uudemo.properties"; 
	private static Resultatinformation ri;
	static final String HINDER_FOR_RAPPORTERING = "http://relations.ladok.se/resultat/studieresultat/resultat/skapa/hinder";

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
		List<String> kurstillfallen = new ArrayList<String>();
		//kurstillfallen.add("01010101-2222-3333-0043-000000002094");
		//testSokResultat("01010101-2222-3333-0043-000000000949", kurstillfallen, "UTKAST");
		kurstillfallen.add("bce52909-d426-11e7-a506-1c40749b409d");
//		testSokResultat("3ee026a6-d432-11e7-a506-1c40749b409d", kurstillfallen, "OBEHANDLADE_UTKAST_KLARMARKERADE_ATTESTERADE");
		testSokResultat("9d39f49e-d42c-11e7-a506-1c40749b409d", kurstillfallen, "OBEHANDLADE_UTKAST_KLARMARKERADE_ATTESTERADE");
	}

	public void testSokResultat(String kursinstansUid, List<String> kurstillfallen, String filtrering) throws Exception {
		StudieresultatForRapporteringSokVarden sokVarden = new StudieresultatForRapporteringSokVarden();
		sokVarden.getKurstillfallenUID().addAll(kurstillfallen);
		sokVarden.getFiltrering().add(StudieresultatTillstandVidRapporteringEnum.fromValue(filtrering));
		sokVarden.setGruppUID("");
		sokVarden.setPage(1);
		sokVarden.setLimit(45);
		sokVarden.getOrderBy().add(StudieresultatOrderByEnum.fromValue("EFTERNAMN_ASC"));

		SokresultatStudieresultatResultat s = ri.sokStudieResultat(sokVarden, kursinstansUid);
		System.out.println("totalt antal poster: " +  s.getTotaltAntalPoster());
		assertNotNull(s);
		List<Studieresultat> resultat = s.getResultat();
		System.out.println("längd på Resultatlistan: " + resultat.size());
		for (Studieresultat r : resultat) {
			Student student = r.getStudent();
			System.out.println("Student " + student.getFornamn()  + " " + student.getEfternamn());
			Studieresultat.ResultatPaUtbildningar resultatPaUtbildningar = r.getResultatPaUtbildningar();

			for (ResultatPaUtbildning resultatPaUtbildning : resultatPaUtbildningar.getResultatPaUtbildning()) {
				// Necessary to compare against moduleUID because the service sometimes gives results that
				// belong to other modules.
				if (resultatPaUtbildning.getSenastAttesteradeResultat() != null &&
						resultatPaUtbildning.getSenastAttesteradeResultat().getUtbildningsinstansUID() != null &&
						kursinstansUid.equals(resultatPaUtbildning.getSenastAttesteradeResultat().getUtbildningsinstansUID())) {
					Resultat senastAttesteradeResultat = resultatPaUtbildning.getSenastAttesteradeResultat();
					Integer betygsgrad = senastAttesteradeResultat != null ? senastAttesteradeResultat.getBetygsgrad() : null;
					System.out.println("   ATTESTERAD " + senastAttesteradeResultat.getProcessStatus());
					break;
				}
				Resultat arbetsunderlag = resultatPaUtbildning.getArbetsunderlag();
				if (arbetsunderlag == null) {
					System.out.println("Arbetsunderlag null");
				}
				if (arbetsunderlag != null && arbetsunderlag.getBetygsgrad() != null && arbetsunderlag.getUtbildningsinstansUID() != null &&
						kursinstansUid.equals(arbetsunderlag.getUtbildningsinstansUID())) {
					System.out.println("   " + arbetsunderlag.getProcessStatus());
					//System.out.println(arbetsunderlag);
				}
			}
			if (r.getLink() != null) {
				for (RelationLink link : r.getLink()) {
					if (HINDER_FOR_RAPPORTERING.equals(link.getRel())) {
						System.out.println("Hinder för rapportering!");
						System.out.println(link.getUri());
						System.out.println("");
					}
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


	// Note: It says "utbildningUID" in the restdoc but this is wrong, it should be "utbildningsinstansUID"
	//resultat/studieresultat/{studieresultatUID}/utbildning/{utbildningsinstansUID}/resultat/hinder
	@Deprecated
	//@Test
	public void testHamtaHinder() throws Exception {
		// Mario  9429b55a-e646-11e7-922a-3b91b6b54620
		// Wilma  0466cc55-f603-11e7-b1a4-a7800d66443f
		Hinderlista hinderlista = ri.hamtaHinder("9429b55a-e646-11e7-922a-3b91b6b54620", "9d39f49e-d42c-11e7-a506-1c40749b409d");
		assertNotNull(hinderlista);

		List<Hinder> hinders = hinderlista.getHinder();
		for (Hinder hinder : hinders) {
			System.out.println("hinder: " + hinder.getI18NNyckel() + " . " + hinder.toString());
			System.out.println("stoppande: " + hinder.isStoppande());
		}
	}

	//@Test
	public void testHamtaHinderSkapa() throws Exception {
		Hinderlista hinderlista = ri.hamtaHinderSkapa("0466cc55-f603-11e7-b1a4-a7800d66443f", "3ee026a6-d432-11e7-a506-1c40749b409d");
		assertNotNull(hinderlista);

		List<Hinder> hinders = hinderlista.getHinder();
		for (Hinder hinder : hinders) {
			System.out.println("hinder: " + hinder.getI18NNyckel() + " . " + hinder.toString());
			System.out.println("stoppande: " + hinder.isStoppande());
		}
	}

	//@Test
	public void testHamtaHinderKlarmarkera() throws Exception {
		//Hinderlista hinderlista = ri.hamtaHinderKlarmarkera("bccc0f0b-d43e-11e7-8965-8d9fc5414a76", "1551808d-d852-11e7-8965-8d9fc5414a76");
		Hinderlista hinderlista = ri.hamtaHinderKlarmarkera("bccc0f0b-d43e-11e7-8965-8d9fc5414a76", "eb263447-d6df-11e7-8965-8d9fc5414a76");
		assertNotNull(hinderlista);

		List<Hinder> hinders = hinderlista.getHinder();
		for (Hinder hinder : hinders) {
			System.out.println("hinder: " + hinder.getI18NNyckel() + " . " + hinder.toString());
			System.out.println("stoppande: " + hinder.isStoppande());
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
