package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import se.ladok.schemas.Hinderlista;
import se.ladok.schemas.Identiteter;
import se.ladok.schemas.Organisationslista;
import se.ladok.schemas.dap.ServiceIndex;
import se.ladok.schemas.resultat.*;
import se.ladok.schemas.resultat.UtdataAvgransningarLista.UtdataAvgransningar;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokAktivitetstillfalleQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokAktivitetstillfallesmojlighetQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokResultatKurstillfallesdeltagareQuery;
import se.sunet.ati.ladok.rest.api.resultatinformation.SokResultatResultatUppfoljningQuery;
import se.sunet.ati.ladok.rest.services.Resultatinformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static se.ladok.schemas.resultat.UtdataAvgransningstyp.HAR_GODKAND_KURS;
import static se.ladok.schemas.resultat.UtdataAvgransningstyp.HAR_GODKANT_RESULTAT_PA_MODUL;
import static se.ladok.schemas.resultat.UtdataAvgransningstyp.KURSUTBILDNINGUIDER;
import static se.ladok.schemas.resultat.UtdataAvgransningstyp.MODULUTBILDNINGUIDER;
import static se.ladok.schemas.resultat.UtdataAvgransningstyp.RESULTAT_INOM;
import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;
import static se.sunet.ati.ladok.rest.util.ClientUtil.addQueryParams;

public class ResultatinformationImpl extends LadokServicePropertiesImpl implements Resultatinformation {
	

	private static Log log = LogFactory.getLog(ResultatinformationImpl.class);

	private static final String RESULTAT_URL = "/resultat";
	private static final String RESULTAT_RESPONSE_TYPE = "application/vnd.ladok-resultat";
	private static final String RESULTAT_MEDIATYPE = "xml;charset=UTF-8";
	private static final String RESOURCE_STUDIERESULTAT = "studieresultat";
	private static final String RESOURCE_STUDENTRESULTAT = "studentresultat";
	private static final String RESOURCE_RESULTAT= "resultat";
	private static final String RESOURCE_STUDENT_CRITERIA = "student";
	private static final String RESOURCE_KURS = "utbildningstillfalle";
	private static final String RESOURCE_UTBILDNINGSINSTANS = "utbildningsinstans";
	private static final String RESOURCE_RAPPORTERA = "rapportera";
	private static final String RESOURCE_ATTESTERA = "attestera";
	private static final String RESOURCE_ATTESTERADE = "attesterade";
	private static final String MODULER = "moduler";
	private static final String RESOURCE_UTBILDNING = "utbildning";
	private static final String RESOURCE_KLARMARKERA = "klarmarkera";
	private static final String RESOURCE_AKTIVITETSTILLFALLE = "aktivitetstillfalle";
	private static final String RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET = "aktivitetstillfallesmojlighet";
	private static final String RESOURCE_GRUNDDATA = "grunddata";
	private static final String RESOURCE_AKTIVITETSTILLFALLESTYP = "aktivitetstillfallestyp";
	private static final String RESOURCE_FILTRERA ="filtrera";
	private static final String RESOURCE_STUDIELOKALISERING = "studielokalisering";
	private static final String RESOURCE_ORGANISATION = "organisation";
	private static final String RESOURCE_AKTIVITETSTILLFALLESTYPER = "aktivitetstillfallestyper";
	private static final String RESOURCE_STALLIN = "stallin";
	private static final String RESOURCE_AKTIVERA = "aktivera";
	private static final String RESOURCE_KURSTILLFALLE = "kurstillfalle";
	private static final String RESOURCE_PERIOD = "period";
	private static final String RESOURCE_RESULTATUPPFOLJNING = "resultatuppfoljning";
	private static final String RESOURCE_KURSINSTANS = "kursinstans";
	private static final String RESOURCE_AVANMAL = "avanmal";
	private static final String RESOURCE_ANMAL = "anmal";
	private static final String RESOURCE_STUDENTIDENTITETER = "studentidentiteter";
	private static final String RESOURCE_SKAPA="skapa";
	private static final String RESOURCE_UPPDATERA = "uppdatera";
	private static final String RESOURCE_HINDER = "hinder";
	private static final String RESOURCE_KURSTILLFALLESDELTAGARE = "kurstillfallesdeltagare";
	private static final String RESOURCE_SOK = "sok";
	private static final String RESOURSE_SERVICE = "service";
	private static final String RESOURSE_INDEX = "index";
	private static final String RESOURCE_UTDATA = "utdata";

