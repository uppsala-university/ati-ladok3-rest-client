package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.kataloginformation.I18NLista;
import se.ladok.schemas.kataloginformation.Perioder;

public interface Kataloginformation extends LadokServiceProperties {
    
	public I18NLista hamtaOversattningarSvenska();

	public Perioder listaLokalaPerioder();
}
