package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Utbildningstyp;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;

public interface Utbildningsinformation extends LadokServiceProperties {

	Utbildningstyp hamtaUtbildningsttypID(String utbildningstypKod);

	Utbildningstillfalle hamtaUtbildningstillfalleViaUtbildningsUtbildningstillfalleUID(String utbildningstillfalleUID);

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
}
