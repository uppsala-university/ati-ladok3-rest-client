package se.sunet.ati.ladok.rest.services;

import java.util.List;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.NivaerInomStudieordning;
import se.ladok.schemas.utbildningsinformation.NivaInomStudieordning;
import se.ladok.schemas.utbildningsinformation.LokalUtbildningsmall;
import se.ladok.schemas.utbildningsinformation.UtbildningProjektion;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Utbildningstyp;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;

public interface Utbildningsinformation extends LadokServiceProperties {

	LokalUtbildningsmall hamtaLokalUtbildningsmall(int utbildningstypID, String datum);

	Utbildningstyp hamtaUtbildningsttypID(String utbildningstypKod);

	Utbildningstillfalle hamtaUtbildningstillfalleViaUtbildningstillfalleUID(String utbildningstillfalleUID);

	List<UtbildningProjektion> hamtaUtbildningsinstansViaKod(String utbildningsinstansKod, int studieordningID, int utbildningstypID);

	Utbildningsinstans hamtaUtbildningsinstansViaUtbildningsinstansUID(String utbildningsinstansUID);

	Utbildningsinstans skapaUtbildningsinstans(Utbildningsinstans utbildningsinstans);

	Utbildningsinstans uppdateraUtbildningsinstans(Utbildningsinstans utbildningsinstans);

	/**
	 * Skapa en ny version av en utbildningsinstans.
	 * @param utbildningsinstansUID uid för utbildningsinstansen som en ny version ska skapas utifrån.
	 * @param utbildningsinstans versonsinformation
	 * @return Den nya versionen
	 */
	Utbildningsinstans skapaNyVersionUtbildningsinstans(String utbildningsinstansUID, Versionsinformation utbildningsinstans);

	Utbildningsinstans skapaUnderliggandeUtbildningsinstans(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID);

	Organisationslista sokAllaOrganisationer();

	Utbildningstillfalle skapaUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle);

	Utbildningsinstans utbildningsinstansTillStatusPaborjad(String utbildningsinstansUID);

	/**
	 * Lista nivåer inom studieordning.
	 *
	 * @return Nivåer inom studieordning
	 */
	NivaerInomStudieordning listaNivaerInomStudieordning();

	/**
	 * Hämtar en nivå inom studieordning.
	 *
	 * @param kod Kod på den nivå som ska hämtas
	 * @return Nivå inom studieordning
	 */
	NivaInomStudieordning hamtaNivaInomStudieordning(String kod);
}
