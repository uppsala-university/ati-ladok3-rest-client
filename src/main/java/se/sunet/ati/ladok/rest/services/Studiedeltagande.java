package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.Student;

public interface Studiedeltagande extends LadokServiceProperties {
	
	public Student hamtaStudentViaPersonnummer(String personnummer);

}
