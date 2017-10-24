package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.kataloginformation.*;

public interface Kataloginformation extends LadokServiceProperties {
	I18NLista hamtaOversattningarSvenska();

	Perioder listaLokalaPerioder();

	SuccessivaFordjupningar listaSuccessivaFordjupningar();

	SvenskaOrter listaSvenskaOrter();

	Kommuner listaKommuner();

	Lander listaLander();

	Undervisningstider listaUndervisningstider();

	Undervisningsformer listaUndervisningsformer();

	Betygsskalor listaBetygskalor();

	NivaerInomStudieordning listaNivaerInomStudieordning();

	Amnesgrupper listaAmnesgrupper();

	Studietakter listaStudietakter();

	Finansieringsformer listaFinansieringsformer();

	Utbildningsomraden listaUtbildningsomraden();

	OrganisationLista listaOrganisationer();

	KravPaTidigareStudierLista listaKravPaTidigareStudier();

	Studielokaliseringar listaStudielokaliseringar();

	Antagningsomgangar hamtaAntagningsomgangar();

	UndervisningssprakLista listaUndervisningssprak();

	Studieordningar listaStudieordningar();

	Enheter listaEnheter();

	Omradesbehorigheter listaOmradesbehorigheter();

	Anvandare hamtAutentiserad();
}
