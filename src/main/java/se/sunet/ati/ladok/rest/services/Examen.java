package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.examen.PreciseringLista;

/*
 * Examen
 */
public interface Examen extends LadokServiceProperties {

	PreciseringLista listaPrecisering(String preciseringstyp);
	
}
