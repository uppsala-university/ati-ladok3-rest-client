package se.sunet.ati.ladok.rest.services.impl;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;
import static se.sunet.ati.ladok.rest.util.ClientUtil.CONTENT_TYPE_HEADER_NAME;
import static se.sunet.ati.ladok.rest.util.ClientUtil.CONTENT_TYPE_HEADER_VALUE;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.extintegration.UtannonseringLista;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.sunet.ati.ladok.rest.services.Extintegration;
import se.sunet.ati.ladok.rest.util.ClientUtil;

public class ExtintegrationImpl extends LadokServicePropertiesImpl implements Extintegration {
	
	private static final String RESOURCE_GRUNDDATA = "emil";
	private static final String RESOURCE_UTANNONSERINGAR = "utannonseringar";
	private static final String RESOURCE_UTBILDNINGSTILLFALLE = "utbildningstillfalle";
	
	private static Log log = LogFactory.getLog(ExtintegrationImpl.class);

	private static final String EXTINTEGRATION_URL = "/extintegration";
	private static final String EXTINTEGRATION_RESPONSE_TYPE = "application/vnd.ladok-extintegration";
	private static final String EXTINTEGRATION_MEDIATYPE = "xml;charset=UTF-8";

	
	WebTarget extintegration;

	WebTarget getClient() {
		if (this.extintegration == null) {
			this.extintegration = ClientUtil.newClient(this, EXTINTEGRATION_URL);
		}
		return this.extintegration;
	}

	@Override
	public Utbildningstillfalle utannonseraUtbildningstillfalle(String utbildningstillfalleUID) {
		String responseType = EXTINTEGRATION_RESPONSE_TYPE + "+" + EXTINTEGRATION_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_UTANNONSERINGAR)
				.path(RESOURCE_UTBILDNINGSTILLFALLE)
				.path(utbildningstillfalleUID);

		log.info("Query URL: " + client.getUri());
		
		Response response = client.request()
				.header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity("", CONTENT_TYPE_HEADER_VALUE));	
		
		return validatedResponse(response, Utbildningstillfalle.class);
	}
	
	@Override
	public UtannonseringLista isUtbildningstillfalleUtannonserat(String utbildningstillfalleUID) {
		
		String responseType = EXTINTEGRATION_RESPONSE_TYPE + "+" + EXTINTEGRATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_UTANNONSERINGAR)
				.path(RESOURCE_UTBILDNINGSTILLFALLE)
				.path(utbildningstillfalleUID);

		log.info("Query URL: " + client.getUri());
		
		Response response = client
				.request()
				.header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, UtannonseringLista.class);
	}
	
	
}
