package se.sunet.ati.ladok.rest.services.impl;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;
import static se.sunet.ati.ladok.rest.util.ClientUtil.CONTENT_TYPE_HEADER_NAME;
import static se.sunet.ati.ladok.rest.util.ClientUtil.CONTENT_TYPE_HEADER_VALUE;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.dap.ServiceIndex;
import se.ladok.schemas.examen.Precisering;
import se.ladok.schemas.examen.PreciseringLista;
import se.ladok.schemas.extintegration.UtannonseringLista;
import se.ladok.schemas.kataloginformation.Undervisningstider;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.sunet.ati.ladok.rest.services.Examen;
import se.sunet.ati.ladok.rest.services.Extintegration;
import se.sunet.ati.ladok.rest.util.ClientUtil;

public class ExamenImpl extends LadokServicePropertiesImpl implements Examen {
	
	private static final String RESOURCE_PRECISERING = "precisering";
	
	// https://www.integrationstest.ladok.se/gui/proxy/examen/precisering?preciseringstyp=huvudomrade
	private static Log log = LogFactory.getLog(ExamenImpl.class);

	private static final String EXAMEN_URL = "/examen";
	private static final String EXAMEN_RESPONSE_TYPE = "application/vnd.ladok-examen";
	private static final String EXAMEN_MEDIATYPE = "xml;charset=UTF-8";

	
	WebTarget extintegration;

	WebTarget getClient() {
		if (this.extintegration == null) {
			this.extintegration = ClientUtil.newClient(this, EXAMEN_URL);
		}
		return this.extintegration;
	}

	@Override
	public PreciseringLista listaPrecisering(String preciseringstyp) {
		String responseType = EXAMEN_RESPONSE_TYPE + "+" + EXAMEN_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_PRECISERING)
				.queryParam("preciseringstyp", preciseringstyp);

		log.info("Query URL: " + client.getUri());
		

		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		
		return validatedResponse(response, PreciseringLista.class);
	}
	
	
}
