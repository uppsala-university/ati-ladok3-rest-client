package se.sunet.ati.ladok.rest.services.impl;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.resultat.Resultat;
import se.ladok.schemas.resultat.ResultatLista;
import se.ladok.schemas.resultat.SkapaResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfalleResultat;
import se.ladok.schemas.resultat.SokresultatAktivitetstillfallesmojlighetResultat;
import se.ladok.schemas.resultat.Studielokaliseringar;
import se.ladok.schemas.resultat.Studieresultat;
import se.ladok.schemas.resultat.Utbildningsinstans;
import se.ladok.schemas.resultat.Aktivitetstillfalle;
import se.ladok.schemas.resultat.Aktivitetstillfallestyp;
import se.ladok.schemas.resultat.Klarmarkera;
import se.ladok.schemas.resultat.ObjectFactory;
import se.ladok.schemas.resultat.SokresultatStudieresultatResultat;
import se.sunet.ati.ladok.rest.services.Resultatinformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;

public class ResultatinformationImpl extends LadokServicePropertiesImpl implements Resultatinformation {
	private static Log log = LogFactory.getLog(ResultatinformationImpl.class);

	private static final String RESULTAT_URL = "/resultat";
	private static final String RESULTAT_RESPONSE_TYPE = "application/vnd.ladok-resultat";
	private static final String RESULTAT_MEDIATYPE = "xml;charset=UTF-8";
	private static final String RESOURCE_STUDIERESULTAT = "studieresultat";
	private static final String RESOURCE_RESULTAT= "resultat";
	private static final String RESOURCE_STUDENT_CRITERIA = "student";
	private static final String RESOURCE_KURS = "utbildningstillfalle";
	private static final String RESOURCE_UTBILDNINGSINSTANS = "utbildningsinstans";
	private static final String RESOURCE_RAPPORTERA = "rapportera";
	private static final String MODULER = "moduler";
	private static final String RESOURCE_UTBILDNING = "utbildning";
	private static final String RESOURCE_KLARMARKERA = "klarmarkera";
	private static final String RESOURCE_AKTIVITETSTILLFALLE = "aktivitetstillfalle";
	private static final String RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET = "aktivitetstillfallesmojlighet";
	private static final String RESOURCE_GRUNDDATA = "grunddata";
	private static final String RESOURCE_AKTIVITETSTILLFALLESTYP = "aktivitetstillfallestyp";
	private static final String RESOURCE_FILTRERA ="filtrera";
	private static final String RESOURCE_STUDIELOKALISERING = "studielokalisering";
	
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

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
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

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
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

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		System.out.println("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Utbildningsinstans.class);
	}

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

	
	/**
	 * utbildningsinstansUID - uid for a modul gives results on that modul; uid for a kursinstans gives results on the course 
	 * filtrering 
	 */
	public SokresultatStudieresultatResultat sokStudieResultat(String utbildningsinstansUID,
			String[] kurstillfalleUIDs,
			String filtrering,
			String grupp,
			int page,
			int limit,
			String orderby) {
		WebTarget client = getClient().path(RESOURCE_STUDIERESULTAT)
				.path(RESOURCE_RAPPORTERA)
				.path(RESOURCE_UTBILDNINGSINSTANS)
				.path(utbildningsinstansUID)
				//.queryParam("filtrering", "KLARMARKERADE", "OBEHANDLADE_UTKAST_KLARMARKERADE", "OBEHANDLADE", "UTKAST", "OBEHANDLADE_UTKAST")
				.queryParam("filtrering", filtrering)
				.queryParam("grupp", grupp)
				.queryParam("page", page)
				.queryParam("limit", limit)
				.queryParam("orderby", orderby);


		for (String kurstillfalleUID : kurstillfalleUIDs) {
			client = client.queryParam("kurstillfallen", kurstillfalleUID);
		}
		log.info("Query URL : " + client.getUri());

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

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		
		Response response = 
				client.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		
		return validatedResponse(response,Aktivitetstillfalle.class);
	}

	@Override
	public Aktivitetstillfallestyp hamtaAktivitetstillfallestyp(int aktivitetstillfalleUID) {
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_GRUNDDATA)
				.path(RESOURCE_AKTIVITETSTILLFALLESTYP)
				.path(Integer.toString(aktivitetstillfalleUID));

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(); 
				
		return validatedResponse(response, Aktivitetstillfallestyp.class);
	}

	@Override
	public SokresultatAktivitetstillfalleResultat sokAktivitetstillfallen(
			String aktivitetstillfallestypID,
			String benamning, 
			String kurstillfalleUID, 
			String kurstillfalleskod, 
			String aktivitetUID, 
			String kurskod,
			String organisation, 
			String datumperiod, 
			String orderby, 
			boolean skipCount, 
			boolean onlyCount,
			String sprakkod, 
			int page, 
			int limit) {
	
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLE)
				.path(RESOURCE_FILTRERA)
				.queryParam("aktivitetstillfallestypID", aktivitetstillfallestypID)
				.queryParam("benamning",benamning) 
				.queryParam("kurstillfalleUID",kurstillfalleUID) 
				.queryParam("kurstillfalleskod",kurstillfalleskod) 
				.queryParam("aktivitetUID",aktivitetUID) 
				.queryParam("kurskod",kurskod)
				.queryParam("organisation",organisation) 
				.queryParam("datumperiod",datumperiod) 
				.queryParam("orderby",orderby) 
				.queryParam("skipCount",skipCount) 
				.queryParam("onlyCount",onlyCount)
				.queryParam("sprakkod",sprakkod)
				.queryParam("limit",limit)
				.queryParam("page",page);

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(SokresultatAktivitetstillfalleResultat.class);
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
		
		String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;
		WebTarget client = getClient()
				.path(RESOURCE_AKTIVITETSTILLFALLESMOJLIGHET)
				.path(RESOURCE_FILTRERA)
				.queryParam("aktivitetstillfalleUID",aktivitetstillfalleUID)
				.queryParam("anmalda",anmalda)
				.queryParam("personnummer",personnummer)
				.queryParam("fornamn",fornamn)
				.queryParam("efternamn",efternamn)
				.queryParam("page",page)
				.queryParam("limit",limit)
				.queryParam("skipCount",skipCount)
				.queryParam("onlyCount",onlyCount)
				.queryParam("sprakkod",sprakkod);
		
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
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

		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		Response response =  client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();
		
		return validatedResponse(response, Studielokaliseringar.class);
	}

}