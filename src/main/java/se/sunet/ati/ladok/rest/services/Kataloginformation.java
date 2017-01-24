package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.kataloginformation.Betygsskalor;
import se.ladok.schemas.kataloginformation.I18NLista;
import se.ladok.schemas.kataloginformation.Kommuner;
import se.ladok.schemas.kataloginformation.Perioder;
import se.ladok.schemas.kataloginformation.SuccessivaFordjupningar;
import se.ladok.schemas.kataloginformation.SvenskaOrter;
import se.ladok.schemas.kataloginformation.Undervisningstider;

public interface Kataloginformation extends LadokServiceProperties {
	I18NLista hamtaOversattningarSvenska();

	Perioder listaLokalaPerioder();

	SuccessivaFordjupningar listaSuccessivaFordjupningar();

	SvenskaOrter listaSvenskaOrter();

	Kommuner listaKommuner();

	Undervisningstider listaUndervisningstider();

	Betygsskalor listaBetygskalor();
}
