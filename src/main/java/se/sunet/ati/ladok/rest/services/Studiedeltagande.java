package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Student;
import se.ladok.schemas.studiedeltagande.*;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurspaketeringstillfalleQuery;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurstillfalleQuery;

public interface Studiedeltagande extends LadokServiceProperties {
	TillfallesdeltagandeLista hamtaPaborjadeKurser(String studentUid);

	Student hamtaStudentViaUid(String studentUid);

	Student hamtaStudentViaPersonnummer(String personnummer);

	/**
	 * @deprecated använd sokDeltagareKurstillfalle(SokDeltagareKurstillfalleQuery q)
	 */
	@Deprecated
	SokresultatDeltagare sokDeltagareKurstillfalle(String kurstillfalleUID);

	SokresultatDeltagare sokDeltagareKurstillfalle(SokDeltagareKurstillfalleQuery query);

	SokresultatDeltagare sokDeltagareKurspaketeringstillfalle(SokDeltagareKurspaketeringstillfalleQuery query);

	TillfallesdeltagandeLista hamtaKommandeKurstillfallesdeltaganden(String studentUid);

	void registreraStudentPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

    void taBortStudentregistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

    Hinderlista hamtaHinderMotStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

	Atgard hamtaAtgardStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex);

	BehorighetsvillkorLista hamtaBehorighetsVillkorForTillfallesantagning(String kurstillfallesantagningUid);

	TillfallesdeltagandeLista hamtaAllaKurstillfallesdeltagandenForStudent(String studentUid);

	Tillfallesdeltagande hamtaKurspaketeringstillfallesdeltagandeForStudiestrukturreferens(String studiestrukturreferens);

	IngaendeKurspaketeringstillfalleLista hamtaIngaendeKurspaketeringMedBarn(String studiestrukturreferens);

	IngaendeKurspaketeringstillfalleLista hamtaStudiestrukturerForStudent(String studentuid);

	GruppLista hamtaGrupperForUtbildning(String utbildninguid);
}
