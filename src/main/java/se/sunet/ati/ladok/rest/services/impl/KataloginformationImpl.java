package se.sunet.ati.ladok.rest.services.impl;

import javax.ws.rs.client.WebTarget;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.kataloginformation.*;

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

	@Override
	public Lander listaLander() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("land");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Lander.class);
	}

	@Override
	public Undervisningstider listaUndervisningstider() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("undervisningstid");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(Undervisningstider.class);
	}

	@Override
	public Betygsskalor listaBetygskalor() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("betygsskala");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Betygsskalor.class);
	}

	@Override
	public NivaerInomStudieordning listaNivaerInomStudieordning() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("nivainomstudieordning");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(NivaerInomStudieordning.class);
	}

	@Override
	public Amnesgrupper listaAmnesgrupper() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("amnesgrupp");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Amnesgrupper.class);
	}

	@Override
	public Studietakter listaStudietakter() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("studietakt");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Studietakter.class);
	}

	@Override
	public Finansieringsformer listaFinansieringsformer() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("finansieringsform");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Finansieringsformer.class);
	}

	@Override
	public Utbildningsomraden listaUtbildningsomraden() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("utbildningsomrade");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Utbildningsomraden.class);
	}

	@Override
	public OrganisationLista listaOrganisationer() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("organisation");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(OrganisationLista.class);
	}

	@Override
	public KravPaTidigareStudierLista listaKravPaTidigareStudier(){
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path("grunddata").path("kravpatidigarestudier");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(KravPaTidigareStudierLista.class);
	}
}
