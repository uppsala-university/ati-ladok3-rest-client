package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.dap.ServiceIndex;
import se.ladok.schemas.kataloginformation.*;
import se.sunet.ati.ladok.rest.api.kataloginformation.SokOrganisationQuery;
import se.sunet.ati.ladok.rest.services.Kataloginformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;

public class KataloginformationImpl extends LadokServicePropertiesImpl implements Kataloginformation {

	private static final String RESOURCE_GRUNDDATA = "grunddata";
	private static final String RESOURCE_ANVANDARE = "anvandare";
	private static final String RESOURCE_ENHET = "enhet";
	private static final String RESOURCE_ORGANISATION = "organisation";
	private static final String RESOURCE_AKTIVITETSTILLFALLESTYP = "aktivitetstillfallestyp";
	private static final String RESOURSE_SERVICE = "service";
	private static final String RESOURSE_INDEX = "index";
	
	

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
	public Enheter listaEnheter() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path(RESOURCE_ENHET);
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Enheter.class);
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
	
	@Override
	public Anvandare hamtAutentiserad() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_ANVANDARE).path("autentiserad");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Anvandare.class);
	}
	
	
	@Override
	public AnvandarbehorighetLista hamtAnvandarbehorighetEgna() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path("anvandarbehorighet") 
				.path("egna");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(AnvandarbehorighetLista.class);
	}

	@Override
	public Organisation hamtaOrganisation(String organisationUid) {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_ORGANISATION).path(organisationUid);
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Organisation.class);
	}

	@Override
	public SokresultatOrganisationKataloginformation sokOrganisation(SokOrganisationQuery sokOrganisationQuery){
		WebTarget client = getClient()
				.path(RESOURCE_ORGANISATION)
				.path("filtrera")
				.queryParam("kod", sokOrganisationQuery.getKod())
				.queryParam("benamning", sokOrganisationQuery.getBenamning())
				.queryParam("orderBy", sokOrganisationQuery.getOrderBy())
				.queryParam("skipCount", sokOrganisationQuery.getSkipCount())
				.queryParam("onlyCount", sokOrganisationQuery.getOnlyCount())
				.queryParam("sprakkod", sokOrganisationQuery.getSprakkod());

		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatOrganisationKataloginformation.class);
	}

	@Override
	public Utbildningstyper listaUtbildningstyper() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("utbildningstyp");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Utbildningstyper.class);
	}

	@Override
	public Aktivitetstillfallestyper listaAktivitetstillfallestyper() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_AKTIVITETSTILLFALLESTYP);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Aktivitetstillfallestyper.class);
	}

	@Override
	public ServiceIndex hamtaIndex() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURSE_SERVICE)
				.path(RESOURSE_INDEX);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, ServiceIndex.class);
	}

	@Override
	public Markningsnycklar listaMarkningsnycklar() {
		String responseType = KATALOGINFORMATION_RESPONSE_TYPE + "+" + KATALOGINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path("lokalmarkning");
		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(Markningsnycklar.class);
	}

}
