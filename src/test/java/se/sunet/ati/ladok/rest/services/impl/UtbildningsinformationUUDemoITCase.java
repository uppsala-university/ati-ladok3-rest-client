package se.sunet.ati.ladok.rest.services.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import se.ladok.schemas.utbildningsinformation.SokresultatUtbildningstillfalleProjektion;
import se.sunet.ati.ladok.rest.api.utbildningsinformation.SokUtbildningstillfallenQuery;
import se.sunet.ati.ladok.rest.services.Utbildningsinformation;
import se.sunet.ati.ladok.rest.util.TestUtil;

public class UtbildningsinformationUUDemoITCase {

	static final String TEST_DATA_FILE = "restclient.testdata.uudemo.properties";
	static Properties properties = null;

	@BeforeClass
	public static void beforeClass() throws IOException {
		properties = TestUtil.getProperties(TEST_DATA_FILE);
	}

	@Test
	public void sokKurstillfallen() {
		Utbildningsinformation ui = new UtbildningsinformationImpl();
		SokUtbildningstillfallenQuery query = SokUtbildningstillfallenQuery.builder()
				.utbildningskod(Arrays.asList("1MA006"))
				.build();

		SokresultatUtbildningstillfalleProjektion projektion = ui.sokUtbildningstillfallen(query);

		Assert.assertTrue(projektion.getResultat().size() > 10);
	}

	@Test
	public void sokKurstillfallenWithPaging() {
		Utbildningsinformation ui = new UtbildningsinformationImpl();
		SokUtbildningstillfallenQuery query = SokUtbildningstillfallenQuery.builder()
				.utbildningskod(Arrays.asList("1MA006"))
				.limit(3)
				.page(2)
				.build();

		SokresultatUtbildningstillfalleProjektion projektion = ui.sokUtbildningstillfallen(query);

		Assert.assertEquals(3, projektion.getResultat().size());
	}

}
