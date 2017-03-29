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
import se.ladok.schemas.resultat.Studieresultat;
import se.ladok.schemas.resultat.Utbildningsinstans;
import se.ladok.schemas.LadokException;
import se.ladok.schemas.resultat.Klarmarkera;
import se.ladok.schemas.resultat.ObjectFactory;
import se.sunet.ati.ladok.rest.services.LadokRestClientException;
import se.sunet.ati.ladok.rest.services.Resultatinformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

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
	private static final String MODULER = "moduler";
	private static final String RESOURCE_UTBILDNING = "utbildning";
	private static final String RESOURCE_KLARMARKERA = "klarmarkera";
	private static final String responseType = RESULTAT_RESPONSE_TYPE + "+" + RESULTAT_MEDIATYPE;

	WebTarget resultat;

	WebTarget getClient() {
		if (this.resultat == null) {
			this.resultat = ClientUtil.newClient(this, RESULTAT_URL);
		}
		return this.resultat;
	}

	@Override
	public Studieresultat hmtaStudieResultatForStudentPaUtbildningstillfalle(String studentUID, 
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
	public ResultatLista hmtaStudieResultatForStudent(String studentUID) {
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
	public Utbildningsinstans hmtaModulerForUtbildningsinstans(String kursinstansUID) {
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

	private <T> T validatedResponse(Response response, Class<T> responseType) {
		if (response.getStatus()/100 == 2) {
			return response.readEntity(responseType);
		} else {
			throw new LadokRestClientException(response.getStatus(), response.readEntity(LadokException.class));
		}
	}
}