package se.sunet.ati.ladok.rest.services.impl;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.kataloginformation.Amnesgrupper;
import se.ladok.schemas.kataloginformation.Antagningsomgangar;
import se.ladok.schemas.kataloginformation.Betygsskalor;
import se.ladok.schemas.kataloginformation.Finansieringsformer;
import se.ladok.schemas.kataloginformation.I18NLista;
import se.ladok.schemas.kataloginformation.Kommuner;
import se.ladok.schemas.kataloginformation.KravPaTidigareStudierLista;
import se.ladok.schemas.kataloginformation.Lander;
import se.ladok.schemas.kataloginformation.NivaerInomStudieordning;
import se.ladok.schemas.kataloginformation.Omradesbehorigheter;
import se.ladok.schemas.kataloginformation.OrganisationLista;
import se.ladok.schemas.kataloginformation.Perioder;
import se.ladok.schemas.kataloginformation.Studielokaliseringar;
import se.ladok.schemas.kataloginformation.Studieordningar;
import se.ladok.schemas.kataloginformation.Studietakter;
import se.ladok.schemas.kataloginformation.SuccessivaFordjupningar;
import se.ladok.schemas.kataloginformation.SvenskaOrter;
import se.ladok.schemas.kataloginformation.Undervisningsformer;
import se.ladok.schemas.kataloginformation.UndervisningssprakLista;
import se.ladok.schemas.kataloginformation.Undervisningstider;
import se.ladok.schemas.kataloginformation.Utbildningsomraden;
import se.sunet.ati.ladok.rest.services.Kataloginformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

public class KataloginformationImpl extends LadokServicePropertiesImpl implements Kataloginformation {

	private static final String RESOURCE_GRUNDDATA = "grunddata";

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
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("period");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(Perioder.class);
	}

	@Override
	public SuccessivaFordjupningar listaSuccessivaFordjupningar() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("successivfordjupning");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(SuccessivaFordjupningar.class);
	}

	@Override
	public SvenskaOrter listaSvenskaOrter() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("svenskort");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(SvenskaOrter.class);
	}

	@Override
	public Kommuner listaKommuner() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("kommun");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType).get(Kommuner.class);
	}

	@Override
	public Lander listaLander() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("land");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Lander.class);
	}

	@Override
	public Undervisningstider listaUndervisningstider() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("undervisningstid");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Undervisningstider.class);
	}

	@Override
	public Undervisningsformer listaUndervisningsformer() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("undervisningsform");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Undervisningsformer.class);
	}

	@Override
	public Betygsskalor listaBetygskalor() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("betygsskala");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Betygsskalor.class);
	}

	@Override
	public NivaerInomStudieordning listaNivaerInomStudieordning() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("nivainomstudieordning");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(NivaerInomStudieordning.class);
	}

	@Override
	public Amnesgrupper listaAmnesgrupper() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("amnesgrupp");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Amnesgrupper.class);
	}

	@Override
	public Studietakter listaStudietakter() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("studietakt");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Studietakter.class);
	}

	@Override
	public Finansieringsformer listaFinansieringsformer() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("finansieringsform");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Finansieringsformer.class);
	}

	@Override
	public Utbildningsomraden listaUtbildningsomraden() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("utbildningsomrade");
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
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("kravpatidigarestudier");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(KravPaTidigareStudierLista.class);
	}

	@Override
	public Studieordningar listaStudieordningar() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("studieordning");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Studieordningar.class);
	}

	@Override
	public Omradesbehorigheter listaOmradesbehorigheter() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;

		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("omradesbehorighet");

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get();

		return validatedResponse(response, Omradesbehorigheter.class);
	}

	@Override
	public Studielokaliseringar listaStudielokaliseringar() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("studielokalisering");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Studielokaliseringar.class);
	}
	
	@Override
	public Antagningsomgangar hamtaAntagningsomgangar() {	
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("antagningsomgang");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Antagningsomgangar.class);
		
	}

	@Override
	public UndervisningssprakLista listaUndervisningssprak() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("undervisningssprak");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(UndervisningssprakLista.class);
	}
}
