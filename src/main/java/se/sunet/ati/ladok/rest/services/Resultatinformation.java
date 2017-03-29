package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.resultat.Klarmarkera;
import se.ladok.schemas.resultat.Resultat;
import se.ladok.schemas.resultat.ResultatLista;
import se.ladok.schemas.resultat.SkapaResultat;
import se.ladok.schemas.resultat.Studieresultat;
import se.ladok.schemas.resultat.Utbildningsinstans;

public interface Resultatinformation {

	public Studieresultat hmtaStudieResultatForStudentPaUtbildningstillfalle(String studentUid, 
			String kurstillfalleUID);

	public ResultatLista hmtaStudieResultatForStudent(String studentUID);

	public Utbildningsinstans hmtaModulerForUtbildningsinstans(String utbildningsinstanceUID);

	public Resultat skapaResultatForStudentPakurs(SkapaResultat resultat, String studieresultatUID, String utbildningUID);

	public Resultat updateraResultatForStudentPakurs(Resultat resultat, String resultatUID);
	
	public Resultat klarmarkeraResultatForStudentPakurs(Klarmarkera klarmarkera, String resultatUID);
}
