package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.utbildningsinformation.*;
import se.sunet.ati.ladok.rest.api.utbildningsinformation.SokUtbildningsinstansQuery;
import se.sunet.ati.ladok.rest.api.utbildningsinformation.type.Status;
import se.sunet.ati.ladok.rest.api.utbildningsinformation.SokUtbildningstillfallenQuery;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;

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
	private static final String RESOURCE_STUDIEORDNING = "studieordning";
	private static final String RESOURCE_ORGANISATION = "organisation";
	private static final String RESOURCE_PERIOD = "period";
	private static final String RESOURCE_UNDERLIGGANDE = "underliggande";
	private static final String RESOURCE_UTBILDNINGSTYP = "utbildningstyp";
	private static final String RESOURCE_VERSION = "version";
	private static final String RESOURCE_HUVUDOMRADE = "huvudomrade";
	private static final String RESOURCE_STRUKTUR = "struktur";
	private static final String RESOURCE_PUBLICERA = "publicera";
	private static final String RESOURCE_BOX = "box";

	private static final String RESOURCE_NATIONELL = "nationell";
	private static final String RESOURCE_ATTRIBUTDEFINITIONER = "attributdefinitioner";
	private static final String RESOURCE_MEDUNDERLIGGANDE = "utbildningmedunderliggandeutbildningar";
	private static final String RESOURCE_UTBILDNINGSINSTANSBOX = "utbildningsinstansbox";
	private static final String RESOURCE_UTBILDNINGSTILLFALLESSBOX = "utbildningstillfallesbox";
	private static final String RESOURCE_MARKNINGSNYCKEL = "markningsnyckel";
	private static final String RESOURCE_MARKNINGSVARDE = "markningsvarde";
	private static final String RESOURCE_VARDEN = "varden";
	private static final String UTBILDNINGSBAS_UID = "utbildningsbasUID";

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
	public List<Markningsnyckel> hamtaMarkningsnycklar() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_MARKNINGSNYCKEL);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		return validatedResponse(response, Markningsnycklar.class).getMarkningsnyckel();
	}
	
	@Override
	public Markningsvarde hamtaMarkningsvarde(String kod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_MARKNINGSVARDE).path(kod);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		return validatedResponse(response, Markningsvarde.class);
	}
	
	@Override
	public Markningsvarden hamtaMarkningsvarden(String kod) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_MARKNINGSNYCKEL).path(kod).path(RESOURCE_VARDEN);
		System.out.println(client.getUri().getPath());
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		return validatedResponse(response, Markningsvarden.class);
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
	public Studieordningar hamtaStudieordningar() {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_STUDIEORDNING);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Studieordningar.class);
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
		JAXBElement<Beslut> beslutJAXBElement = getBeslutJAXBElement(beslut);

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
	public Utbildningsinformationsstruktur sokStruktur(String utbildningsbasUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_STRUKTUR).queryParam(UTBILDNINGSBAS_UID, utbildningsbasUID);
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
	public Utbildningstillfallesbox hamtaUtbildningstillfallesbox(int utbildningstypID, String utbildningstillfalleUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_STRUKTUR)
				.path(RESOURCE_UTBILDNINGSTILLFALLESSBOX)
				.path(String.valueOf(utbildningstypID))
				.path(utbildningstillfalleUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
	
		return validatedResponse(response, Utbildningstillfallesbox.class);
	}

	@Override
	public Box publiceraBox(String strukturUID, String boxUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_STRUKTUR)
				.path(strukturUID)
				.path(RESOURCE_PUBLICERA)
				.path(RESOURCE_BOX)
				.path(boxUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Box.class);
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
	public Utbildningstillfalle stallInTillfalle(String utbildningstillfalleUID, Beslut beslut) {
		JAXBElement<Beslut> beslutJAXBElement = getBeslutJAXBElement(beslut);
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE)
				.path("installt")
				.path(utbildningstillfalleUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.put(Entity.entity(beslutJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningstillfalle.class);
	}

	@Override
	public Utbildningstillfalle bytUtbildningsinstansForUtbildningstillfalle(String utbildningstillfalleUID, String utbildningsinstansUID) {
		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE)
				.path(utbildningstillfalleUID)
				.path("bytutbildningsinstans");

		IdentitetRepresentation identitetRepresentation = new IdentitetRepresentation(utbildningsinstansUID);

		final String LADOK_UTBILDNINGSINFORMATION_JSON = "application/vnd.ladok-utbildningsinformation+json";
		Response response = client.request()
				.header("Content-Type", LADOK_UTBILDNINGSINFORMATION_JSON)
				.accept(LADOK_UTBILDNINGSINFORMATION_JSON)
				.put(Entity.entity(identitetRepresentation, LADOK_UTBILDNINGSINFORMATION_JSON));

		return validatedResponse(response, Utbildningstillfalle.class);
	}

	/**
	 * Den här klassen ska vara privat eftersom detta är en nödlösning eftersom
	 * XSDerna från Ladok missar att inkludera typen IdentitetRepresentation.
	 *
	 * I väntan på att Ladoks XSDer innehåller typen ska DTO-projektet utökas till att
	 * innehålla IdentitetRepresentation.
	 *
	 * Se ärende https://jira.its.umu.se/browse/LADOKSUPP-416
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name = "IdentitetRepresentation",
			        namespace = "http://schemas.ladok.se/utbildningsinformation" )
	private static class IdentitetRepresentation {

		@XmlElement(name = "Uid", namespace = "http://schemas.ladok.se/utbildningsinformation")
		private String uid;

		public IdentitetRepresentation(String uid) {
			this.uid = uid;
		}

		public IdentitetRepresentation() {
			// Behövs för serializering
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}
	}

	@Override
	public Utbildningstillfalle utbildningstillfalleTillStatusKomplett(String utbildningstillfalleUID, Beslut beslut) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		JAXBElement<Beslut> beslutJAXBElement = getBeslutJAXBElement(beslut);

		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE)
				.path("status3")
				.path(utbildningstillfalleUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(beslutJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningstillfalle.class);
	}

	@Override
	public Utbildningstillfalle utannonseraUtbildningstillfalle(String utbildningstillfalleUID) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSTILFALLE)
				.path("publicering")
				.path(utbildningstillfalleUID);

		Response response = client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE));

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
	public Utbildningsinstans utbildningsinstansTillStatusKomplett(String utbildningsinstansUID, Beslut beslut) {
		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		JAXBElement<Beslut> beslutJAXBElement = getBeslutJAXBElement(beslut);

		WebTarget client = getClient()
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path("status3").path(utbildningsinstansUID);

		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(beslutJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Utbildningsinstans.class);
	}

	private JAXBElement<Beslut> getBeslutJAXBElement(Beslut beslut) {
		return new ObjectFactory().createBeslut(beslut);
	}

	@Override
	public SokresultatUtbildningstillfalleProjektion sokUtbildningstillfallen(String utbildningstypID,
																			  String utbildningstillfallestypID,
																			  String studieordningID,
																			  Collection<String> utbildningstillfalleskod,
																			  Collection<String> utbildningskod,
																			  String benamning,
																			  Collection<String> organisationUID,
																			  Collection<String> status,
																			  String studieperiod,
																			  int page, int limit, boolean skipCount, boolean onlyCount, String sprakkod) {

		WebTarget client = getClient()
				.path("utbildningstillfalle")
				.path("filtrera")
				.queryParam("utbildningstypID", utbildningstypID)
				.queryParam("utbildningstillfallestypID", utbildningstillfallestypID)
				.queryParam("studieordningID", studieordningID)
				.queryParam("benamning", benamning)
				.queryParam("studieperiod", studieperiod)
				.queryParam("page", page)
				.queryParam("limit", limit)
				.queryParam("skipCount", skipCount)
				.queryParam("onlyCount", onlyCount)
				.queryParam("sprakkod", sprakkod);

		client = addQueryParams("utbildningstillfalleskod", utbildningstillfalleskod, client);
		client = addQueryParams("utbildningskod", utbildningskod, client);
		client = addQueryParams("organisationUID", organisationUID, client);
		client = addQueryParams("status", status, client);

		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatUtbildningstillfalleProjektion.class);
	}

	private <T> WebTarget addQueryParams(final String parameterName, Collection<T> paramValues, WebTarget client ) {
		if (paramValues == null) {
			return client;
		}

		WebTarget newClient = client;
		for (T val: paramValues) {
			newClient = newClient.queryParam(parameterName, val);
		}
		return newClient;
	}

	private <T> WebTarget addQueryParam(final String parameterName, T paramValue, WebTarget client ) {
		if (paramValue == null) {
			return client;
		}

		return client.queryParam(parameterName, paramValue);
	}

	@Override
	public SokresultatUtbildningstillfalleProjektion sokUtbildningstillfallen(SokUtbildningstillfallenQuery query) {
		WebTarget client = getClient()
				.path("utbildningstillfalle")
				.path("filtrera")
				.queryParam("utbildningstypID", query.getUtbildningstypID())
				.queryParam("utbildningstillfallestypID", query.getUtbildningstillfallestypID())
				.queryParam("studieordningID", query.getStudieordningID())
				.queryParam("benamning", query.getBenamning())
				.queryParam("studieperiod", query.getStudieperiod())
				.queryParam("startperiodID", query.getStartperiodID())
				.queryParam("page", query.getPage())
				.queryParam("limit", query.getLimit())
				.queryParam("skipCount", query.getSkipCount())
				.queryParam("onlyCount", query.getOnlyCount())
				.queryParam("sprakkod", query.getSprakkod());
		client = addQueryParams("utbildningstillfalleskod", query.getUtbildningstillfalleskod(), client);
		client = addQueryParams("utbildningskod", query.getUtbildningskod(), client);
		client = addQueryParams("organisationUID", query.getOrganisationUID(), client);
		client = addQueryParams("status", query.getStatus(), client);


		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatUtbildningstillfalleProjektion.class);
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
		return sokUtbildningstillfallen(
				utbildningstypID,
				utbildningstillfallestypID,
				studieordningID,
				asListOrNull(utbildningstillfalleskod),
				asListOrNull(utbildningskod),
				benamning,
				asListOrNull(organisationUID),
				asListOrNull(status),
				studieperiod,
				page, limit, skipCount, onlyCount, sprakkod);
	}

	private Collection<String> asListOrNull(String ... val) {
		List<String> list = Arrays.asList(val);
		return list.contains(null) ? null : list;
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

	@Override
	public SokresultatUtbildningsinstans sokUtbildningsinstans(SokUtbildningsinstansQuery sokUtbildningsinstansQuery) {
		WebTarget client = getClient()
				.path("utbildningsinstans")
				.path("filtrera")
				.queryParam("utbildningUID", sokUtbildningsinstansQuery.getUtbildningUID())
				.queryParam("utbildningsinstansUID", sokUtbildningsinstansQuery.getUtbildningsinstansUID())
				.queryParam("studieordningID", sokUtbildningsinstansQuery.getStudieordningID())
				.queryParam("overliggandeUtbildningUID", sokUtbildningsinstansQuery.getOverliggandeUtbildningUID())
				.queryParam("aktuellVersion", sokUtbildningsinstansQuery.isAktuellVersion())
				.queryParam("skipCount", sokUtbildningsinstansQuery.isSkipCount())
				.queryParam("onlyCount", sokUtbildningsinstansQuery.isOnlyCount())
				.queryParam("sprakkod", sokUtbildningsinstansQuery.getSprakkod())
				.queryParam("page", sokUtbildningsinstansQuery.getPage())
				.queryParam("limit", sokUtbildningsinstansQuery.getLimit());

		client = addQueryParams("utbildningstypID", sokUtbildningsinstansQuery.getUtbildningstypID(), client);
		client = addQueryParams("utbildningskod", sokUtbildningsinstansQuery.getUtbildningskod(), client);
		client = addQueryParams("status", sokUtbildningsinstansQuery.getStatus(), client);
		client = addQueryParams("organisationUID", sokUtbildningsinstansQuery.getOrganisationUID(), client);
		client = addQueryParams("benamning", sokUtbildningsinstansQuery.getBenamning(), client);

		String responseType = UTBILDNINGSINFORMATION_RESPONSE_TYPE + "+" + UTBILDNINGSINFORMATION_MEDIATYPE;
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client.request(MediaType.APPLICATION_XML_TYPE)
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatUtbildningsinstans.class);
	}

	@Deprecated
	@Override
	public SokresultatUtbildningsinstans sokUtbildningsinstans( 
																String utbildningstypID,
																String studieordningID,
															 	String utbildningskod,
															 	String benamning,
																String status,
																int page,
																int limit,
																boolean skipCount,
																String sprakkod) {
		SokUtbildningsinstansQuery sokUtbildningsinstansQuery = SokUtbildningsinstansQuery.builder()
				.addUtbildningstypID(utbildningstypID)
				.studieordningID(studieordningID)
				.addUtbildningskod(utbildningskod)
				.addBenamning(benamning)
				.addStatus(Status.fromId(Integer.valueOf(status)))
				.page(page)
				.limit(limit)
				.skipCount(skipCount)
				.sprakkod(sprakkod)
				.build();
		return sokUtbildningsinstans(sokUtbildningsinstansQuery);
	}

}
