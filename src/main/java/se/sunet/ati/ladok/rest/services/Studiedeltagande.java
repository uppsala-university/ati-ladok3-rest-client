package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Student;
import se.ladok.schemas.studiedeltagande.SokresultatDeltagare;
import se.ladok.schemas.studiedeltagande.TillfallesdeltagandeLista;

public interface Studiedeltagande extends LadokServiceProperties {
	public TillfallesdeltagandeLista hamtaPaborjadeKurser(String studentUid);

	public Student hamtaStudentViaPersonnummer(String personnummer);

	public SokresultatDeltagare sokDeltagareKurstillfalle(String kurstillfalleUID);
}
