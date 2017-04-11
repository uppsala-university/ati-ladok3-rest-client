package se.sunet.ati.ladok.rest.services.impl;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.LadokException;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.Attributdefinition;
import se.ladok.schemas.utbildningsinformation.Attributdefinitioner;
import se.ladok.schemas.utbildningsinformation.Beslut;
import se.ladok.schemas.utbildningsinformation.Huvudomraden;
import se.ladok.schemas.utbildningsinformation.LokalUtbildningsmall;
import se.ladok.schemas.utbildningsinformation.NivaInomStudieordning;
import se.ladok.schemas.utbildningsinformation.NivaerInomStudieordning;
import se.ladok.schemas.utbildningsinformation.ObjectFactory;
import se.ladok.schemas.utbildningsinformation.Period;
import se.ladok.schemas.utbildningsinformation.Perioder;
import se.ladok.schemas.utbildningsinformation.SokresultatUtbildningstillfalleProjektion;
import se.ladok.schemas.utbildningsinformation.UtbildningMedUnderliggandeUtbildningar;
import se.ladok.schemas.utbildningsinformation.UtbildningProjektion;
import se.ladok.schemas.utbildningsinformation.Utbildningsinformationsstruktur;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstansbox;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstansprojektioner;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Utbildningstyp;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;
import se.sunet.ati.ladok.rest.services.LadokRestClientException;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

public class UtbildningsinformationImpl extends LadokServicePropertiesImpl implements Utbildningsinformation {
	
	private static Log log = LogFactory.getLog(UtbildningsinformationImpl.class);
	private static final String UTBILDNINGSINFORMATION_URL = "/utbildningsinformation";

	private static final String UTBILDNINGSINFORMATION_RESPONSE_TYPE = "application/vnd.ladok-utbildningsinformation";
	private static final String UTBILDNINGSINFORMATION_MEDIATYPE = "xml";
	private static final String RESOURCE_UTBILDNINGSMALL = "utbildningsmall";
	private static final String RESOURCE_UTBILDNINGSTILFALLE = "utbildningstillfalle";
	private static final String RESOURCE_UTBILDNINGSINSTANS = "utbildningsinstans";
	private static final String RESOURCE_AVVECKLA = "avveckla";
	private static final String RESOURCE_GRUNDDATA = "grunddata";
	private static final String RESOURCE_KOD = "kod";
	private static final String RESOURCE_LOKAL = "lokal";
	private static final String RESOURCE_NIVAINOMSTUDIEORDNING = "nivaInomStudieordning";
	private static final String RESOURCE_ORGANISATION = "organisation";
	private static final String RESOURCE_PERIOD = "period";
	private static final String RESOURCE_UNDERLIGGANDE = "underliggande";
	private static final String RESOURCE_UTBILDNINGSTYP = "utbildningstyp";
	private static final String RESOURCE_VERSION = "version";
	private static final String RESOURCE_HUVUDOMRADE = "huvudomrade";
	private static final String RESOURCE_STRUKTUR = "struktur";
	
	private static final String RESOURCE_NATIONELL = "nationell";
	private static final String RESOURCE_ATTRIBUTDEFINITIONER = "attributdefinitioner";
	private static final String RESOURCE_MEDUNDERLIGGANDE = "utbildningmedunderliggandeutbildningar";
	private static final String RESOURCE_UTBILDNINGSINSTANSBOX = "utbildningsinstansbox";

	WebTarget utbildningsinformation;

	/**
	 * This method makes it possible to lazily constructing the web client,
	 * which is needed in order to enable loading properties either from a
	 * properties file or via dependency injection in an OSGi environment.
	 */
	WebTarget getClient() {
		if (this.utbildningsinformation == null) {
			this.utbildningsinformation = ClientUtil.newClient(this, UTBILDNINGSINFORMATION_URL);
		}
		return this.utbildningsinformation;
	}

	@Override
	public List<Attributdefinition> hamtaAttributdefinitionerViaUtbildningstyp(int utbildningstypID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSMALL)
				.path(RESOURCE_NATIONELL)
				.path(RESOURCE_ATTRIBUTDEFINITIONER)
				.queryParam("utbildningstypID", utbildningstypID);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get();

