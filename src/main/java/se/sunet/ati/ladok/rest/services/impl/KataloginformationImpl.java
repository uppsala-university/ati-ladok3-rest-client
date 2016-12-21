package se.sunet.ati.ladok.rest.services.impl;

import javax.ws.rs.client.WebTarget;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.kataloginformation.I18NLista;
import se.ladok.schemas.kataloginformation.Kommuner;
import se.ladok.schemas.kataloginformation.Perioder;
import se.ladok.schemas.kataloginformation.SuccessivaFordjupningar;
import se.ladok.schemas.kataloginformation.SvenskaOrter;
import se.sunet.ati.ladok.rest.services.Kataloginformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

public class KataloginformationImpl extends LadokServicePropertiesImpl implements Kataloginformation {

	private static Log log = LogFactory.getLog(KataloginformationImpl.class);

	private static final String KATALOGINFORMATION_URL = "/kataloginformation";
	private static final String KATALOGINFORMATION_RESPONSE_TYPE = "application/vnd.ladok-kataloginformation";
	private static final String KATALOGINFORMATION_MEDIATYPE = "xml";

	private static final String SPRAKKOD_SVENSKA = "sv";

	WebTarget kataloginformation;

	WebTarget getClient() {
		if (this.kataloginformation == null) {
			this.kataloginformation = ClientUtil.newClient(this, KATALOGINFORMATION_URL);
		}
		return this.kataloginformation;
	}

	private I18NLista hamtaOversattningar(String sprakkod) {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("i18n").path("oversattningar").path("sprakkod").path(sprakkod);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(I18NLista.class);
	}

	@Override
	public I18NLista hamtaOversattningarSvenska() {
		return hamtaOversattningar(SPRAKKOD_SVENSKA);
	}

	@Override
	public Perioder listaLokalaPerioder() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("period");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(Perioder.class);
	}

	@Override
	public SuccessivaFordjupningar listaSuccessivaFordjupningar() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("successivfordjupning");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(SuccessivaFordjupningar.class);
	}

	@Override
	public SvenskaOrter listaSvenskaOrter() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("svenskort");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(SvenskaOrter.class);
	}

	@Override
	public Kommuner listaKommuner() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("kommun");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(Kommuner.class);
	}
}
