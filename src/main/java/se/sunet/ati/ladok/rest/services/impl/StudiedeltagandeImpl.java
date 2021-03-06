package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Student;
import se.ladok.schemas.dap.ServiceIndex;
import se.ladok.schemas.studiedeltagande.*;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurspaketeringstillfalleQuery;
import se.sunet.ati.ladok.rest.api.studiedeltagande.SokDeltagareKurstillfalleQuery;
import se.sunet.ati.ladok.rest.services.Studiedeltagande;
import se.sunet.ati.ladok.rest.util.ClientUtil;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;
import static se.sunet.ati.ladok.rest.util.ClientUtil.addQueryParams;

public class StudiedeltagandeImpl extends LadokServicePropertiesImpl implements Studiedeltagande {
	private static Log log = LogFactory.getLog(StudiedeltagandeImpl.class);

	private static final String STUDIEDELTAGANDE_URL = "/studiedeltagande";
	private static final String STUDIEDELTAGANDE_RESPONSE_TYPE = "application/vnd.ladok-studiedeltagande";
	private static final String STUDIEDELTAGANDE_MEDIATYPE = "xml;charset=UTF-8";
	private static final String RESOURCE_DELTAGARE = "deltagare";
	private static final String RESOURCE_KURSTILLFALLE = "kurstillfalle";
	private static final String RESOURCE_KURSPAKETERINGSTILLFALLE = "kurspaketeringstillfalle";
	private static final String RESOURCE_KURS = "kurs";
	private static final String RESOURCE_TILLFALLESDELTAGANDE ="tillfallesdeltagande";
	private static final String RESOURCE_KOMMANDE = "kommande";
	private static final String RESOURCE_REGISTRERING = "registrering";
	private static final String RESOURCE_HINDER = "hinder";
	private static final String RESOURCE_ATGARD = "atgard";
	private static final String RESOURCE_BEHORIGHETSVILLKOR = "behorighetsvillkor";
	private static final String RESOURCE_TILLFALLESANTAGNING = "tillfallesantagning";
	private static final String RESOURCE_KURSTILLFALLESANTAGNING = "kurstillfallesantagning";
	private static final String RESOURCE_KURSTILLFALLESANTAGNINGUID = "kurstillfallesantagninguid";
	private static final String RESOURCE_PERIODINDEX = "periodindex";
	private static final String RESOURCE_TA_BORT = "ta_bort";
	private static final String RESOURCE_KURSTILLFALLESDELTAGANDE = "kurstillfallesdeltagande";
	private static final String RESOURCE_STUDIESTRUKTURREFERENS = "studiestrukturreferens";
	private static final String RESOURCE_STUDIESTRUKTUR = "studiestruktur";
	private static final String RESOURCE_GRUPP = "grupp";
	private static final String RESOURCE_UTBILDNING = "utbildning";
	private static final String RESOURSE_SERVICE = "service";
	private static final String RESOURSE_INDEX = "index";

	// OBS Nedanstående resurs är felstavad hos Ladok3
	private static final String RESOURCE_PABORJAD_UTBILDNING = "paborjadutbilding";
	private static final String RESOURCE_STUDENT = "student";
	private static final String RESOURCE_STUDENT_CRITERIA = "personnummer";

	WebTarget studiedeltagande;

	WebTarget getClient() {
		if (this.studiedeltagande == null) {
			this.studiedeltagande = ClientUtil.newClient(this, STUDIEDELTAGANDE_URL);
		}
		return this.studiedeltagande;
	}
	
