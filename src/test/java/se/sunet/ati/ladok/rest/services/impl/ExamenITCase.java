package se.sunet.ati.ladok.rest.services.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import se.ladok.schemas.examen.Precisering;
import se.ladok.schemas.examen.PreciseringLista;
import se.sunet.ati.ladok.rest.services.Examen;

public class ExamenITCase {
	
	private static Examen examen;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		examen = new ExamenImpl();
	}
	
	@Test
	public void testPreciseringHuvudomrade() {	
		final String preciseringstyp = "huvudomrade";
		PreciseringLista listaPrecisering = examen.listaPrecisering(preciseringstyp);
	
		assertNotNull(listaPrecisering);
		assertNotNull(listaPrecisering.getPrecisering());
		assertFalse(listaPrecisering.getPrecisering().isEmpty());
		for (Precisering p : listaPrecisering.getPrecisering()) {
			//System.out.println(p);
			assertNotNull(p);
		}
	}
}
