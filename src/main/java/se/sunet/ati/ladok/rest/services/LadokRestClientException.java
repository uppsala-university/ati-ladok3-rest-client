package se.sunet.ati.ladok.rest.services;

import se.ladok.schemas.LadokException;

public class LadokRestClientException extends RuntimeException {
	private final int httpStatusCode;
	private final LadokException ladokException;

	public LadokRestClientException(int httpStatusCode, String errorMessage, LadokException ladokException) {
		super(String.format("Httpkod: %s, meddelande: %s", httpStatusCode, errorMessage));
		this.httpStatusCode = httpStatusCode;
		this.ladokException = ladokException;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public LadokException getLadokException() {
		return ladokException;
	}

}
