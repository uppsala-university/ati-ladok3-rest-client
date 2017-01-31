package se.sunet.ati.ladok.rest.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A utility class for tests.
 */
public class TestUtil {
	
	private static final Log log = LogFactory.getLog(TestUtil.class);
	private static final String TESTDATA_FILE = "restclient.testdata.properties";

	public static Properties getProperties() throws IOException {
		return getProperties(TESTDATA_FILE);
	}
	
	public static Properties getProperties(String file) throws IOException {
		InputStream in = TestUtil.class.getClassLoader().getResourceAsStream(file);
		if (in != null) {
			Properties properties = new Properties();
			properties.load(in);
			log.info("Loading test properties from " + file);
			return properties;
		}
		else {
			throw new RuntimeException("The file " + file + " was not found");
		}
	}
}
