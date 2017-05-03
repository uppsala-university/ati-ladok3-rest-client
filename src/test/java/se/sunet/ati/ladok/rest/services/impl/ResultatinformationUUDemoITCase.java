package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.resultat.Aktivitetstillfalle;
import se.ladok.schemas.resultat.Aktivitetstillfallestyp;
import se.ladok.schemas.Student;
import se.ladok.schemas.resultat.Benamning;
import se.ladok.schemas.resultat.Resultat;
import se.ladok.schemas.resultat.ResultatLista;
import se.ladok.schemas.resultat.ResultatPaUtbildning;
import se.ladok.schemas.resultat.SkapaResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfalleResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfallesmojlighetResultat;
import se.ladok.schemas.resultat.Studielokaliseringar;
import se.ladok.schemas.resultat.Studieresultat;
import se.ladok.schemas.resultat.Utbildningsinstans;
import se.ladok.schemas.resultat.SokresultatStudieresultatResultat;
import se.sunet.ati.ladok.rest.services.Resultatinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class ResultatinformationUUDemoITCase {
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
	@Test
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

	//PUT
	//resultat/studieresultat/resultat/{resultatUID}
	@Test
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
	
	@Test
	public void testHamtaAktivitetstillfalle() throws Exception {
		String aktivitetestillfalleUid = 
				properties.getProperty("rest.resultat.aktivitetstillfalle.uid");
		
		Aktivitetstillfalle aktivitetstillfalle = 
				ri.hamtaAktivitetstillfalle(aktivitetestillfalleUid);
		
		assertNotNull(aktivitetstillfalle);			
	}
	
//	@Test
	public void testHamtaAktivitetstillfallestyp() throws Exception {
		int aktivitetstillfallestypId =
				Integer.parseInt(properties.getProperty("rest.resultat.aktivitetstillfallestyp.id"));
		
		Aktivitetstillfallestyp aktivitetstillfallestyp =
				ri.hamtaAktivitetstillfallestyp(aktivitetstillfallestypId);
		
		assertNotNull(aktivitetstillfallestyp);
	}
	
//	@Test
	public void testSokAktivitetstillfallesmojligheter() throws Exception {
		String aktivitetestillfalleUid = 
				properties.getProperty("rest.resultat.resultat.uid");
		
		SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter =
			ri.sokAktivitetstillfallesmojligheter(
					aktivitetestillfalleUid, 
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
		
		assertNotNull(sokAktivitetstillfallesmojligheter);
	}
	
//	@Test
	public void testSokAktivitetstillfallen() throws Exception {
		String aktivitetstillfallestypId =
				properties.getProperty("rest.resultat.aktivitetstillfallestyp.id");
		
		SokresultatAktivitetstillfalleResultat sokresultatAktivitetstillfalleResultat
		 = ri.sokAktivitetstillfallen(
				aktivitetstillfallestypId,
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
				null,
				1,
				25
		);
		
		assertNotNull(sokresultatAktivitetstillfalleResultat);
	}
	
//	@Test
	public void testSokAllaStudielokaliseringar() throws Exception {
		Studielokaliseringar studielokaliseringar = 
				ri.sokAllaStudielokaliseringar();
		
		assertNotNull(studielokaliseringar);
	}


	@Test
	public void testSokResultat() throws Exception {
		System.out.println("UTKAST");
		testSokResultat("01010101-2222-3333-0043-000000000949", new String[]{"01010101-2222-3333-0043-000000002094"}, "UTKAST");
	}	
	
	
	public void testSokResultat(String kursinstansUid, String[] kurstillfallen, String filtrering) throws Exception {
		System.out.println("............ 0");
		//String[] kurstillfallen2 = new String[]{"01010101-2222-3333-0043-000000002094"};
		SokresultatStudieresultatResultat s = ri.sokStudieResultat(kursinstansUid,   //"01010101-2222-3333-0043-000000000949", 
				kurstillfallen,
				//"KLARMARKERADE,OBEHANDLADE_UTKAST_KLARMARKERADE",
				//"KLARMARKERADE",
				//"OBEHANDLADE_UTKAST",
				//"UTKAST",
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
			//System.out.println("aktuell kursinstans: " + r.getAktuellKursinstans() + " anonymkod: " + r.getAnonymiseringskod());
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
}
