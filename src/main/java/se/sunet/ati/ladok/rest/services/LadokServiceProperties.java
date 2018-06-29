package se.sunet.ati.ladok.rest.services;

public interface LadokServiceProperties {
	String getRestbase();
	void setRestbase(String restbase);

	String getRestApiTransportProtcol();
	void setRestApiTransportProtcol(String restApiTransportProtcol);

	String getClientCertificateFile();
	void setClientCertificateFile(String clientCertificateFile);

	String getClientCertificatePwd();
	void setClientCertificatePwd(String clientCertificatePwd);

	String getClientCertificateKeystoreType();
	void setClientCertificateKeystoreType(String clientCertificateKeystoreType);

	String getTrustStoreFile();
	void setTrustStoreFile(String trustStoreFile);

	String getTrustStorePwd();
	void setTrustStorePwd(String trustStorePwd);

	String getTrustStoreType();
	void setTrustStoreType(String trustStoreType);
	
	String getReadTimeOutName();
	void setReadTimeOutName(String readTimeOutName) ;
	
	String getConnectTimeOutName() ;
	void setConnectTimeOutName(String connectTimeOutName);
	
	int getConnectTimeOutValue();
	void setConnectTimeOutValue(int connectTimeOutValue) ;
	
	int getReadTimeOutValue();
	void setReadTimeOutValue(int readTimeOutValue);
}
