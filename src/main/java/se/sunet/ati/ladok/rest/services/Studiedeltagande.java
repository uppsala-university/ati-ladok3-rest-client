package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Student;
import se.ladok.schemas.studiedeltagande.Atgard;
import se.ladok.schemas.studiedeltagande.BehorighetsvillkorLista;
import se.ladok.schemas.studiedeltagande.SokresultatDeltagare;
import se.ladok.schemas.studiedeltagande.TillfallesdeltagandeLista;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurstillfalleQuery;

public interface Studiedeltagande extends LadokServiceProperties {
	public TillfallesdeltagandeLista hamtaPaborjadeKurser(String studentUid);

	public Student hamtaStudentViaPersonnummer(String personnummer);

	@Deprecated
	public SokresultatDeltagare sokDeltagareKurstillfalle(String kurstillfalleUID);

	public SokresultatDeltagare sokDeltagareKurstillfalle(SokDeltagareKurstillfalleQuery sokDeltagareKurstillfalleQuery);
	
	public TillfallesdeltagandeLista hamtaKommandeKurstillfallesdeltaganden(String studentUid);
	
	public void registreraStudentPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

	public Hinderlista hamtaHinderMotStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

	public Atgard hamtaAtgardStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

	public BehorighetsvillkorLista hamtaBehorighetsVillkorForTillfallesantagning(String kurstillfallesantagningUid);
}
