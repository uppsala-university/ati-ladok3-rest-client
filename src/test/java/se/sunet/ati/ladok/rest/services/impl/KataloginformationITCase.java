package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import se.ladok.schemas.kataloginformation.*;
import se.sunet.ati.ladok.rest.services.Kataloginformation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class KataloginformationITCase {

	private static Log log = LogFactory.getLog(KataloginformationITCase.class);

	@Test
	public void hamtaOversattningarSvenska() {
		Kataloginformation ki = new KataloginformationImpl();

		I18NLista i18nLista = ki.hamtaOversattningarSvenska();
		assertNotNull(i18nLista);
		assertNotNull(i18nLista.getOversattning());
		assertFalse(i18nLista.getOversattning().isEmpty());
		I18N iaaa18n = new I18N();
		iaaa18n.setI18NNyckel("commons.fel.detaljkod.antagningsforfarande");
		iaaa18n.setText("Antagningsförfarande");

		for (I18N i18n : i18nLista.getOversattning()) {
			assertTrue(!i18n.getI18NNyckel().equals(iaaa18n.getI18NNyckel())
					|| (i18n.getI18NNyckel().equals(iaaa18n.getI18NNyckel()) && i18n.getText().equals(iaaa18n.getText())));
		}

	}

	@Test
	public void listaLokalaPerioder() {
		Kataloginformation ki = new KataloginformationImpl();
		Perioder perioder = ki.listaLokalaPerioder();
		assertFalse(perioder.getPeriod().isEmpty());
	}

	@Test
	public void listaSuccessivaFordjupningar() {
		Kataloginformation ki = new KataloginformationImpl();
		SuccessivaFordjupningar successivaFordjupningar = ki.listaSuccessivaFordjupningar();
		assertFalse(successivaFordjupningar.getSuccessivFordjupning().isEmpty());
	}

	@Test
	public void listaSvenskaOrter() {
		Kataloginformation ki = new KataloginformationImpl();
		SvenskaOrter svenskaOrter = ki.listaSvenskaOrter();
		assertFalse(svenskaOrter.getSvenskOrt().isEmpty());
	}

	@Test
	public void listaKommuner() {
		Kataloginformation ki = new KataloginformationImpl();
		Kommuner kommuner = ki.listaKommuner();
		assertFalse(kommuner.getKommun().isEmpty());
	}

	@Test
	public void listaLander() {
		Kataloginformation ki = new KataloginformationImpl();
		Lander lander = ki.listaLander();
		assertFalse(lander.getLand().isEmpty());
	}

	@Test
	public void listaUndervisningstider() {
		Kataloginformation ki = new KataloginformationImpl();
		Undervisningstider undervisningstider = ki.listaUndervisningstider();
		assertFalse(undervisningstider.getUndervisningstid().isEmpty());
	}

	@Test
	public void listaUndervisningsformer() {
		Kataloginformation ki = new KataloginformationImpl();
		Undervisningsformer undervisningsformer = ki.listaUndervisningsformer();
		assertFalse(undervisningsformer.getUndervisningsform().isEmpty());
	}

	@Test
	public void listaBetygskalor() {
		Kataloginformation ki = new KataloginformationImpl();
		Betygsskalor betygsskalor = ki.listaBetygskalor();
		assertFalse(betygsskalor.getBetygsskala().isEmpty());
	}

	@Test
	public void listaNivaInomStudieordning() {
		Kataloginformation ki = new KataloginformationImpl();
		NivaerInomStudieordning nivaerInomStudieordning = ki.listaNivaerInomStudieordning();
		assertFalse(nivaerInomStudieordning.getNivaInomStudieordning().isEmpty());
	}

	@Test
	public void listaAmnesgrupper() {
		Kataloginformation ki = new KataloginformationImpl();
		Amnesgrupper amnesgrupper = ki.listaAmnesgrupper();
		assertFalse(amnesgrupper.getAmnesgrupp().isEmpty());
	}

	@Test
	public void listaStudietakter() {
		Kataloginformation ki = new KataloginformationImpl();
		Studietakter studietakter = ki.listaStudietakter();
		assertFalse(studietakter.getStudietakt().isEmpty());
	}

	@Test
	public void listaFinansieringsformer() {
		Kataloginformation ki = new KataloginformationImpl();
		Finansieringsformer finansieringsformer = ki.listaFinansieringsformer();
		assertFalse(finansieringsformer.getFinansieringsform().isEmpty());
	}

	@Test
	public void listaUtbildningsomraden() {
		Kataloginformation ki = new KataloginformationImpl();
		Utbildningsomraden utbildningsomraden = ki.listaUtbildningsomraden();
		assertFalse(utbildningsomraden.getUtbildningsomrade().isEmpty());
	}

	@Test
	public void listaOrganisationer() {
		Kataloginformation ki = new KataloginformationImpl();
		OrganisationLista organisationLista = ki.listaOrganisationer();
		assertFalse(organisationLista.getOrganisationer().isEmpty());
	}

	@Test
	public void listaKravPaTidigareStudier() {
		Kataloginformation ki = new KataloginformationImpl();
		KravPaTidigareStudierLista kravPaTidigareStudierLista = ki.listaKravPaTidigareStudier();
		assertFalse(kravPaTidigareStudierLista.getKravPaTidigareStudier().isEmpty());
	}

	@Test
	public void listaStudielokaliseringar() {
		Kataloginformation ki = new KataloginformationImpl();
		Studielokaliseringar studielokaliseringar = ki.listaStudielokaliseringar();
		assertFalse(studielokaliseringar.getStudielokalisering().isEmpty());
	}

	@Test
	public void listaUndervisningssprak()  {
		Kataloginformation ki = new KataloginformationImpl();
		UndervisningssprakLista undervisningssprak = ki.listaUndervisningssprak();
		assertFalse(undervisningssprak.getUndervisningssprak().isEmpty());
	}

	@Test
	public void listaOmradesbehorigheter()  {
		Kataloginformation ki = new KataloginformationImpl();
		Omradesbehorigheter undervisningssprak = ki.listaOmradesbehorigheter();
		assertFalse(undervisningssprak.getOmradesbehorighet().isEmpty());
	}
	
	@Test
	public void hamtAutentiserad()  {
		Kataloginformation ki = new KataloginformationImpl();
		Anvandare a = ki.hamtAutentiserad();
		assertFalse(a.getAnvandarnamn() == null);
	}

	@Test
	public void listaAktivitetstillfallestyper() throws Exception {
		Kataloginformation ki = new KataloginformationImpl();
		Aktivitetstillfallestyper aktivitetstillfallestyper = ki.listaAktivitetstillfallestyper();
		assertFalse(aktivitetstillfallestyper.getAktivitetstillfallestyp().isEmpty());
	}
	
	@Test
	public void listaMarkningsnycklar() throws Exception {
		Kataloginformation ki = new KataloginformationImpl();
		Markningsnycklar markningsnycklar = ki.listaMarkningsnycklar();
		assertFalse(markningsnycklar.getMarkningsnyckel().isEmpty());
	}
}
