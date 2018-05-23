package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;

public interface Extintegration extends LadokServiceProperties {
	public Utbildningstillfalle utannonseraUtbildningstillfalle(String utbildningstillfalleUID);
}
