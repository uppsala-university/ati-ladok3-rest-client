package se.sunet.ati.ladok.rest.services;

import java.util.List;

import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.*;

public interface Utbildningsinformation extends LadokServiceProperties {

	List<Attributdefinition> hamtaAttributdefinitionerViaUtbildningstyp(int utbildningstypID);

	LokalUtbildningsmall hamtaLokalUtbildningsmall(int utbildningstypID, String datum);

	LokalUtbildningsmall hamtaLokalUtbildningsmall(String utbildningsmallUID);

	/**
	 * Hämtar en nivå inom studieordning.
	 *
	 * @param kod Kod på den nivå som ska hämtas
	 * @return Nivå inom studieordning
	 */
	NivaInomStudieordning hamtaNivaInomStudieordning(String kod);

	/**
	 * Lista nivåer inom studieordning.
	 *
	 * @return Nivåer inom studieordning
	 */
	NivaerInomStudieordning hamtaNivaerInomStudieordning();

	List<Period> hamtaPerioder();

	Period hamtaPeriodViaKod(String periodKod);

	List<UtbildningProjektion> hamtaUtbildningsinstansViaKod(String utbildningsinstansKod, int studieordningID, int utbildningstypID);

	List<UtbildningProjektion> hamtaUtbildningsinstansViaKod(String utbildningsinstansKod);

	Utbildningsinstans hamtaUtbildningsinstansViaUID(String utbildningsinstansUID);

	Utbildningstillfalle hamtaUtbildningstillfalleViaUID(String utbildningstillfalleUID);

	Utbildningstyp hamtaUtbildningstypViaKod(String utbildningstypKod);

	Utbildningsinstans skapaUtbildningsinstans(Utbildningsinstans utbildningsinstans);

	void avvecklaUtbildning(String utbildningUID, Beslut beslut);

	/**
	 * Skapa en ny version av en utbildningsinstans.
	 * @param utbildningsinstansUID uid för utbildningsinstansen som en ny version ska skapas utifrån.
	 * @param utbildningsinstans versonsinformation
	 * @return Den nya versionen
	 */
	Utbildningsinstans skapaUtbildningsinstansNyVersion(String utbildningsinstansUID, Versionsinformation utbildningsinstans);

	Utbildningsinstans skapaUtbildningsinstansUnderliggande(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID);
	
	Utbildningsinstans uppdateraUtbildningsinstansUnderliggande(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID);
	
	Utbildningsinformationsstruktur uppdateraStruktur(Utbildningsinformationsstruktur utbildningsinformationsstruktur, String strukturUID);
	
	Utbildningsinformationsstruktur skapaStruktur(Utbildningsinformationsstruktur utbildningsinformationsstruktur);

	Utbildningstillfalle skapaUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle);

	Utbildningstillfalle uppdateraUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle);

	Utbildningstillfalle bytUtbildningsinstansForUtbildningstillfalle(String utbildningstillfalleUID, String utbildningsinstansUID);

	Utbildningstillfalle utbildningstillfalleTillStatusKomplett(String utbildningstillfalleUID, Beslut beslut);

	Utbildningstillfalle utannonseraUtbildningstillfalle(String utbildningstillfalleUID);

	Organisationslista sokAllaOrganisationer();

	Organisationslista sokOrganisationer(String kod);

	Utbildningsinstans uppdateraUtbildningsinstans(Utbildningsinstans utbildningsinstans);

	Utbildningsinstans utbildningsinstansTillStatusPaborjad(String utbildningsinstansUID);

	Utbildningsinstans utbildningsinstansTillStatusKomplett(String utbildningsinstansUID, Beslut beslut);

	SokresultatUtbildningstillfalleProjektion sokUtbildningstillfallen(String utbildningstypID,
																	   String utbildningstillfallestypID,
																	   String studieordningID,
																	   String utbildningstillfalleskod,
																	   String utbildningskod,
																	   String benamning,
																	   String organisationUID,
																	   String status,
																	   String studieperiod,
																	   int page,
																	   int limit,
																	   boolean skipCount,
																	   boolean onlyCount,
																	   String sprakkod);

	/**
	 * Hämtar samtliga huvudområden
	 * @return
	 */
	Huvudomraden hamtaHuvudamraden();

	Utbildningsinformationsstruktur hamtaStruktur(String utbildningsinformationsstrukturUID);

	Utbildningsinstans skapaUtbildningsinstansMedunderliggandeutbildningar(UtbildningMedUnderliggandeUtbildningar utbildningMedUnderliggandeUtbildningar);

	Utbildningsinstansbox hamtaUtbildningsinstansbox(int utbildningstypID, String utbildningsinstansUID);

}