		return validatedResponse(response, Attributdefinitioner.class).getAttributdefinitioner();
	}

	@Override
	public LokalUtbildningsmall hamtaLokalUtbildningsmall(int utbildningstypID, String datum) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSMALL)
				.path(RESOURCE_LOKAL)
				.queryParam("utbildningstypID", utbildningstypID)
				.queryParam("datum", datum);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, LokalUtbildningsmall.class);
	}

	@Override
	public LokalUtbildningsmall hamtaLokalUtbildningsmall(String utbildningsmallUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSMALL)
				.path(RESOURCE_LOKAL)
				.path(utbildningsmallUID);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, LokalUtbildningsmall.class);
	}

	@Override
	public NivaInomStudieordning hamtaNivaInomStudieordning(String kod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_NIVAINOMSTUDIEORDNING)
				.path(RESOURCE_KOD)
				.path(kod);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, NivaInomStudieordning.class);
	}

	@Override
	public NivaerInomStudieordning hamtaNivaerInomStudieordning() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_NIVAINOMSTUDIEORDNING);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, NivaerInomStudieordning.class);
	}
	

	@Override
	public List<Period> hamtaPerioder() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_PERIOD);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Perioder.class).getPeriod();
	}

	@Override
	public Period hamtaPeriodViaKod(String periodKod) {
		List<Period> perioder = hamtaPerioder();
		if (periodKod != null) {
			for (Period period : perioder) {
				if (periodKod.equals(period.getKod())) {
					return period;
				}
			}
		}
		return null;
	}

	@Override
	public Utbildningstyp hamtaUtbildningstypViaKod(String utbildningstypKod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_UTBILDNINGSTYP)
				.path(RESOURCE_KOD)
				.path(utbildningstypKod);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningstyp.class);
	}

	@Override
	public List<UtbildningProjektion> hamtaUtbildningsinstansViaKod(String utbildningsinstansKod, int studieordningID, int utbildningstypID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.queryParam("kod", utbildningsinstansKod)
				.queryParam("studieordningID", studieordningID)
				.queryParam("utbildningstypID", utbildningstypID);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningsinstansprojektioner.class).getUtbildningar();
	}

	@Override
	public List<UtbildningProjektion> hamtaUtbildningsinstansViaKod(String utbildningsinstansKod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.queryParam("kod", utbildningsinstansKod);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningsinstansprojektioner.class).getUtbildningar();
	}

	@Override
	public Utbildningsinstans hamtaUtbildningsinstansViaUID(String utbildningsinstansUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS).path(utbildningsinstansUID);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningsinstans.class);
	}

	@Override
	public Utbildningstillfalle hamtaUtbildningstillfalleViaUID(String utbildningstillfalleUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE)
				.path(utbildningstillfalleUID);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningstillfalle.class);
	}

	@Override
	public Utbildningsinstans skapaUtbildningsinstans(Utbildningsinstans utbildningsinstans) {
		JAXBElement<Utbildningsinstans> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningsinstans(utbildningsinstans);
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS);
		
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}
	
	@Override
	public Utbildningsinstans skapaUtbildningsinstansMedunderliggandeutbildningar(UtbildningMedUnderliggandeUtbildningar utbildningsinstans) {
		JAXBElement<UtbildningMedUnderliggandeUtbildningar> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningMedUnderliggandeUtbildningar(utbildningsinstans);
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS).path(RESOURCE_MEDUNDERLIGGANDE);
		
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}

	@Override
	public void avvecklaUtbildning(String utbildningUID, Beslut beslut) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
        //The date isn't supposed to append timezone information, which it does out of the box. Explicitly tell the object that it is undefined to avoid that.
		if (beslut.getBeslutsdatum() != null) {
			beslut.getBeslutsdatum().setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		}
		JAXBElement<Beslut> beslutJAXBElement = new ObjectFactory().createBeslut(beslut);

		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS).path(RESOURCE_AVVECKLA).path(utbildningUID);

		log.debug("PUT URL: " + client.getUri());
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(beslutJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		validatedResponse(response, String.class);
	}

	@Override
	public Utbildningsinstans skapaUtbildningsinstansNyVersion(String utbildningsinstansUID, Versionsinformation versionsinformation) {
		JAXBElement<Versionsinformation> versionsinformationJAXBElement = new ObjectFactory().createVersionsinformation(versionsinformation);
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path(utbildningsinstansUID).path(RESOURCE_VERSION);

		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(versionsinformationJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}

	@Override
	public Utbildningsinstans skapaUtbildningsinstansUnderliggande(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID) {
		JAXBElement<Utbildningsinstans> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningsinstans(utbildningsinstans);
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS).path(utbildningsinstansUID)
				.path(RESOURCE_UNDERLIGGANDE);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.post(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}
	
	@Override
	public Utbildningsinstans uppdateraUtbildningsinstansUnderliggande(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID) {
		JAXBElement<Utbildningsinstans> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningsinstans(utbildningsinstans);
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS).path(utbildningsinstansUID)
				.path(RESOURCE_UNDERLIGGANDE);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.put(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}
	
	@Override
	public Utbildningsinformationsstruktur hamtaStruktur(String utbildningsinformationsstrukturUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_STRUKTUR).path(utbildningsinformationsstrukturUID);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningsinformationsstruktur.class);
	}

	
	@Override
	public Utbildningsinformationsstruktur uppdateraStruktur(Utbildningsinformationsstruktur utbildningsinformationsstruktur, String strukturUID) {
		JAXBElement<Utbildningsinformationsstruktur> utbildningsinformationsstrukturJAXBElement = new ObjectFactory().createUtbildningsinformationsstruktur(utbildningsinformationsstruktur);
		WebTarget client = getClient()
				.path(RESOURCE_STRUKTUR).path(strukturUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.put(Entity.entity(utbildningsinformationsstrukturJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinformationsstruktur.class);
	}
	
	
	@Override
	public Utbildningsinstansbox hamtaUtbildningsinstansbox(int utbildningstypID, String utbildningsinstansUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_STRUKTUR)
				.path(RESOURCE_UTBILDNINGSINSTANSBOX)
				.path(String.valueOf(utbildningstypID))
				.path(utbildningsinstansUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
	
		return validatedResponse(response, Utbildningsinstansbox.class);
	}
	
	
	@Override
	public Utbildningsinformationsstruktur skapaStruktur(Utbildningsinformationsstruktur utbildningsinformationsstruktur) {
		JAXBElement<Utbildningsinformationsstruktur> utbildningsinformationsstrukturJAXBElement = new ObjectFactory().createUtbildningsinformationsstruktur(utbildningsinformationsstruktur);
		WebTarget client = getClient()
				.path(RESOURCE_STRUKTUR);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.post(Entity.entity(utbildningsinformationsstrukturJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinformationsstruktur.class);
	}

	@Override
	public Utbildningstillfalle skapaUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle) {
		JAXBElement<Utbildningstillfalle> utbildningstillfalleJAXBElement = new ObjectFactory().createUtbildningstillfalle(utbildningstillfalle);
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.post(Entity.entity(utbildningstillfalleJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningstillfalle.class);
	}

	@Override
	public Utbildningstillfalle uppdateraUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle) {
		JAXBElement<Utbildningstillfalle> utbildningstillfalleJAXBElement = new ObjectFactory().createUtbildningstillfalle(utbildningstillfalle);
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE)
				.path(utbildningstillfalle.getUid());

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.put(Entity.entity(utbildningstillfalleJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningstillfalle.class);
	}

	@Override
	public Utbildningstillfalle utbildningstillfalleTillStatusKomplett(String utbildningstillfalleUID) {
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE)
				.path("status3")
				.path(utbildningstillfalleUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.put(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningstillfalle.class);
	}

	@Override
	public Organisationslista sokAllaOrganisationer() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_ORGANISATION);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Organisationslista.class);
	}

	@Override
	public Organisationslista sokOrganisationer(final String kod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_ORGANISATION)
				.queryParam("kod", kod);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Organisationslista.class);
	}

	@Override
	public Utbildningsinstans uppdateraUtbildningsinstans(Utbildningsinstans utbildningsinstans) {
		JAXBElement<Utbildningsinstans> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningsinstans(utbildningsinstans);
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path(utbildningsinstans.getUid());

		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}

	@Override
	public Utbildningsinstans utbildningsinstansTillStatusPaborjad(String utbildningsinstansUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path("status2").path(utbildningsinstansUID);

		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}

	@Override
	public Utbildningsinstans utbildningsinstansTillStatusKomplett(String utbildningsinstansUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path("status3").path(utbildningsinstansUID);

		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}

	@Override
	public SokresultatUtbildningstillfalleProjektion sokUtbildningstillfallen(String utbildningstypID,
																			  String utbildningstillfallestypID,
																			  String studieordningID,
																			  String utbildningstillfalleskod,
																			  String utbildningskod,
																			  String benamning,
																			  String organisationUID,
																			  String status,
																			  String studieperiod,
																			  int page,
																			  int limit,
																			  boolean skipCount,
																			  boolean onlyCount,
																			  String sprakkod) {
		WebTarget client = getClient()
				.path("utbildningstillfalle")
				.path("filtrera")
				.queryParam("utbildningstypID", utbildningstypID)
				.queryParam("utbildningstillfallestypID", utbildningstillfallestypID)
				.queryParam("studieordningID", studieordningID)
				.queryParam("utbildningstillfalleskod", utbildningstillfalleskod)
				.queryParam("utbildningskod", utbildningskod)
				.queryParam("benamning", benamning)
				.queryParam("organisationUID", organisationUID)
				.queryParam("status", status)
				.queryParam("studieperiod", studieperiod)
				.queryParam("page", page)
				.queryParam("limit", limit)
				.queryParam("skipCount", skipCount)
				.queryParam("onlyCount", onlyCount)
				.queryParam("sprakkod", sprakkod);

		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatUtbildningstillfalleProjektion.class);
	}

	@Override
	public Huvudomraden hamtaHuvudamraden() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_HUVUDOMRADE);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Huvudomraden.class);
	}

	private <T> T validatedResponse(Response response, Class<T> responseType) {
		if (response.getStatus()/100 == 2) {
			return response.readEntity(responseType);
		} else {
			throw new LadokRestClientException(response.getStatus(), response.readEntity(LadokException.class));
		}
	}
	
	
}
