package se.sunet.ati.ladok.rest.services.impl;

import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.NivaerInomStudieordning;
import se.ladok.schemas.utbildningsinformation.NivaInomStudieordning;
import se.ladok.schemas.utbildningsinformation.LokalUtbildningsmall;
import se.ladok.schemas.utbildningsinformation.ObjectFactory;
import se.ladok.schemas.utbildningsinformation.UtbildningProjektion;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstans;
import se.ladok.schemas.utbildningsinformation.Utbildningsinstansprojektioner;
import se.ladok.schemas.utbildningsinformation.Utbildningstillfalle;
import se.ladok.schemas.utbildningsinformation.Utbildningstyp;
import se.ladok.schemas.utbildningsinformation.Versionsinformation;
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
	private static final String RESOURCE_GRUNDDATA = "grunddata";
	private static final String RESOURCE_KOD = "kod";
	private static final String RESOURCE_LOKAL = "lokal";
	private static final String RESOURCE_NIVAINOMSTUDIEORDNING = "nivaInomStudieordning";
	private static final String RESOURCE_ORGANISATION = "organisation";
	private static final String RESOURCE_UNDERLIGGANDE = "underliggande";
	private static final String RESOURCE_UTBILDNINGSTYP = "utbildningstyp";
	private static final String RESOURCE_VERSION = "version";

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
	public LokalUtbildningsmall hamtaLokalUtbildningsmall(int utbildningstypID, String datum) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSMALL).path(RESOURCE_LOKAL)
				.queryParam("utbildningstypID", utbildningstypID)
				.queryParam("datum", datum);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(LokalUtbildningsmall.class);
	}

	@Override
	public NivaInomStudieordning hamtaNivaInomStudieordning(String kod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_NIVAINOMSTUDIEORDNING)
				.path(RESOURCE_KOD)
				.path(kod);

		return client.request().header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType).get(NivaInomStudieordning.class);
	}

	@Override
	public NivaerInomStudieordning listaNivaerInomStudieordning() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path(RESOURCE_NIVAINOMSTUDIEORDNING);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(NivaerInomStudieordning.class);
	}

	@Override
	public Utbildningstyp hamtaUtbildningsttypID(String utbildningstypKod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_GRUNDDATA).path(RESOURCE_UTBILDNINGSTYP).path(RESOURCE_KOD).path(utbildningstypKod);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(Utbildningstyp.class);
	}

	@Override
	public List<UtbildningProjektion> hamtaUtbildningsinstansViaKod(String utbildningsinstansKod, int studieordningID, int utbildningstypID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS)
				.queryParam("kod", utbildningsinstansKod)
				.queryParam("studieordningID", studieordningID)
				.queryParam("utbildningstypID", utbildningstypID);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(Utbildningsinstansprojektioner.class).getUtbildningar();
	}

	@Override
	public Utbildningsinstans hamtaUtbildningsinstansViaUtbildningsinstansUID(String utbildningsinstansUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS).path(utbildningsinstansUID);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(Utbildningsinstans.class);
	}

	@Override
	public Utbildningstillfalle hamtaUtbildningstillfalleViaUtbildningstillfalleUID(String utbildningstillfalleUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSTILFALLE).path(utbildningstillfalleUID);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(Utbildningstillfalle.class);
	}

	@Override
	public Utbildningsinstans skapaUtbildningsinstans(Utbildningsinstans utbildningsinstans) {
		JAXBElement<Utbildningsinstans> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningsinstans(utbildningsinstans);
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS);
		return client
				.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE), Utbildningsinstans.class);
	}

	@Override
	public Utbildningsinstans skapaNyVersionUtbildningsinstans(String utbildningsinstansUID, Versionsinformation versionsinformation) {
		JAXBElement<Versionsinformation> versionsinformationJAXBElement = new ObjectFactory().createVersionsinformation(versionsinformation);
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS).path(utbildningsinstansUID).path(RESOURCE_VERSION);
		return client
				.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(versionsinformationJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE), Utbildningsinstans.class);
	}

	@Override
	public Utbildningsinstans skapaUnderliggandeUtbildningsinstans(Utbildningsinstans utbildningsinstans, String utbildningsinstansUID) {
		JAXBElement<Utbildningsinstans> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningsinstans(utbildningsinstans);
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS).path(utbildningsinstansUID).path(RESOURCE_UNDERLIGGANDE);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.post(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE), Utbildningsinstans.class);
	}

	@Override
	public Utbildningstillfalle skapaUtbildningstillfalle(Utbildningstillfalle utbildningstillfalle) {
		JAXBElement<Utbildningstillfalle> utbildningstillfalleJAXBElement = new ObjectFactory().createUtbildningstillfalle(utbildningstillfalle);
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSTILFALLE);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.post(Entity.entity(utbildningstillfalleJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE), Utbildningstillfalle.class);
	}

	@Override
	public Organisationslista sokAllaOrganisationer() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_ORGANISATION);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(Organisationslista.class);
	}

	@Override
	public Utbildningsinstans uppdateraUtbildningsinstans(Utbildningsinstans utbildningsinstans) {
		JAXBElement<Utbildningsinstans> utbildningsinstansJAXBElement = new ObjectFactory().createUtbildningsinstans(utbildningsinstans);
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS).path(utbildningsinstans.getUid());
		return client
				.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(utbildningsinstansJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE), Utbildningsinstans.class);
	}

	@Override
	public Utbildningsinstans utbildningsinstansTillStatusPaborjad(String utbildningsinstansUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS).path("status2").path(utbildningsinstansUID);
		return client
				.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE), Utbildningsinstans.class);
	}
}
