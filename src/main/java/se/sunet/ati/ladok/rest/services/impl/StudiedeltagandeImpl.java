package se.sunet.ati.ladok.rest.services.impl;

import javax.ws.rs.client.WebTarget;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.ladok.schemas.Student;
import se.ladok.schemas.studiedeltagande.TillfallesdeltagandeLista;
import se.sunet.ati.ladok.rest.services.Studiedeltagande;
import se.sunet.ati.ladok.rest.util.ClientUtil;

public class StudiedeltagandeImpl extends LadokServicePropertiesImpl implements Studiedeltagande {
	
	private static Log log = LogFactory.getLog(StudiedeltagandeImpl.class);

    private static final String STUDIEDELTAGANDE_URL = "/studiedeltagande";
	private static final String STUDIEDELTAGANDE_RESPONSE_TYPE = "application/vnd.ladok-studiedeltagande";
	private static final String STUDIEDELTAGANDE_MEDIATYPE = "xml;charset=UTF-8";
	private static final String RESOURCE_KURS = "kurs";
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
    public Student hamtaStudentViaPersonnummer(String personnummer) {
    	String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
    	WebTarget client = getClient().path(RESOURCE_STUDENT)
    			.path(RESOURCE_STUDENT_CRITERIA)
    			.path(personnummer);
    	log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
    	return client
    			.request()
    			.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
    			.accept(responseType)
    			.get(Student.class);
    }

	@Override
	public TillfallesdeltagandeLista hamtaPaborjadeKurser(String studentUid) {
		String responseType = STUDIEDELTAGANDE_RESPONSE_TYPE + "+" + STUDIEDELTAGANDE_MEDIATYPE;
		WebTarget client = getClient().path(RESOURCE_PABORJAD_UTBILDNING)
				.path(RESOURCE_KURS).path(RESOURCE_STUDENT)
				.path(studentUid);
		log.info("Query URL: " + client.getUri() + ", response type: " + responseType);
		return client
				.request()
				.header(ClientUtil.CONTENT_TYPE_HEADER_NAME, ClientUtil.CONTENT_TYPE_HEADER_VALUE)
				.accept(responseType)
				.get(TillfallesdeltagandeLista.class);
	}
}
