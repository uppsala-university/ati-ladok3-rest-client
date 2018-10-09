package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.dap.ServiceIndex;
import se.ladok.schemas.extintegration.UtannonseringLista;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;

public interface Extintegration extends LadokServiceProperties {
	Utbildningstillfalle utannonseraUtbildningstillfalle(String utbildningstillfalleUID);

	UtannonseringLista isUtbildningstillfalleUtannonserat(String utbildningstillfalleUID);

	ServiceIndex hamtaIndex();
}
