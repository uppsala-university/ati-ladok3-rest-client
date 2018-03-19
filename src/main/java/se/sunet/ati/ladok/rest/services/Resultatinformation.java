package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Identiteter;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.resultat.*;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokResultatResultatUppfoljningQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokAktivitetstillfallesmojlighetQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokAktivitetstillfalleQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokResultatKurstillfallesdeltagareQuery;

public interface Resultatinformation {

	public Studieresultat hamtaStudieResultatForStudentPaUtbildningstillfalle(String studentUid, 
			String kurstillfalleUID);

	public ResultatLista hamtaStudieResultatForStudent(String studentUID);

	public Utbildningsinstans hamtaModulerForUtbildningsinstans(String utbildningsinstanceUID);

	public Resultat skapaResultatForStudentPakurs(SkapaResultat resultat, String studieresultatUID, String utbildningUID);

	public Resultat updateraResultatForStudentPakurs(Resultat resultat, String resultatUID);
	
	public ResultatLista skapaResultatForStudentPaUtbildningsinstans(SkapaFlera resultat);
	
	public ResultatLista uppdateraResultatForStudentPaUtbildningsinstans(UppdateraFlera resultat);

	public Resultat klarmarkeraResultatForStudentPakurs(Klarmarkera klarmarkera, String resultatUID);

	public ResultatLista klarmarkeraResultatForStudentPakurs(KlarmarkeraFlera resultat);
	
	public Aktivitetstillfalle hamtaAktivitetstillfalle(String aktivitesttillfalleUID);
	
	public void taBortAktivitetstillfalle(String aktivitesttillfalleUID);
	
	public Aktivitetstillfalle skapaAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle);
	
	public Aktivitetstillfalle updateraAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle);
	
	public Aktivitetstillfalle stallInAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle);
	
	public Aktivitetstillfalle aktiveraAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle);
	
	public Aktivitetstillfallestyp hamtaAktivitetstillfallestyp(int aktivitesttillfalletypUID);

	public Aktivitetstillfallestyper listaAktivitetstillfallestyper();

	public SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(SokAktivitetstillfalleQuery sokAktivitetstillfalleQuery);

	public SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(
			String aktivitetstillfallestypID,
			String benamning, 
			String[] kurstillfalleUID, 
			String[] kurstillfalleskod, 
			String[] aktivitetUID, 
			String[] kurskod,
			String[] organisation, 
			String datumperiod, 
			String orderby, 
			boolean skipCount, 
			boolean onlyCount,
			String sprakkod, 
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

	public SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter(SokAktivitetstillfallesmojlighetQuery sokAktivitetstillfallesmojlighetQuery);

	public Studielokaliseringar sokAllaStudielokaliseringar();

	SokresultatStudieresultatResultat sokStudieResultat(StudieresultatForRapporteringSokVarden studieresultatForRapporteringSokVarden, String utbildningsinstansUID);

	/**
	 * @deprecated
	 */
	@Deprecated
	public SokresultatStudieresultatResultat sokStudieResultat(String utbildningsinstansUID,
			String[] kurstillfalleUIDn,
			String filtrering,
			String grupp,
			int page,
			int limit,
			String orderby);

	public SokresultatStudieresultatResultat sokAttesteradeStudieResultat(String utbildningsinstansUID,
			String[] kurstillfalleUIDn,
			String filtrering,
			String grupp,
			int page,
			int limit,
			String orderby);
	
	public Organisationslista listaOrganisatoriskaDelar();
	
	public SokresultatKurstillfalleResultat sokresultatKurstillfalle(
			String	benamning,
			String	kurskod,
			String	tillfalleskod,
			String[] organisationer,
			String	datumperiod,
			String	utbildningstypID,
			String	utbildningsgrundtypID,
			boolean	skipCount,
			boolean	onlyCount,
			int limit,
			int page,
			String	sprakkod); 

	public Perioder listaPerioder();
	
	public SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(
			String kursinstansUID,
			String[] kurstillfallen,
			String grupp,
			String tillstand,
			int page,
			int limit,
			String orderby,
			String orderBetygsgradAvserUtbildningUID,
			String orderExaminationsdatumAvserUtbildningUID);

	public SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(
			SokResultatResultatUppfoljningQuery sokResultatResultatUppfoljningQuery);

	public SokresultatStudieresultatResultat sokStudieresultatForRapporteringsunderlag(
		String aktivitetstillfalleUID,
		String[] kurstillfallen,
		String filtrering,
		String filtreringAnmalda,
		String[] student,
		int page,
		int limit,
		String orderby);

	public Aktivitetstillfallesmojlighet hamtaAktivitetstillfallesmojlighet(String aktivitetstillfallesmojlighetUID);

	public Aktivitetstillfallesmojlighet avanmalStudentFranAktivitetstillfalle(String aktivitetstillfallesmojlighetUID, Anmalan anmalan);
	
	public Aktivitetstillfallesmojlighet anmalStudentTillAktivitetstillfalle(String aktivitetstillfallesmojlighetUID, Anmalan anmalan);
	
	public void avanmalOchTaBortAktivitetstillfallesmojlighet(String aktivitetstillfallesmojlighetUID);
	
	public Aktivitetstillfallesmojlighet skapaAktivitetstillfallesmojlighet(String studieresultatUID, Aktivitetstillfalle aktivitetstillfalle);
	
	public Identiteter sokStudentidentiteter(String aktivitetstillfalleUID);

	public Hinderlista hamtaHinder(String studieresultatUID, String utbildningUID);

	public SokresultatKurstillfallesdeltagare sokresultatKurstillfallesdeltagare(
			String aktivitetstillfalleUID,
			String[] kurstillfalleUID,
			String gruppUID,
			boolean skipCount,
			boolean onlyCount,
			String sprakkod,
			int limit,
			int page,
			String orderby
	);

	public SokresultatKurstillfallesdeltagare sokresultatKurstillfallesdeltagare(
			SokResultatKurstillfallesdeltagareQuery sokResultatKurstillfallesdeltagareQuery);

	public AktivitetstillfalleForStudentLista sokAktivitetstillfalleForStudent(
			String studentUID,
			String kurstillfalleUID,
			Boolean endastAnmalan
	);
}
