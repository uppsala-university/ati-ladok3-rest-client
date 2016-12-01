package se.sunet.ati.ladok.rest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.sunet.ati.ladok.rest.services.LadokServiceProperties;

public class ClientUtil {

	private static Log log = LogFactory.getLog(ClientUtil.class);

	static public WebTarget newClient(LadokServiceProperties lsp, String path) {
		try {
			loadProperties(lsp);
			checkProperties(lsp);
		} catch (Exception e) {
			log.error("Unable to read restclient.properties");
			throw new RuntimeException(e);
		}

		try {
			SSLContext sslContext = SSLContext.getInstance(lsp.getRestApiTransportProtcol());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance( KeyManagerFactory.getDefaultAlgorithm() );

			// Initiate client certificate key store.
			KeyStore clientKeystore;
			clientKeystore = KeyStore.getInstance(lsp.getClientCertificateKeystoreType());
			clientKeystore.load(new FileInputStream(lsp.getClientCertificateFile()), lsp.getClientCertificatePwd().toCharArray());
			// Initiate optional certificate trust store.
			KeyStore trustStore = null;
			if (lsp.getTrustStoreFile() != null) {
				trustStore = KeyStore.getInstance(lsp.getTrustStoreType());
				trustStore.load(new FileInputStream(lsp.getTrustStoreFile()), lsp.getTrustStorePwd().toCharArray());
			}

			// Assign and initiate client builder.
			kmf.init(clientKeystore, lsp.getClientCertificatePwd().toCharArray() );
			sslContext.init( kmf.getKeyManagers(), null, null );
			ClientBuilder cb = ClientBuilder.newBuilder();
			cb.keyStore(clientKeystore, lsp.getClientCertificatePwd());
			if (trustStore != null) {
				cb.trustStore(trustStore);
			}

			return cb.build().target(stripEndSlash(lsp.getRestbase()) + "/" + stripStartSlash(path));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static private void loadProperties(LadokServiceProperties lsp) throws IOException {
		if (lsp.getClientCertificatePwd() != null) {
			log.info("Properties already set via setters. Not using restclient.properties");
			return;
		}
		InputStream in = ClientUtil.class.getClassLoader().getResourceAsStream("restclient.properties");
		if (in != null) {
			Properties properties = new Properties();
			properties.load(in);
			log.info("Loading properties from restclient.properties");
			String clientCertificateFile = properties.getProperty("clientCertificateFile");
			if (clientCertificateFile != null && !clientCertificateFile.equals("")) {
				lsp.setClientCertificateFile(clientCertificateFile);
			}
			String clientCertificatePwd = properties.getProperty("clientCertificatePwd");
			if (clientCertificatePwd != null && !clientCertificatePwd.equals("")) {
				lsp.setClientCertificatePwd(clientCertificatePwd);
			}
			String clientCertificateKeystoreType = properties.getProperty("clientCertificateKeystoreType");
			if (clientCertificateKeystoreType != null && !clientCertificateKeystoreType.equals("")) {
				lsp.setClientCertificateKeystoreType(clientCertificateKeystoreType);
			}
			String trustStoreFile = properties.getProperty("trustStoreFile");
			if (trustStoreFile != null && !trustStoreFile.equals("")) {
				lsp.setTrustStoreFile(trustStoreFile);
			}
			String trustStorePwd = properties.getProperty("trustStorePwd");
			if (trustStorePwd != null && !trustStorePwd.equals("")) {
				lsp.setTrustStorePwd(trustStorePwd);
			}
			String trustStoreType = properties.getProperty("trustStoreType");
			if (trustStoreType != null && !trustStoreType.equals("")) {
				lsp.setTrustStoreType(trustStoreType);
			}
			String restBase = properties.getProperty("restbase");
			if (restBase != null && !restBase.equals("")) {
				lsp.setRestbase(restBase);
			}
			String restApiTransportProtcol = properties.getProperty("restApiTransportProtcol");
			if (restApiTransportProtcol != null && !restApiTransportProtcol.equals("")) {
				lsp.setRestApiTransportProtcol(restApiTransportProtcol);
			}
		} else {
			throw new RuntimeException("No restclient.properties found");
		}
	}

	static private void checkProperties(LadokServiceProperties lsp) throws Exception {
		String clientCertificateFile = lsp.getClientCertificateFile();
		if (clientCertificateFile == null || clientCertificateFile.equals("")) {
			throw new Exception("Missing property \"clientCertificateFile\".");
		}
		if (!clientCertificateFile.substring(0, 1).equalsIgnoreCase("/")) {
			clientCertificateFile = System.getProperty("user.home") + "/" + clientCertificateFile;
			lsp.setClientCertificateFile(clientCertificateFile);
			log.debug("Using client certificate keystore path relative to home directory '" + System.getProperty("user.home")  + "'.");
		}
		if (!Files.exists(Paths.get(clientCertificateFile))) {
			throw new Exception("Property \"clientCertificateFile\" (\"" + clientCertificateFile + "\") does not exist.");
		}
		log.info("Using client certificate keystore: " + clientCertificateFile);
		String clientCertificatePwd = lsp.getClientCertificatePwd();
		if (clientCertificatePwd == null || clientCertificatePwd.equals("")) {
			throw new Exception("Missing property \"clientCertificatePwd\".");
		}
		String clientCertificateKeystoreType = lsp.getClientCertificateKeystoreType();
		if (clientCertificateKeystoreType == null || clientCertificateKeystoreType.equals("")) {
			throw new Exception("Missing property \"clientCertificateKeystoreType\".");
		}
		log.info("Using client certificate key store type: " + clientCertificateKeystoreType);

		String trustStoreFile = lsp.getTrustStoreFile();
		if (trustStoreFile == null || trustStoreFile.equals("")) {
			log.info("The property \"trustStoreFile\" is not specified. No truststore will be used.");
		}
		else {
			if(!trustStoreFile.substring(0, 1).equalsIgnoreCase("/")) {
				trustStoreFile = System.getProperty("user.home") + "/" + trustStoreFile;
				lsp.setTrustStoreFile(trustStoreFile);
				log.debug("Using certificate trust store path relative to home directory '" + System.getProperty("user.home") + "'.");
			}
			if(!Files.exists(Paths.get(trustStoreFile))) {
				throw new Exception("Property \"trustStoreFile\" have no corresponding resource.");
			}
			log.info("Using certificate trust store: " + trustStoreFile);

			String trustStorePwd = lsp.getTrustStorePwd();
			if(trustStorePwd == null || trustStorePwd.equals("")) {
				throw new Exception("Missing property \"trustStorePwd\".");
			}
			String trustStoreType = lsp.getTrustStoreType();
			if(trustStoreType == null || trustStoreType.equals("")) {
				throw new Exception("Missing property \"trustStoreType\".");
			}
			log.info("Using trust store type: " + trustStoreType);
		}

		String restBase = lsp.getRestbase();
		if (restBase == null || restBase.equals("")) {
			throw new Exception("Missing property \"restbase\"");
		}
		log.info("Using REST base URL: " + restBase);
		String restApiTransportProtcol = lsp.getRestApiTransportProtcol();
		if (restApiTransportProtcol == null || restApiTransportProtcol.equals("")) {
				throw new Exception("Missing property \"restApiTransportProtcol\"");
		}
		log.info("Using transport protocol: " + restApiTransportProtcol);
	}

	static private String stripEndSlash(String path) {
		return path.replaceFirst("/$", "");
	}

	static private String stripStartSlash(String path) {
		return path.replaceFirst("^/", "");
	}

	public static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
	public static final String CONTENT_TYPE_HEADER_VALUE = "application/vnd.ladok+xml";

}
