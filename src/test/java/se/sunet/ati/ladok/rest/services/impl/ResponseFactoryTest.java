package se.sunet.ati.ladok.rest.services.impl;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

import java.util.Objects;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import se.ladok.schemas.LadokException;
import se.ladok.schemas.studentinformation.Student;
import se.sunet.ati.ladok.rest.services.LadokRestClientException;

public class ResponseFactoryTest {

	@Test
	public void responseOk() {
		Response response = mockResponse(200, new Student());

		Student student = ResponseFactory.validatedResponse(response, Student.class);

		assertNotNull(student);
	}

	@Test
	public void responseGenericException() {
		Response response = mockResponse(300, new IllegalStateException("FEL"));

		try {
			ResponseFactory.validatedResponse(response, Student.class);
			fail();
		} catch (LadokRestClientException e) {
			assertTrue(e.getMessage().contains("FEL"));
			assertEquals(300, e.getHttpStatusCode());
			assertNull(e.getLadokException());
		}
	}

	@Test
	public void responseLadokException() {
		LadokException ladokException = new LadokException();
		Response response = mockResponse(300, ladokException);

		try {
			ResponseFactory.validatedResponse(response, Student.class);
			fail();
		} catch (LadokRestClientException e) {
			assertTrue(e.getMessage().contains("meddelande: " + LadokException.class.getCanonicalName()));
			assertEquals(300, e.getHttpStatusCode());
			assertNotNull(e.getLadokException());
		}
	}

	private Response mockResponse(int statusCode, Object responseEntity) {
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatus()).thenReturn(statusCode);
		Mockito.when(response.readEntity(Matchers.eq(responseEntity.getClass()))).thenReturn(responseEntity);
		Mockito.when(response.readEntity(Matchers.eq(String.class))).thenReturn(Objects.toString(responseEntity));
		return response;
	}
}