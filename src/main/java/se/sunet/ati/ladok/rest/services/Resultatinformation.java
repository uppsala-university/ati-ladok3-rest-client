package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.resultat.Aktivitetstillfalle;
import se.ladok.schemas.resultat.Aktivitetstillfallestyp;
import se.ladok.schemas.resultat.Klarmarkera;
import se.ladok.schemas.resultat.Resultat;
import se.ladok.schemas.resultat.ResultatLista;
import se.ladok.schemas.resultat.SkapaResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfalleResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfallesmojlighetResultat;
import se.ladok.schemas.resultat.Studielokaliseringar;
import se.ladok.schemas.resultat.Studieresultat;
import se.ladok.schemas.resultat.Utbildningsinstans;
import se.ladok.schemas.resultat.SokresultatStudieresultatResultat;

public interface Resultatinformation {

	public Studieresultat hamtaStudieResultatForStudentPaUtbildningstillfalle(String studentUid, 
			String kurstillfalleUID);

	public ResultatLista hamtaStudieResultatForStudent(String studentUID);

	public Utbildningsinstans hamtaModulerForUtbildningsinstans(String utbildningsinstanceUID);

	public Resultat skapaResultatForStudentPakurs(SkapaResultat resultat, String studieresultatUID, String utbildningUID);

	public Resultat updateraResultatForStudentPakurs(Resultat resultat, String resultatUID);
	
	public Resultat klarmarkeraResultatForStudentPakurs(Klarmarkera klarmarkera, String resultatUID);
	
	public Aktivitetstillfalle hamtaAktivitetstillfalle(String aktivitesttillfalleUID);
	
	public Aktivitetstillfallestyp hamtaAktivitetstillfallestyp(int aktivitesttillfalletypUID);
	
	public SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(
			String	aktivitetstillfallestypID,
			String	benamning,
			String	kurstillfalleUID,
			String	kurstillfalleskod,
			String	aktivitetUID,
			String	kurskod,
			String	organisation,
			String	datumperiod,	
			String	orderby,	
			boolean	skipCount,
			boolean	onlyCount,
			String	sprakkod,
			int page,
			int limit
	);
	
	public SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter(
			String	aktivitetstillfalleUID,
			boolean	anmalda,
			String	personnummer,
			String	fornamn,
			String	efternamn,
			boolean	skipCount,
			boolean	onlyCount,
			String	sprakkod,
			int page, 
			int limit
	);
	
	public Studielokaliseringar sokAllaStudielokaliseringar();

	public SokresultatStudieresultatResultat sokStudieResultat(String utbildningsinstansUID,
			String[] kurstillfalleUIDn,
			String filtrering,
			String grupp,
			int page,
			int limit,
			String orderby);

}
