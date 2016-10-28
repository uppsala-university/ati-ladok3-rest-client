package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;

public interface Utbildningsinformation extends LadokServiceProperties {

	public Utbildningstillfalle hamtaUtbildningstillfalleViaUtbildningsUtbildningstillfalleUID(String utbildningstillfalleUID);

	public Utbildningsinstans hamtaUtbildningsinstansViaUtbildningsinstansUID(String utbildningsinstansUID);

	public Utbildningsinstans skapaUtbildningsinstans(Utbildningsinstans utbildningsinstans);

	public Utbildningsinstans skapaUnderliggandeUtbildningsinstans(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID);

	public Organisationslista sokAllaOrganisationer();

	public Utbildningstillfalle skapaUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle);
}
