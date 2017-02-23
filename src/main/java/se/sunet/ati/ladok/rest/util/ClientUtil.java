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

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.sunet.ati.ladok.rest.services.LadokServiceProperties;

public class ClientUtil {

	private static Log log = LogFactory.getLog(ClientUtil.class);

	private static InputStream createInputStream(String filePath, String fileType) throws IOException {
		InputStream keystoreInputStream = null;
		if (Files.exists(Paths.get(filePath))) {
			// Try to find the keystore as a file
			keystoreInputStream = new FileInputStream(filePath);
			log.info("Found the " + fileType + " '" + filePath + "' as a file");
		}
		else {
			// Try to find the keystore as a classpath resource
			keystoreInputStream = ClientUtil.class.getClassLoader().getResourceAsStream(filePath);
			if (keystoreInputStream == null) {
				String message = "Unable to find the " + fileType
						+ " '" + filePath + "' as a classpath resource";
				log.debug(message);
				throw new IOException(message);
			}
			log.info("Found the " + fileType + " '" + filePath + "' as a classpath resource");
		}
		if (keystoreInputStream == null) {
			throw new IOException("Unable to find the " + fileType
							      + " '" + filePath + "'");
		}
		return keystoreInputStream;
	}

	static public WebTarget newClient(LadokServiceProperties lsp, String path) {
		loadProperties(lsp);
		checkProperties(lsp);

		InputStream keystoreInputStream = null;
		InputStream truststoreInputStream = null;
		try {
			SSLContext sslContext = SSLContext.getInstance(lsp.getRestApiTransportProtcol());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

			// Initiate client certificate key store.
			KeyStore clientKeystore;
			clientKeystore = KeyStore.getInstance(lsp.getClientCertificateKeystoreType());
			keystoreInputStream = createInputStream(lsp.getClientCertificateFile(), "client certificate key store");
			clientKeystore.load(keystoreInputStream, lsp.getClientCertificatePwd().toCharArray());
			// Initiate optional certificate trust store.
			KeyStore trustStore = null;
			if (lsp.getTrustStoreFile() != null) {
				trustStore = KeyStore.getInstance(lsp.getTrustStoreType());
				truststoreInputStream = createInputStream(lsp.getTrustStoreFile(), "trust store");
				trustStore.load(truststoreInputStream, lsp.getTrustStorePwd().toCharArray());
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
			throw new IllegalStateException(e);
		} finally {
			if (keystoreInputStream != null) {
				try {
					keystoreInputStream.close();
				}
				catch (IOException e) {
					// Ignore
				}
			}
			if (truststoreInputStream != null) {
				try {
					truststoreInputStream.close();
				}
				catch (IOException e) {
					// Ignore
				}
			}
		}
	}

	static private void loadProperties(LadokServiceProperties lsp) {
		if (lsp.getClientCertificatePwd() != null) {
			log.info("Properties already set via setters. Not using restclient.properties");
			return;
		}
		try (InputStream in = ClientUtil.class.getClassLoader().getResourceAsStream("restclient.properties")) {
			Properties properties = new Properties();
			properties.load(in);
			log.info("Loading properties from restclient.properties");
			String clientCertificateFile = StrSubstitutor.replaceSystemProperties(properties.getProperty("clientCertificateFile"));
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
			String trustStoreFile = StrSubstitutor.replaceSystemProperties(properties.getProperty("trustStoreFile"));
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
	} catch (IOException e) {
			throw new IllegalStateException("Failed to open restclient.properties" ,e);
		}
	}

	static private void checkProperties(LadokServiceProperties lsp) {
		String clientCertificateFile = lsp.getClientCertificateFile();
		if (clientCertificateFile == null || clientCertificateFile.equals("")) {
			throw new IllegalArgumentException("Missing property \"clientCertificateFile\".");
		}
		log.info("Using client certificate key store: " + clientCertificateFile);
		String clientCertificatePwd = lsp.getClientCertificatePwd();
		if (clientCertificatePwd == null || clientCertificatePwd.equals("")) {
			throw new IllegalArgumentException("Missing property \"clientCertificatePwd\".");
		}
		String clientCertificateKeystoreType = lsp.getClientCertificateKeystoreType();
		if (clientCertificateKeystoreType == null || clientCertificateKeystoreType.equals("")) {
			throw new IllegalArgumentException("Missing property \"clientCertificateKeystoreType\".");
		}
		log.info("Using client certificate key store type: " + clientCertificateKeystoreType);

		String trustStoreFile = lsp.getTrustStoreFile();
		if (trustStoreFile == null || trustStoreFile.equals("")) {
			log.info("The property \"trustStoreFile\" is not specified. No truststore will be used.");
		}
		else {
			log.info("Using certificate trust store: " + trustStoreFile);

			String trustStorePwd = lsp.getTrustStorePwd();
			if(trustStorePwd == null || trustStorePwd.equals("")) {
				throw new IllegalArgumentException("Missing property \"trustStorePwd\".");
			}
			String trustStoreType = lsp.getTrustStoreType();
			if(trustStoreType == null || trustStoreType.equals("")) {
				throw new IllegalArgumentException("Missing property \"trustStoreType\".");
			}
			log.info("Using trust store type: " + trustStoreType);
		}

		String restBase = lsp.getRestbase();
		if (restBase == null || restBase.equals("")) {
			throw new IllegalArgumentException("Missing property \"restbase\"");
		}
		log.info("Using REST base URL: " + restBase);
		String restApiTransportProtcol = lsp.getRestApiTransportProtcol();
		if (restApiTransportProtcol == null || restApiTransportProtcol.equals("")) {
				throw new IllegalArgumentException("Missing property \"restApiTransportProtcol\"");
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