	@Override
	public Student hamtaStudentViaUid(String studentUid) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_STUDENT)
				.path(studentUid);
		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		return validatedResponse(response, Student.class);
	}

	@Override
	public Student hamtaStudentViaPersonnummer(String personnummer) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_STUDENT)
				.path(RESOURCE_STUDENT_CRITERIA)
				.path(personnummer);
		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		
		return validatedResponse(response, Student.class);
	}
	
	@Override
	public TillfallesdeltagandeLista hamtaPaborjadeKurser(String studentUid) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_PABORJAD_UTBILDNING)
				.path(RESOURCE_KURS).path(RESOURCE_STUDENT)
				.path(studentUid);
		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		
		return validatedResponse(response, TillfallesdeltagandeLista.class);
	}
	
	@Override
	public TillfallesdeltagandeLista hamtaKommandeKurstillfallesdeltaganden(String studentUid) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_TILLFALLESDELTAGANDE)
				.path(RESOURCE_KOMMANDE)
				.path(RESOURCE_STUDENT)
				.path(studentUid);
		
		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		
		return validatedResponse(response, TillfallesdeltagandeLista.class);
	}
	
	@Deprecated
	@Override
	public SokresultatDeltagare sokDeltagareKurstillfalle(String kurstillfalleUID) {
		SokDeltagareKurstillfalleQuery sokDeltagareKurstillfalleQuery = SokDeltagareKurstillfalleQuery.builder()
				.addKurstillfalleUID(kurstillfalleUID)
				.build();
		return sokDeltagareKurstillfalle(sokDeltagareKurstillfalleQuery);
	}
	
	@Override
	public SokresultatDeltagare sokDeltagareKurstillfalle(SokDeltagareKurstillfalleQuery query) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_DELTAGARE).path(RESOURCE_KURSTILLFALLE);
		client = addQueryParams("kurstillfalleUID", query.getKurstillfalleUID(), client)
				.queryParam("kanRegistreraPaPeriod" ,query.getKanRegistreraPaPeriod())
				.queryParam("page", query.getPage())
				.queryParam("limit", query.getLimit())
				.queryParam("orderBy", query.getOrderBy());
		client = addQueryParams("deltagaretillstand", query.getDeltagareTillstands(), client);
		log.info("Query URL: " + client.getUri());
		Response response =  client
			.request()
			.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
			.accept(responseType)
			.get();
		
		return validatedResponse(response, SokresultatDeltagare.class);
	}

	@Override
	public SokresultatDeltagare sokDeltagareKurspaketeringstillfalle(SokDeltagareKurspaketeringstillfalleQuery query) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_DELTAGARE).path(RESOURCE_KURSPAKETERINGSTILLFALLE).path(query.getKurspaketeringstillfalleUID())
				.queryParam("page", query.getPage())
				.queryParam("limit", query.getLimit())
				.queryParam("orderBy", query.getOrderBy());
		client = addQueryParams("deltagaretillstand", query.getDeltagareTillstands(), client);
		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatDeltagare.class);
	}
	
	@Override
	public void registreraStudentPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		
		WebTarget client = getClient()
				.path(RESOURCE_REGISTRERING)
				.path(RESOURCE_KURSTILLFALLESANTAGNING)
				.path(kurstillfallesantagningUid)
				.path(RESOURCE_PERIODINDEX)
				.path(periodIndex);
		
		log.info("Query URL: " + client.getUri());
		
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(null, ClientUtil.CONTENT_TYPE_HEADER_VALUE));
		
		validatedResponse(response, String.class);
	}

	@Override
	public void taBortStudentregistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_REGISTRERING)
				.path(RESOURCE_TA_BORT)
				.path(RESOURCE_KURSTILLFALLESANTAGNING)
				.path(kurstillfallesantagningUid)
				.path(RESOURCE_PERIODINDEX)
				.path(periodIndex);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.delete();

		validatedResponse(response, String.class);
	}


	@Override
	public Hinderlista hamtaHinderMotStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_REGISTRERING)
				.path(RESOURCE_HINDER)
				.path(RESOURCE_KURSTILLFALLESANTAGNING)
				.path(kurstillfallesantagningUid)
				.path(RESOURCE_PERIODINDEX)
				.path(periodIndex);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Hinderlista.class);
	}

	@Override
	public Atgard hamtaAtgardStudentRegistreringPaKurstillfalle(String kurstillfallesantagningUid, String periodIndex) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_REGISTRERING)
				.path(RESOURCE_ATGARD)
				.path(RESOURCE_KURSTILLFALLESANTAGNING)
				.path(kurstillfallesantagningUid)
				.path(RESOURCE_PERIODINDEX)
				.path(periodIndex);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Atgard.class);
	}

	@Override
	public BehorighetsvillkorLista hamtaBehorighetsVillkorForTillfallesantagning(String tillfallesantagningUid) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_BEHORIGHETSVILLKOR)
				.path(RESOURCE_TILLFALLESANTAGNING)
				.path(tillfallesantagningUid);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, BehorighetsvillkorLista.class);
	}

	@Override
	public TillfallesdeltagandeLista hamtaAllaKurstillfallesdeltagandenForStudent(String studentUid){
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_TILLFALLESDELTAGANDE)
				.path(RESOURCE_KURSTILLFALLESDELTAGANDE)
				.path(RESOURCE_STUDENT)
				.path(studentUid);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, TillfallesdeltagandeLista.class);
	}

	@Override
	public Tillfallesdeltagande hamtaKurspaketeringstillfallesdeltagandeForStudiestrukturreferens(String studiestrukturreferens){
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_TILLFALLESDELTAGANDE)
				.path(RESOURCE_STUDIESTRUKTURREFERENS)
				.path(studiestrukturreferens);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Tillfallesdeltagande.class);
	}

	@Override
	public IngaendeKurspaketeringstillfalleLista hamtaIngaendeKurspaketeringMedBarn(String studiestrukturreferens) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_STUDIESTRUKTUR)
				.path(RESOURCE_STUDIESTRUKTURREFERENS)
				.path(studiestrukturreferens);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, IngaendeKurspaketeringstillfalleLista.class);
	}

	@Override
	public IngaendeKurspaketeringstillfalleLista hamtaStudiestrukturerForStudent(String studentuid) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;

		WebTarget client = getClient()
				.path(RESOURCE_STUDIESTRUKTUR)
				.path(RESOURCE_STUDENT)
				.path(studentuid);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, IngaendeKurspaketeringstillfalleLista.class);
	}

	@Override
	public GruppLista hamtaGrupperForUtbildning(String utbildninguid) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;


		WebTarget client = getClient()
				.path(RESOURCE_GRUPP)
				.path(RESOURCE_UTBILDNING)
				.path(utbildninguid);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, GruppLista.class);
	}

	@Override
	public ServiceIndex hamtaIndex() {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
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

}
