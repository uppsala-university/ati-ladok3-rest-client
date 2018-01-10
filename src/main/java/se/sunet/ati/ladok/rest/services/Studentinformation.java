package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.studentinformation.AvskiljandebeslutLista;
import se.ladok.schemas.studentinformation.Avstangningar;

public interface Studentinformation extends LadokServiceProperties {

	AvskiljandebeslutLista hamtaAvskiljandebeslutGivetStudent(String studentUID);

	Avstangningar hamtaAvstangningarGivetStudent(String studentUID);
}