	private static final String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
	public static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
	public static final String CONTENT_TYPE_HEADER_VALUE = "application/vnd.ladok+xml";


	WebTarget resultat;

	WebTarget getClient() {
		if (this.resultat == null) {
			this.resultat = ClientUtil.newClient(this, RESULTAT_URL);
		}
		return this.resultat;
	}

	@Override
	public Studieresultat hamtaStudieResultatForStudentPaUtbildningstillfalle(String studentUID,
	                                                                          String kurstillfalleUID) {
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT)
				.path(RESOURCE_STUDENT_CRITERIA)
				.path(studentUID)
				.path(RESOURCE_KURS)
				.path(kurstillfalleUID);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Studieresultat.class);
	}

	@Override
	public ResultatLista hamtaStudieResultatForStudent(String studentUID) {
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT)
				.path(RESOURCE_RESULTAT)
				.path(RESOURCE_STUDENT_CRITERIA)
				.path(studentUID);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, ResultatLista.class);
	}

	@Override
	public Utbildningsinstans hamtaModulerForUtbildningsinstans(String kursinstansUID) {
		WebTarget client = getClient().path(RESOURCE_UTBILDNINGSINSTANS)
				.path(kursinstansUID)
				.path(MODULER);

		log.info("Query URL: " + client.getUri());
		System.out.println("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningsinstans.class);
	}

	@Deprecated
	@Override
	public Resultat skapaResultatForStudentPakurs(SkapaResultat resultat, String studieresultatUID, String utbildningUID) {
		JAXBElement<SkapaResultat> resultatJAXBElement = new ObjectFactory().createSkapaResultat(resultat);
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT).path(studieresultatUID).path(RESOURCE_UTBILDNING).path(utbildningUID).path(RESOURCE_RESULTAT);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Resultat.class);

	}

	@Override
	public ResultatLista skapaResultatForStudentPaUtbildningsinstans(SkapaFlera resultat) {
		JAXBElement<SkapaFlera> resultatJAXBElement = new ObjectFactory().createSkapaFlera(resultat);
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT).path(RESOURCE_SKAPA);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, ResultatLista.class);
	}

	@Deprecated
	@Override
	public Resultat updateraResultatForStudentPakurs(Resultat resultat, String resultatUID) {
		JAXBElement<Resultat> resultatJAXBElement = new ObjectFactory().createResultat(resultat);
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT).path(RESOURCE_RESULTAT).path(resultatUID);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Resultat.class);

	}

	@Override
	public ResultatLista uppdateraResultatForStudentPaUtbildningsinstans(UppdateraFlera resultat) {
		JAXBElement<UppdateraFlera> resultatJAXBElement = new ObjectFactory().createUppdateraFlera(resultat);
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT).path(RESOURCE_UPPDATERA);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, ResultatLista.class);
	}

	@Override
	public Resultat klarmarkeraResultatForStudentPakurs(Klarmarkera klarmarkera, String resultatUID) {
		JAXBElement<Klarmarkera> resultatJAXBElement = new ObjectFactory().createKlarmarkera(klarmarkera);
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT).path(RESOURCE_RESULTAT).path(resultatUID).path(RESOURCE_KLARMARKERA);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Resultat.class);
	}

	@Override
	public ResultatLista  klarmarkeraResultatForStudentPakurs(KlarmarkeraFlera resultat) {
		JAXBElement<KlarmarkeraFlera> resultatJAXBElement = new ObjectFactory().createKlarmarkeraFlera(resultat);
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT).path(RESOURCE_KLARMARKERA);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, ResultatLista.class);
	}

	/**
	 * utbildningsinstansUID - uid for a modul gives results on that modul; uid for a kursinstans gives results on the course 
	 *
	 */
	@Override
	public SokresultatStudieresultatResultat sokStudieResultat(String utbildningsinstansUID,
	                                                           String[] kurstillfalleUIDs,
	                                                           String filtrering,
	                                                           String grupp,
	                                                           int page,
	                                                           int limit,
	                                                           String orderby) {
		StudieresultatForRapporteringSokVarden studieresultatForRapporteringSokVarden = new StudieresultatForRapporteringSokVarden();
		studieresultatForRapporteringSokVarden.setUtbildningsinstansUID(utbildningsinstansUID);
		if (filtrering != null) {
			studieresultatForRapporteringSokVarden.getFiltrering().add(StudieresultatTillstandVidRapporteringEnum.fromValue(filtrering));
		}
		if (kurstillfalleUIDs != null) {
			studieresultatForRapporteringSokVarden.getKurstillfallenUID().addAll(Arrays.asList(kurstillfalleUIDs));
		}
		studieresultatForRapporteringSokVarden.setGruppUID(grupp);
		studieresultatForRapporteringSokVarden.setPage(page);
		studieresultatForRapporteringSokVarden.setLimit(limit);
		if (orderby != null) {
			studieresultatForRapporteringSokVarden.getOrderBy().add(StudieresultatOrderByEnum.fromValue(orderby));

		}
		return sokStudieResultat(studieresultatForRapporteringSokVarden, utbildningsinstansUID);
	}

	@Override
	public SokresultatStudieresultatResultat sokStudieResultat(StudieresultatForRapporteringSokVarden studieresultatForRapporteringSokVarden, String utbildningsinstansUID) {
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT)
				.path(RESOURCE_RAPPORTERA)
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path(utbildningsinstansUID)
				.path("sok");
		log.info("Query URL: " + client.getUri());

		JAXBElement<StudieresultatForRapporteringSokVarden> sokVardenJAXBElement = new ObjectFactory().createStudieresultatForRapporteringSokVarden(studieresultatForRapporteringSokVarden);

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(sokVardenJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, SokresultatStudieresultatResultat.class);
	}

	/**
	 * utbildningsinstansUID - uid for a modul gives results on that modul; uid for a kursinstans gives results on the course 
	 *
	 */
	@Override
	public SokresultatStudieresultatResultat sokAttesteradeStudieResultat(
			String utbildningsinstansUID,
			String[] kurstillfalleUIDs,
			String filtrering,
			String grupp,
			int page,
			int limit,
			String orderby) {
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT)
				.path(RESOURCE_ATTESTERA)
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path(utbildningsinstansUID)
				.queryParam("filtrering", filtrering)
				.queryParam("grupp", grupp)
				.queryParam("page", page)
				.queryParam("limit", limit)
				.queryParam("orderby", orderby);

		for (String kurstillfalleUID : kurstillfalleUIDs) {
			client = client.queryParam("kurstillfallen", kurstillfalleUID);
		}
		log.info("Query URL: " + client.getUri());

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatStudieresultatResultat.class);
	}

	@Override
	public Aktivitetstillfalle hamtaAktivitetstillfalle(String aktivitetstillfalleUID) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(aktivitetstillfalleUID);

		log.info("Query URL: " + client.getUri());

		Response response =
				client.request()
						.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
						.accept(responseType)
						.get();

		return validatedResponse(response, Aktivitetstillfalle.class);
	}

	@Override
	public void taBortAktivitetstillfalle(String aktivitesttillfalleUID) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(aktivitesttillfalleUID);

		log.info("Query URL: " + client.getUri());

		Response response =
				client.request()
						.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
						.accept(responseType)
						.delete();

		validatedResponse(response,  String.class);
	}

	@Override
	public Aktivitetstillfalle skapaAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle) {
		JAXBElement<Aktivitetstillfalle> resultatJAXBElement = new ObjectFactory().createAktivitetstillfalle(aktivitetstillfalle);

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE);

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Aktivitetstillfalle.class);
	}

	@Override
	public Aktivitetstillfalle updateraAktivitetstillfalle(Aktivitetstillfalle aktivitetstillfalle) {
		JAXBElement<Aktivitetstillfalle> resultatJAXBElement = new ObjectFactory().createAktivitetstillfalle(aktivitetstillfalle);

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(aktivitetstillfalle.getUid());

		log.info("Query URL: " + client.getUri());

		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Aktivitetstillfalle.class);
	}

	@Override
	public Aktivitetstillfalle stallInAktivitetstillfalle(String aktivitetstillfalleUid) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(aktivitetstillfalleUid)
				.path(RESOURCE_STALLIN);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Aktivitetstillfalle.class);

	}

	@Override
	public Aktivitetstillfalle aktiveraAktivitetstillfalle(String aktivitetstillfalleUid) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(aktivitetstillfalleUid)
				.path(RESOURCE_AKTIVERA);


		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity("", ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Aktivitetstillfalle.class);

	}

	@Override
	public Aktivitetstillfallestyp hamtaAktivitetstillfallestyp(int aktivitetstillfalleUID) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_AKTIVITETSTILLFALLESTYP)
				.path(Integer.toString(aktivitetstillfalleUID));

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Aktivitetstillfallestyp.class);
	}

	@Override
	public Aktivitetstillfallestyper listaAktivitetstillfallestyper() {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_AKTIVITETSTILLFALLESTYPER);

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Aktivitetstillfallestyper.class);
	}

	@Override
	public SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(SokAktivitetstillfalleQuery query) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(RESOURCE_FILTRERA)
				.queryParam("benamning", query.getBenamning());
		client = addQueryParams("aktivitetstillfallestypID", query.getAktivitetstillfallestypID(), client);
		client = addQueryParams("kurstillfalleUID", query.getKurstillfalleUID(), client);
		client = addQueryParams("kurstillfalleskod", query.getKurstillfalleskod(), client);
		client = addQueryParams("aktivitetUID", query.getAktivitetUID(), client);
		client = addQueryParams("kurskod", query.getKurskod(), client);
		client = addQueryParams("organisation", query.getOrganisation(), client);
		client = client.queryParam("datumperiod",query.getDatumperiod())
				.queryParam("orderby",query.getOrderby())
				.queryParam("skipCount",query.getSkipCount())
				.queryParam("onlyCount",query.getOnlyCount())
				.queryParam("sprakkod", query.getSprakkod())
				.queryParam("limit", query.getLimit())
				.queryParam("page",query.getPage());

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatAktivitetstillfalleResultat.class);
	}

	@Override
	@Deprecated
	public SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(
			String aktivitetstillfallestypID,
			String benamning,
			String[] kurstillfalleUID,
			String[] kurstillfalleskod,
			String[] aktivitetUID,
			String[] kurskod,
			String[] organisation,
			String datumperiod,
			String orderby,
			boolean skipCount,
			boolean onlyCount,
			String sprakkod,
			int page,
			int limit) {
		return sokAktivitetstillfallen(SokAktivitetstillfalleQuery.builder()
				.addAktivitetstillfallestypID(Integer.valueOf(aktivitetstillfallestypID))
				.benamning(benamning)
				.addKurstillfalleUIDList(Arrays.asList(kurstillfalleUID))
				.addKurstillfalleskodList(Arrays.asList(kurstillfalleskod))
				.addAktivitetUIDList(Arrays.asList(aktivitetUID))
				.addKurskodList(Arrays.asList(kurskod))
				.addOrganisationList(Arrays.asList(organisation))
				.datumperiod(datumperiod)
				.orderby(orderby)
				.skipCount(skipCount)
				.onlyCount(onlyCount)
				.sprakkod(sprakkod)
				.page(page)
				.limit(limit)
				.build());
	}

	@Override
	public SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter(
			String aktivitetstillfalleUID,
			boolean anmalda,
			String personnummer,
			String fornamn,
			String efternamn,
			boolean skipCount,
			boolean onlyCount,
			String sprakkod,
			int page,
			int limit) {
		return sokAktivitetstillfallesmojligheter(SokAktivitetstillfallesmojlighetQuery.builder()
				.aktivitetstillfalleUID(aktivitetstillfalleUID)
				.anmalda(anmalda)
				.personnummer(personnummer)
				.fornamn(fornamn)
				.efternamn(efternamn)
				.skipCount(skipCount)
				.onlyCount(onlyCount)
				.sprakkod(sprakkod)
				.page(page)
				.limit(limit)
				.build());
	}
	@Override
	public SokresultatAktivitetstillfallesmojlighetResultat sokAktivitetstillfallesmojligheter(SokAktivitetstillfallesmojlighetQuery sokAktivitetstillfallesmojlighetQuery) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(RESOURCE_FILTRERA);
		log.info("Query URL: " + client.getUri());

		client= client.queryParam("aktivitetstillfalleUID",sokAktivitetstillfallesmojlighetQuery.getAktivitetstillfalleUID());
		client= client.queryParam("anmalda",sokAktivitetstillfallesmojlighetQuery.getAnmalda());
		client= client.queryParam("personnummer",sokAktivitetstillfallesmojlighetQuery.getPersonnummer());
		client= client.queryParam("fornamn",sokAktivitetstillfallesmojlighetQuery.getFornamn());
		client= client.queryParam("efternamn",sokAktivitetstillfallesmojlighetQuery.getEfternamn());
		client= client.queryParam("skipCount",sokAktivitetstillfallesmojlighetQuery.getSkipCount());
		client= client.queryParam("onlyCount",sokAktivitetstillfallesmojlighetQuery.getOnlyCount());
		client= client.queryParam("sprakkod",sokAktivitetstillfallesmojlighetQuery.getSprakkod());
		client= client.queryParam("limit",sokAktivitetstillfallesmojlighetQuery.getLimit());
		client= client.queryParam("page",sokAktivitetstillfallesmojlighetQuery.getPage());

		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response,SokresultatAktivitetstillfallesmojlighetResultat.class);
	}
	@Override
	public Studielokaliseringar sokAllaStudielokaliseringar() {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_STUDIELOKALISERING);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Studielokaliseringar.class);
	}

	@Override
	public Organisationslista listaOrganisatoriskaDelar() {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_ORGANISATION);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Organisationslista.class);
	}

	@Override
	public SokresultatKurstillfalleResultat sokresultatKurstillfalle(
			String benamning,
			String kurskod,
			String tillfalleskod,
			String[] organisationer,
			String datumperiod,
			String utbildningstypID,
			String utbildningsgrundtypID,
			boolean skipCount,
			boolean onlyCount,
			int limit,
			int page,
			String sprakkod) {

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_KURSTILLFALLE)
				.path(RESOURCE_FILTRERA)
				.queryParam("benamning",benamning)
				.queryParam("kurskod",kurskod)
				.queryParam("tillfalleskod",tillfalleskod)
				.queryParam("organisationer",organisationer)
				.queryParam("datumperiod",datumperiod)
				.queryParam("utbildningstypID",utbildningstypID)
				.queryParam("utbildningsgrundtypID",utbildningsgrundtypID)
				.queryParam("skipCount",skipCount)
				.queryParam("onlyCount",onlyCount)
				.queryParam("limit",limit)
				.queryParam("page",page)
				.queryParam("sprakkod",sprakkod);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatKurstillfalleResultat.class);
	}

	@Override
	public Perioder listaPerioder() {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_PERIOD);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Perioder.class);
	}

	@Override
	public SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(
			SokResultatResultatUppfoljningQuery sokResultatResultatUppfoljningQuery) {
		WebTarget client = getClient()
				.path(RESOURCE_RESULTATUPPFOLJNING)
				.path(RESOURCE_KURSINSTANS)
				.path(sokResultatResultatUppfoljningQuery.getKursinstansUID())
				.queryParam("kurstillfallen",sokResultatResultatUppfoljningQuery.getKurstillfallen().toArray(new String[sokResultatResultatUppfoljningQuery.getKurstillfallen().size()]))
				.queryParam("grupp",sokResultatResultatUppfoljningQuery.getGrupp())
				.queryParam("tillstand",sokResultatResultatUppfoljningQuery.getTillstand())
				.queryParam("page",sokResultatResultatUppfoljningQuery.getPage())
				.queryParam("limit",sokResultatResultatUppfoljningQuery.getLimit())
				.queryParam("orderby",sokResultatResultatUppfoljningQuery.getOrderby())
				.queryParam("orderBetygsgradAvserUtbildningUID",sokResultatResultatUppfoljningQuery.getOrderBetygsgradAvserUtbildningUID())
				.queryParam("orderExaminationsdatumAvserUtbildningUID",sokResultatResultatUppfoljningQuery.getOrderExaminationsdatumAvserUtbildningUID());

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatResultatuppfoljning.class);
	}

	@Override
	public SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(
			String kursinstansUID,
			String[] kurstillfallen,
			String grupp,
			String tillstand,
			int page,
			int limit,
			String orderby,
			String orderBetygsgradAvserUtbildningUID,
			String orderExaminationsdatumAvserUtbildningUID) {

		return sokStudieresultatForResultatuppfoljningAvKurs(new SokResultatResultatUppfoljningQuery.Builder()
				.kursinstansUID(kursinstansUID)
				.kurstillfallen(Arrays.asList(kurstillfallen))
				.grupp(grupp)
				.tillstand(tillstand)
				.page(page)
				.limit(limit)
				.orderby(orderby)
				.orderBetygsgradAvserUtbildningUID(orderBetygsgradAvserUtbildningUID)
				.orderExaminationsdatumAvserUtbildningUID(orderExaminationsdatumAvserUtbildningUID)
				.build()
		);
	}

	@Override
	public SokresultatResultatuppfoljning sokStudieresultatForResultatuppfoljningAvKurs(ResultatuppfoljningSokVarden resultatuppfoljningSokVarden){
		JAXBElement<ResultatuppfoljningSokVarden> resultatJAXBElement = new ObjectFactory().createResultatuppfoljningSokVarden(resultatuppfoljningSokVarden);

		
		WebTarget client = getClient()
				.path(RESOURCE_RESULTATUPPFOLJNING)
				.path(RESOURCE_RESULTATUPPFOLJNING)
				.path(RESOURCE_SOK);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, SokresultatResultatuppfoljning.class);
	}
	
	
	@Override
	public StudentresultatPerKurs sokResultatStudent(String studentUid, String kursUID){
			
		WebTarget client = getClient()
				.path(RESOURCE_STUDENTRESULTAT).path(RESOURCE_ATTESTERADE)
				.path(RESOURCE_STUDENT_CRITERIA).path(studentUid)
				.queryParam("kursUID", kursUID);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, StudentresultatPerKurs.class);
	}
	
	/**
	 * Not currently supported by Ladok
	 */
	public UtdataResultat sokResultatAvKurs(List<String> utbildninguider, 
			List<String> moduluider, 
			String startdate, 
			String enddate,
			Boolean godkandModul,
			Boolean godkandKurs){
		
		Objects.requireNonNull(startdate, "startdate is null");
		Objects.requireNonNull(enddate, "enddate is null");
		
		UtdataAvgransningar ua = new UtdataAvgransningar();
		
		if (utbildninguider != null) {
			for (String uid : utbildninguider) {	
				UtdataAvgransning utdataAvgransning = new UtdataAvgransning();
				utdataAvgransning.getUtdataAvgransningsvarden().add(uid);
				utdataAvgransning.setUtdataAvgransningstyp(KURSUTBILDNINGUIDER);
				ua.getUtdataAvgransning().add(utdataAvgransning);
			}
		}
		
		if (moduluider != null) {
			for (String uid : moduluider) {
				UtdataAvgransning utdataAvgransning = new UtdataAvgransning();
				utdataAvgransning.getUtdataAvgransningsvarden().add(uid);
				utdataAvgransning.setUtdataAvgransningstyp(MODULUTBILDNINGUIDER);
				ua.getUtdataAvgransning().add(utdataAvgransning);
			}
		}
		
		if (godkandModul != null) {
			UtdataAvgransning utdataAvgransning = new UtdataAvgransning();
			utdataAvgransning.getUtdataAvgransningsvarden().add(godkandModul.toString());
			utdataAvgransning.setUtdataAvgransningstyp(HAR_GODKANT_RESULTAT_PA_MODUL);
			ua.getUtdataAvgransning().add(utdataAvgransning);
		}
		
		if (godkandKurs != null) {
			UtdataAvgransning utdataAvgransning = new UtdataAvgransning();
			utdataAvgransning.getUtdataAvgransningsvarden().add(godkandKurs.toString());
			utdataAvgransning.setUtdataAvgransningstyp(HAR_GODKAND_KURS);
			ua.getUtdataAvgransning().add(utdataAvgransning);
		}
		
		UtdataAvgransning utdataAvgransningDate = new UtdataAvgransning();
		utdataAvgransningDate.getUtdataAvgransningsvarden().add(startdate + "_" + enddate);
		utdataAvgransningDate.setUtdataAvgransningstyp(RESULTAT_INOM);
		ua.getUtdataAvgransning().add(utdataAvgransningDate);		
			
		UtdataAvgransningarLista avg = new UtdataAvgransningarLista();
		avg.setUtdataAvgransningar(ua);
		
		Utdatafraga u = new Utdatafraga();
		u.setUtdataAvgransningar(avg);
		
		return sokResultatAvKurs(u);	
	}
	
	@Override
	public UtdataResultat sokResultatAvKurs(Utdatafraga u){
		
		JAXBElement<Utdatafraga> resultatJAXBElement = new ObjectFactory().createUtdatafraga(u);
			
		WebTarget client = getClient()
				.path(RESOURCE_UTDATA).path(RESOURCE_SOK)
				.path("resultat.domain.utdata.resultat");

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, UtdataResultat.class);
	}
	
	@Override
	public SokresultatResultatUtdata sokResultatAvKurs(SokkriterierResultatUtdata u){
		
		JAXBElement<SokkriterierResultatUtdata> resultatJAXBElement = new ObjectFactory().createSokkriterierResultatUtdata(u);
			
		WebTarget client = getClient()
				.path(RESOURCE_UTDATA).path("resultat");

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, 	SokresultatResultatUtdata.class);
	}
	
	
	@Override
	public SokresultatStudieresultatResultat sokStudieresultatForRapporteringsunderlag(
			String aktivitetstillfalleUID,
			String[] kurstillfallen,
			String filtrering,
			String filtreringAnmalda,
			String[] student,
			int page,
			int limit,
			String orderby) {

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_STUDIERESULTAT)
				.path(RESOURCE_RAPPORTERA)
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(aktivitetstillfalleUID)
				.queryParam("kurstillfallen",kurstillfallen)
				.queryParam("filtrering",filtrering)
				.queryParam("filtreringAnmalda",filtreringAnmalda)
				.queryParam("student",student)
				.queryParam("page",page)
				.queryParam("limit",limit)
				.queryParam("orderby",orderby);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatStudieresultatResultat.class);
	}

	@Override
	public Aktivitetstillfallesmojlighet avanmalStudentFranAktivitetstillfalle(
			String aktivitetstillfallesmojlighetUID, Anmalan anmalan) {

		JAXBElement<Anmalan> resultatJAXBElement = new ObjectFactory().createAnmalan(anmalan);

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(aktivitetstillfallesmojlighetUID)
				.path(RESOURCE_AVANMAL);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Aktivitetstillfallesmojlighet.class);

	}

	@Override
	public Aktivitetstillfallesmojlighet anmalStudentTillAktivitetstillfalle(
			String aktivitetstillfallesmojlighetUID, Anmalan anmalan) {

		JAXBElement<Anmalan> resultatJAXBElement = new ObjectFactory().createAnmalan(anmalan);

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(aktivitetstillfallesmojlighetUID)
				.path(RESOURCE_ANMAL);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.put(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Aktivitetstillfallesmojlighet.class);
	}

	@Override
	public void avanmalOchTaBortAktivitetstillfallesmojlighet(String aktivitetstillfallesmojlighetUID) {

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(aktivitetstillfallesmojlighetUID);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.delete();

		validatedResponse(response, String.class);
	}

	@Override
	public Aktivitetstillfallesmojlighet hamtaAktivitetstillfallesmojlighet(String aktivitetstillfallesmojlighetUID) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(aktivitetstillfallesmojlighetUID);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Aktivitetstillfallesmojlighet.class);
	}

	@Override
	public Aktivitetstillfallesmojlighet skapaAktivitetstillfallesmojlighet(
			String studieresultatUID, Aktivitetstillfalle aktivitetstillfalle) {

		JAXBElement<Aktivitetstillfalle> resultatJAXBElement = new ObjectFactory().createAktivitetstillfalle(aktivitetstillfalle);

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(RESOURCE_STUDIERESULTAT)
				.path(studieresultatUID);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.post(Entity.entity(resultatJAXBElement, ClientUtil.CONTENT_TYPE_HEADER_VALUE));

		return validatedResponse(response, Aktivitetstillfallesmojlighet.class);
	}

	@Override
	public Identiteter sokStudentidentiteter(String aktivitetstillfalleUID) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(RESOURCE_FILTRERA)
				.path(RESOURCE_STUDENTIDENTITETER)
				.queryParam("aktivitetstillfalleUID", aktivitetstillfalleUID);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Identiteter.class);
	}

	// Note: It says "utbildningUID" in the restdoc but this is wrong, it should be "utbildningsinstansUID"
	@Override
	@Deprecated
	public Hinderlista hamtaHinder(String studieresultatUID, String utbildningsinstansUID) {
		WebTarget client = getClient()
				.path(RESOURCE_STUDIERESULTAT)
				.path(studieresultatUID)
				.path(RESOURCE_UTBILDNING)
				.path(utbildningsinstansUID)
				.path(RESOURCE_RESULTAT)
				.path(RESOURCE_HINDER);

		log.info("Query URL: " + client.getUri());
		System.out.println("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Hinderlista.class);
	}

	@Override
	public Hinderlista hamtaHinderSkapa(String studieresultatUID, String utbildningsinstansUID) {
		WebTarget client = getClient()
				.path(RESOURCE_STUDIERESULTAT)
				.path(studieresultatUID)
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path(utbildningsinstansUID)
				.path(RESOURCE_RESULTAT)
				.path(RESOURCE_SKAPA)
				.path(RESOURCE_HINDER);

		log.info("Query URL: " + client.getUri());
		System.out.println("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Hinderlista.class);
	}

	@Override
	public Hinderlista hamtaHinderKlarmarkera(String studieresultatUID, String resultatUID) {
		WebTarget client = getClient()
				.path(RESOURCE_STUDIERESULTAT)
				.path(studieresultatUID)
				.path(RESOURCE_RESULTAT)
				.path(resultatUID)
				.path(RESOURCE_KLARMARKERA)
				.path(RESOURCE_HINDER);

		log.info("Query URL: " + client.getUri());
		System.out.println("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Hinderlista.class);
	}

	@Override
	public SokresultatKurstillfallesdeltagare sokresultatKurstillfallesdeltagare(
			String aktivitetstillfalleUID,
			String[] kurstillfalleUID,
			String gruppUID,
			boolean skipCount,
			boolean onlyCount,
			String sprakkod,
			int limit,
			int page,
			String orderby) {

		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_KURSTILLFALLESDELTAGARE)
				.path(RESOURCE_FILTRERA)
				.queryParam("aktivitetstillfalleUID",aktivitetstillfalleUID)
				.queryParam("kurstillfalleUID",kurstillfalleUID)
				.queryParam("gruppUID",gruppUID)
				.queryParam("skipCount",skipCount)
				.queryParam("onlyCount",onlyCount)
				.queryParam("sprakkod",sprakkod)
				.queryParam("limit",limit)
				.queryParam("page",page)
				.queryParam("orderby",orderby);

		log.info("Query URL: " + client.getUri());
		System.out.println("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, SokresultatKurstillfallesdeltagare.class);
	}

	@Override
	public SokresultatKurstillfallesdeltagare sokresultatKurstillfallesdeltagare(SokResultatKurstillfallesdeltagareQuery sokResultatKurstillfallesdeltagareQuery) {
		return sokresultatKurstillfallesdeltagare(
				sokResultatKurstillfallesdeltagareQuery.getAktivitetstillfalleUID(),
				sokResultatKurstillfallesdeltagareQuery.getKurstillfalleUIDer().toArray(new String[sokResultatKurstillfallesdeltagareQuery.getKurstillfalleUIDer().size()]),
				sokResultatKurstillfallesdeltagareQuery.getGruppUID(),
				sokResultatKurstillfallesdeltagareQuery.getSkipCount(),
				sokResultatKurstillfallesdeltagareQuery.getOnlyCount(),
				sokResultatKurstillfallesdeltagareQuery.getSprakkod(),
				sokResultatKurstillfallesdeltagareQuery.getLimit(),
				sokResultatKurstillfallesdeltagareQuery.getPage(),
				sokResultatKurstillfallesdeltagareQuery.getOrderby()
		);

	}

	@Override
	public AktivitetstillfalleForStudentLista sokAktivitetstillfalleForStudent(String studentUID, String kurstillfalleUID, Boolean endastAnmalan) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(RESOURCE_STUDENT_CRITERIA)
				.path(studentUID)
				.queryParam("kurstillfalleUID", kurstillfalleUID)
				.queryParam("endastAnmalan", endastAnmalan);

		log.info("Query URL: " + client.getUri());
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, AktivitetstillfalleForStudentLista.class);
	}

	@Override
	public ServiceIndex hamtaIndex() {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
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
