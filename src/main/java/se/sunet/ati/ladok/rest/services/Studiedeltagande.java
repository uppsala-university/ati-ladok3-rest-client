package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Student;
import se.ladok.schemas.studiedeltagande.Atgard;
import se.ladok.schemas.studiedeltagande.BehorighetsvillkorLista;
import se.ladok.schemas.studiedeltagande.SokresultatDeltagare;
import se.ladok.schemas.studiedeltagande.TillfallesdeltagandeLista;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurstillfalleQuery;

public interface Studiedeltagande extends LadokServiceProperties {
	TillfallesdeltagandeLista hamtaPaborjadeKurser(String studentUid);

	Student hamtaStudentViaPersonnummer(String personnummer);

	/**
	 * @deprecated använd sokDeltagareKurstillfalle(SokDeltagareKurstillfalleQuery q)
	 */
	@Deprecated
	SokresultatDeltagare sokDeltagareKurstillfalle(String kurstillfalleUID);

	SokresultatDeltagare sokDeltagareKurstillfalle(SokDeltagareKurstillfalleQuery sokDeltagareKurstillfalleQuery);

	TillfallesdeltagandeLista hamtaKommandeKurstillfallesdeltaganden(String studentUid);

	void registreraStudentPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

    void taBortStudentregistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

    Hinderlista hamtaHinderMotStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

	Atgard hamtaAtgardStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

	BehorighetsvillkorLista hamtaBehorighetsVillkorForTillfallesantagning(String kurstillfallesantagningUid);
}
