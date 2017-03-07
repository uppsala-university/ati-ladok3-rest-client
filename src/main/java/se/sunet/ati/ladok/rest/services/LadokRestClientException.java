package se.sunet.ati.ladok.rest.services;

import java.util.Objects;

import se.ladok.schemas.LadokException;

public class LadokRestClientException extends RuntimeException {
	private final int httpStatusCode;
	private final LadokException ladokException;

	public LadokRestClientException(int httpStatusCode, LadokException ladokException) {
		super(getMessage(httpStatusCode, ladokException));
		this.httpStatusCode = httpStatusCode;
		this.ladokException = ladokException;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public LadokException getLadokException() {
		return ladokException;
	}

	private static String getMessage(int httpStatusCode, LadokException ladokException) {
		return Objects.toString(String.format("Httpkod: %s, kategori: %s, grupp: %s, detalj: %s, meddelande: %s", httpStatusCode,
				ladokException.getFelkategoriText(), ladokException.getFelgruppText(), ladokException.getDetaljkodText(), ladokException.getMeddelande()),
				"null");
	}
}
