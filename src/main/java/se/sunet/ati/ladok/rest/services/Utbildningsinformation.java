package se.sunet.ati.ladok.rest.services;

import java.util.List;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.Huvudomraden;
import se.ladok.schemas.utbildningsinformation.LokalUtbildningsmall;
import se.ladok.schemas.utbildningsinformation.NivaInomStudieordning;
import se.ladok.schemas.utbildningsinformation.NivaerInomStudieordning;
import se.ladok.schemas.utbildningsinformation.Period;
import se.ladok.schemas.utbildningsinformation.SokresultatUtbildningstillfalleProjektion;
import se.ladok.schemas.utbildningsinformation.UtbildningProjektion;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Utbildningstyp;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;

public interface Utbildningsinformation extends LadokServiceProperties {

	LokalUtbildningsmall hamtaLokalUtbildningsmall(int utbildningstypID, String datum);

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

	Utbildningsinstans hamtaUtbildningsinstansViaUID(String utbildningsinstansUID);

	Utbildningstillfalle hamtaUtbildningstillfalleViaUID(String utbildningstillfalleUID);

	Utbildningstyp hamtaUtbildningstypViaKod(String utbildningstypKod);

	Utbildningsinstans skapaUtbildningsinstans(Utbildningsinstans utbildningsinstans);

	/**
	 * Skapa en ny version av en utbildningsinstans.
	 * @param utbildningsinstansUID uid för utbildningsinstansen som en ny version ska skapas utifrån.
	 * @param utbildningsinstans versonsinformation
	 * @return Den nya versionen
	 */
	Utbildningsinstans skapaUtbildningsinstansNyVersion(String utbildningsinstansUID, Versionsinformation utbildningsinstans);

	Utbildningsinstans skapaUtbildningsinstansUnderliggande(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID);

	Utbildningstillfalle skapaUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle);

	Organisationslista sokAllaOrganisationer();

	Utbildningsinstans uppdateraUtbildningsinstans(Utbildningsinstans utbildningsinstans);

	Utbildningsinstans utbildningsinstansTillStatusPaborjad(String utbildningsinstansUID);

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

}
