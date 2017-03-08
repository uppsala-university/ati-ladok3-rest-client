package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.resultat.Benamning;
import se.ladok.schemas.resultat.Resultat;
import se.ladok.schemas.resultat.ResultatLista;
import se.ladok.schemas.resultat.ResultatPaUtbildning;
import se.ladok.schemas.resultat.SkapaResultat;
import se.ladok.schemas.resultat.Studieresultat;
import se.ladok.schemas.resultat.Utbildningsinstans;
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
	public void hmtaStudieResultatForStudentPaUtbildningstillfalle() throws Exception {
		String kurstillfalleUID = properties.getProperty(
				"rest.utbildningsinformation.kurstillfalle2.uid");
		String studentUID = properties.getProperty(
				"rest.studiedeltagande.student.uid");
		Studieresultat resultat = ri.hmtaStudieResultatForStudentPaUtbildningstillfalle(studentUID, kurstillfalleUID);

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
	public void hmtaStudentResultatForStudent() throws Exception {
		ResultatLista resultatLista = ri.hmtaStudieResultatForStudent(properties.getProperty("rest.studiedeltagande.student.uid"));		
		assertNotNull(resultatLista);

		for(Resultat resultat : resultatLista.getResultat()) {
			System.out.println("Kurstifalleskod: " + resultat.getKurstillfalleUID());
		}
	}

	//resultat/utbildningsinstans/{utbildningsinstansUID}/moduler
	@Test
	public void hmtaModulerForKursistans() throws Exception {
		Utbildningsinstans utbildningsInstans = ri.hmtaModulerForUtbildningsinstans(properties.getProperty("rest.utbildningsinformation.kurstillfalle.uid"));
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
	public void SkaparesultatForStudent() throws Exception {
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
	public void UpdateraresultatForStudent() throws Exception {
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
}