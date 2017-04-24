package se.sunet.ati.ladok.rest.services.impl;

import se.ladok.schemas.LadokException;
import se.sunet.ati.ladok.rest.services.LadokRestClientException;

import javax.ws.rs.core.Response;

public class ResponseFactory {

	private ResponseFactory() {
	}

	public static <T> T validatedResponse(Response response, Class<T> responseType) {
		if (response.getStatus()/100 == 2) {
			return response.readEntity(responseType);
		} else {
			response.bufferEntity();
			String errorMessage = response.readEntity(String.class);
			LadokException ladokException = readLadokException(response);
			throw new LadokRestClientException(response.getStatus(), errorMessage, ladokException);
		}
	}

	private static LadokException readLadokException(Response response) {
		try {
			return response.readEntity(LadokException.class);
		} catch (RuntimeException e) {
			return null;
		}
	}

}
