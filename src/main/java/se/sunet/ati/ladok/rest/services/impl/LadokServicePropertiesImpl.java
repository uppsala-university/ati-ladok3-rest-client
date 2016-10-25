package se.sunet.ati.ladok.rest.services.impl;

import se.sunet.ati.ladok.rest.services.LadokServiceProperties;

public class LadokServicePropertiesImpl implements LadokServiceProperties {

	String restbase = "https://api.mit.ladok.se/";
	String restApiTransportProtcol = "TLSv1.2"; // Other options: TLSv1.0, TLSv1.1, SSLv2, SSLv3
	String clientCertificateFile;
	String clientCertificatePwd;
	String clientCertificateKeystoreType = "PKCS12";
	String trustStoreFile;
	String trustStorePwd = "changeit";
	String trustStoreType = "JKS";
	
	public String getRestbase() {
		return restbase;
	}
	public void setRestbase(String restbase) {
		this.restbase = restbase;
	}
	public String getRestApiTransportProtcol() {
		return restApiTransportProtcol;
	}
	public void setRestApiTransportProtcol(String restApiTransportProtcol) {
		this.restApiTransportProtcol = restApiTransportProtcol;
	}
	public String getClientCertificateFile() {
		return clientCertificateFile;
	}
	public void setClientCertificateFile(String clientCertificateFile) {
		this.clientCertificateFile = clientCertificateFile;
	}
	public String getClientCertificatePwd() {
		return clientCertificatePwd;
	}
	public void setClientCertificatePwd(String clientCertificatePwd) {
		this.clientCertificatePwd = clientCertificatePwd;
	}
	public String getClientCertificateKeystoreType() {
		return clientCertificateKeystoreType;
	}
	public void setClientCertificateKeystoreType(
			String clientCertificateKeystoreType) {
		this.clientCertificateKeystoreType = clientCertificateKeystoreType;
	}
	public String getTrustStoreFile() {
		return trustStoreFile;
	}
	public void setTrustStoreFile(String trustStoreFile) {
		this.trustStoreFile = trustStoreFile;
	}
	public String getTrustStorePwd() {
		return trustStorePwd;
	}
	public void setTrustStorePwd(String trustStorePwd) {
		this.trustStorePwd = trustStorePwd;
	}
	public String getTrustStoreType() {
		return trustStoreType;
	}
	public void setTrustStoreType(String trustStoreType) {
		this.trustStoreType = trustStoreType;
	}
}
