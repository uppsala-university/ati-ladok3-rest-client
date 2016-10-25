package se.sunet.ati.ladok.rest.services;

public interface LadokServiceProperties {

	public String getRestbase();
	public void setRestbase(String restbase);

	public String getRestApiTransportProtcol();
	public void setRestApiTransportProtcol(String restApiTransportProtcol);
	
	public String getClientCertificateFile();
	public void setClientCertificateFile(String clientCertificateFile);
	
	public String getClientCertificatePwd();
	public void setClientCertificatePwd(String clientCertificatePwd);
	
	public String getClientCertificateKeystoreType();
	public void setClientCertificateKeystoreType(String clientCertificateKeystoreType);
	
	public String getTrustStoreFile();
	public void setTrustStoreFile(String trustStoreFile);
	
	public String getTrustStorePwd();
	public void setTrustStorePwd(String trustStorePwd);
	
	public String getTrustStoreType();
	public void setTrustStoreType(String trustStoreType);

}
