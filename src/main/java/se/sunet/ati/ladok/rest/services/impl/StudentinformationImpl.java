package se.sunet.ati.ladok.rest.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import se.ladok.schemas.studentinformation.AvskiljandebeslutLista;
import se.ladok.schemas.studentinformation.Avstangningar;
import se.ladok.schemas.studentinformation.Kontaktuppgifter;
import se.ladok.schemas.studentinformation.Student;
import se.sunet.ati.ladok.rest.services.Studentinformation;
import se.sunet.ati.ladok.rest.util.ClientUtil;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static se.sunet.ati.ladok.rest.services.impl.ResponseFactory.validatedResponse;

public class StudentinformationImpl extends LadokServicePropertiesImpl implements Studentinformation {
	private static Log log = LogFactory.getLog(StudentinformationImpl.class);


	private static final String STUDENT_URL = "studentinformation";
	private static final String RESOURCE_AVSKILJANDEBESLUT = "avskiljandebeslut";
	private static final String RESOURCE_AVSTANGNING = "avstangning";
	private static final String RESOURCE_KONTAKTUPPGIFTER = "kontaktuppgifter";
	private static final String RESOURCE_STUDENT = "student";

	private static final String STUDENT_RESPONSE_TYPE = "application/vnd.ladok-studentinformation";
	private static final String STUDENT_MEDIATYPE = "xml;charset=UTF-8";
	private static final String responseType = STUDENT_RESPONSE_TYPE + "+" + STUDENT_MEDIATYPE;


	WebTarget resultat;

	WebTarget getClient() {
		if (this.resultat == null) {
			this.resultat = ClientUtil.newClient(this, STUDENT_URL);
		}
		return this.resultat;
	}

	@Override
	public AvskiljandebeslutLista hamtaAvskiljandebeslutGivetStudent(String studentUID) {
		WebTarget client = getClient().path(RESOURCE_AVSKILJANDEBESLUT)
				.path(RESOURCE_STUDENT)
				.path(studentUID);

		log.info("Query URL: " + client.getUri());
		
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, AvskiljandebeslutLista.class);
	}

	@Override
	public Avstangningar hamtaAvstangningarGivetStudent(String studentUID) {
		WebTarget client = getClient().path(RESOURCE_AVSTANGNING)
				.path(RESOURCE_STUDENT)
				.path(studentUID);

		log.info("Query URL: " + client.getUri());
		
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Avstangningar.class);
	}

	@Override
	public Student hamtaStudent(String studentUID) {
		WebTarget client = getClient()
				.path(RESOURCE_STUDENT)
				.path(studentUID);

		log.info("Query URL: " + client.getUri());
		
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Student.class);
	}
	
	@Override
	public Kontaktuppgifter hamtaKontaktuppgifterGivetStudent(String studentUID) {
		WebTarget client = getClient().path(RESOURCE_STUDENT).path(studentUID)
				.path(RESOURCE_KONTAKTUPPGIFTER);
		
		log.info("Query URL: " + client.getUri());
		Response response = client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get();

		return validatedResponse(response, Kontaktuppgifter.class);
	}
}
