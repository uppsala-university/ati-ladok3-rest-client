package se.sunet.ati.ladok.rest.api.exceptions;

import java.util.Arrays;

public class SaknarObligatoriskParameterException extends RuntimeException {

	public SaknarObligatoriskParameterException(String tjanst, String... parameter) {
		super(createMessage(tjanst, parameter));
	}

	private static String createMessage(String tjanst, String[] parameter) {
		if(hasSingleParameters(parameter)){
			return createSingleParameterMessage(tjanst, first(parameter));
		} else {
			return createMutlipleParametersMessage(tjanst, parameter);
		}
	}

	private static boolean hasSingleParameters(String[] parameter) {
		return 1 == parameter.length;
	}

	private static String createMutlipleParametersMessage(String tjanst, String[] parameter) {
		return new StringBuilder()
				.append("Tjänsten ")
				.append(tjanst)
				.append(" saknar de obligatoriska parametrarna ")
				.append(Arrays.toString(parameter))
				.toString();
	}

	private static String createSingleParameterMessage(String tjanst, String parameter) {
		return new StringBuilder()
				.append("Tjänsten ")
				.append(tjanst)
				.append(" saknar den obligatoriska parametern ")
				.append(parameter)
				.toString();
	}

	private static String first(String[] parameter) {
		return parameter[0];
	}
}
