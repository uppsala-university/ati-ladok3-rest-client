package se.sunet.ati.ladok.rest.services;

import java.util.Collection;
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

	Utbildningstillfalle stallInTillfalle(String utbildningstillfalleUID, Beslut beslut);

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

	/**
	 * Overloaded method that reflects that some of the parameters can in fact be multi-value/list of values.
	 * These are represented by taking in a Collection instead of a single String argument.
	 */
	SokresultatUtbildningstillfalleProjektion sokUtbildningstillfallen(String utbildningstypID,
																	   String utbildningstillfallestypID,
																	   String studieordningID,
																	   Collection<String> utbildningstillfalleskod,
																	   Collection<String> utbildningskod,
																	   String benamning,
																	   Collection<String> organisationUID,
																	   Collection<String> status,
																	   String studieperiod,
																	   int page,
																	   int limit,
																	   boolean skipCount,
																	   boolean onlyCount,
																	   String sprakkod);


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

	Utbildningstillfallesbox hamtaUtbildningstillfallesbox(int utbildningstypID, String utbildningstillfalleUID);

	Box publiceraBox(String strukturUID, String boxUID);

	SokresultatUtbildningsinstans sokUtbildningsinstans(String utbildningstypID, String studieordningID,
			String utbildningskod, String benamning, String status, int page, int limit, boolean skipCount, String sprakkod);

	List<Markningsnyckel> hamtaMarkningsnycklar();

	Markningsvarde hamtaMarkningsvarde(String kod);

	Markningsvarden hamtaMarkningsvarden(String kod);

	Utbildningsinformationsstruktur sokStruktur(String utbildningsbasUID);
}
