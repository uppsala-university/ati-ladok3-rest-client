package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Identiteter;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.dap.ServiceIndex;
import se.ladok.schemas.resultat.*;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokAktivitetstillfalleQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokAktivitetstillfallesmojlighetQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokResultatKurstillfallesdeltagareQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokResultatResultatUppfoljningQuery;

public interface Resultatinformation {

	Studieresultat hamtaStudieResultatForStudentPaUtbildningstillfalle(String studentUid,
			String kurstillfalleUID);

	ResultatLista hamtaStudieResultatForStudent(String studentUID);

	Utbildningsinstans hamtaModulerForUtbildningsinstans(String utbildningsinstanceUID);

	Resultat skapaResultatForStudentPakurs(SkapaResultat resultat, String studieresultatUID, String utbildningUID);

	Resultat updateraResultatForStudentPakurs(Resultat resultat, String resultatUID);
	
	ResultatLista skapaResultatForStudentPaUtbildningsinstans(SkapaFlera resultat);
	
	ResultatLista uppdateraResultatForStudentPaUtbildningsinstans(UppdateraFlera resultat);

	Resultat klarmarkeraResultatForStudentPakurs(Klarmarkera klarmarkera, String resultatUID);

	ResultatLista klarmarkeraResultatForStudentPakurs(KlarmarkeraFlera resultat);
	
	Aktivitetstillfalle hamtaAktivitetstillfalle(String aktivitesttillfalleUID);
	
	void taBortAktivitetstillfalle(String aktivitesttillfalleUID);
	
	Aktivitetstillfalle skapaAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle);
	
	Aktivitetstillfalle updateraAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle);
	
	Aktivitetstillfalle stallInAktivitetstillfalle(String aktivitetstillfalleUid);
	
	Aktivitetstillfalle aktiveraAktivitetstillfalle(String aktivitetstillfalleUid);
	
	Aktivitetstillfallestyp hamtaAktivitetstillfallestyp(int aktivitesttillfalletypUID);

	Aktivitetstillfallestyper listaAktivitetstillfallestyper();

	SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(SokAktivitetstillfalleQuery sokAktivitetstillfalleQuery);

	SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(
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
	
	SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter(
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

	SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter(SokAktivitetstillfallesmojlighetQuery sokAktivitetstillfallesmojlighetQuery);

	Studielokaliseringar sokAllaStudielokaliseringar();

	SokresultatStudieresultatResultat sokStudieResultat(StudieresultatForRapporteringSokVarden studieresultatForRapporteringSokVarden, String utbildningsinstansUID);

	/**
	 * @deprecated
	 */
	@Deprecated
	SokresultatStudieresultatResultat sokStudieResultat(String utbildningsinstansUID,
			String[] kurstillfalleUIDn,
			String filtrering,
			String grupp,
			int page,
			int limit,
			String orderby);

	SokresultatStudieresultatResultat sokAttesteradeStudieResultat(String utbildningsinstansUID,
			String[] kurstillfalleUIDn,
			String filtrering,
			String grupp,
			int page,
			int limit,
			String orderby);
	
	Organisationslista listaOrganisatoriskaDelar();
	
	SokresultatKurstillfalleResultat sokresultatKurstillfalle(
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

	Perioder listaPerioder();
	
	SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(
			String kursinstansUID,
			String[] kurstillfallen,
			String grupp,
			String tillstand,
			int page,
			int limit,
			String orderby,
			String orderBetygsgradAvserUtbildningUID,
			String orderExaminationsdatumAvserUtbildningUID);

	SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(
			SokResultatResultatUppfoljningQuery sokResultatResultatUppfoljningQuery);

	SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(ResultatuppfoljningSokVarden resultatuppfoljningSokVarden);

	SokresultatStudieresultatResultat sokStudieresultatForRapporteringsunderlag(
		String aktivitetstillfalleUID,
		String[] kurstillfallen,
		String filtrering,
		String filtreringAnmalda,
		String[] student,
		int page,
		int limit,
		String orderby);

	Aktivitetstillfallesmojlighet hamtaAktivitetstillfallesmojlighet(String aktivitetstillfallesmojlighetUID);

	Aktivitetstillfallesmojlighet avanmalStudentFranAktivitetstillfalle(String aktivitetstillfallesmojlighetUID, Anmalan anmalan);
	
	Aktivitetstillfallesmojlighet anmalStudentTillAktivitetstillfalle(String aktivitetstillfallesmojlighetUID, Anmalan anmalan);
	
	void avanmalOchTaBortAktivitetstillfallesmojlighet(String aktivitetstillfallesmojlighetUID);
	
	Aktivitetstillfallesmojlighet skapaAktivitetstillfallesmojlighet(String studieresultatUID, Aktivitetstillfalle aktivitetstillfalle);
	
	Identiteter sokStudentidentiteter(String aktivitetstillfalleUID);

	SokresultatKurstillfallesdeltagare sokresultatKurstillfallesdeltagare(
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

	SokresultatKurstillfallesdeltagare sokresultatKurstillfallesdeltagare(
			SokResultatKurstillfallesdeltagareQuery sokResultatKurstillfallesdeltagareQuery);

	AktivitetstillfalleForStudentLista sokAktivitetstillfalleForStudent(
			String studentUID,
			String kurstillfalleUID,
			Boolean endastAnmalan
	);

	@Deprecated
	Hinderlista hamtaHinder(String studieresultatUID, String utbildningUID);

	Hinderlista hamtaHinderSkapa(String studieresultatUID, String utbildningUID);

	Hinderlista hamtaHinderKlarmarkera(String studieresultatUID, String resultatUID);

	ServiceIndex hamtaIndex();
}
