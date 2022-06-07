package com.bbva.libranza.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUtil
{
	public static RestTemplate buildRestTemplate(boolean withSSL) throws Exception
	{
		if(!withSSL) 
		{
			return new RestTemplate();
		} 
		else 
		{
			TrustStrategy acceptingTrustStrategy = new TrustStrategy()
			{
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
				{
					return true;
				}
			};
	
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	 
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
	
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	
			requestFactory.setHttpClient(httpClient);
	
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			return restTemplate;
		}
	}
}
