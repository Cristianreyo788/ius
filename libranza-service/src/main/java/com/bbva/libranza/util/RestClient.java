package com.bbva.libranza.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

public class RestClient {	
	
	private static final Logger LOG = LogManager.getLogger(RestClient.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public static void sendEmail(String serviceUri, Map<String, Object> parameters) throws Throwable {
		try {
			LOG.info("Parametros : " + parameters.toString());
			StringBuilder sbUrl = new StringBuilder();
			sbUrl.append(serviceUri).append("/sendHtml")
				 .append("?")
				 .append(formatParametersMap(parameters));
			
			HttpPost httpPost = new HttpPost(sbUrl.toString());
			
			makeHttpRequest(httpPost);
			
		}catch (SocketTimeoutException ste) {
			LOG.error(ste.getMessage(), ste);
			throw new RuntimeException(
					"Send Email SocketTimeoutException : " + ste.getMessage(), ste);
		} catch (HttpHostConnectException connectEx) {
			LOG.error(connectEx.getMessage(), connectEx);
			throw new RuntimeException(
					"Send Email HttpHostConnectException : "
							+ connectEx.getMessage(),
					connectEx);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(
					"Send Email IOException : " + e.getMessage(),
					e);
		} catch (Throwable t) {
			LOG.error(t.getMessage(), t);
			throw new RuntimeException(
					"Send Email RuntomeException : " + t.getMessage(),
					t);
		}
	}	
	
	@SuppressWarnings("unchecked")
	private static String makeHttpRequest(HttpRequestBase request) throws Throwable {

		CloseableHttpClient client = null;

		try {
			
			if(request.getURI().getScheme().toLowerCase().equals("http")) {
				client = HttpClientBuilder.create().build();
			} else if(request.getURI().getScheme().toLowerCase().equals("https")) {
				SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
					@Override
			        public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
						return true;
			        }
				}).build();

			    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
				
			    client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			} else {
				throw new Exception("Protocolo '" + request.getURI().getScheme() + "' no soportado");
			}
			
			LOG.info("Request : " + request.toString());

			HttpResponse response = client.execute(request);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

			StringBuffer result = new StringBuffer();

			String line = "";

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			if (result.length() == 0) {
				return null;
			} else if (String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2")) {
				return result.toString();
			} else if (response.getStatusLine().getStatusCode() == 500) {
				Map<String, String> errorMap = OBJECT_MAPPER.readValue(result.toString(), Map.class);
				throw new Exception(errorMap.get("error"));
			} else {
				throw new Exception(response.getStatusLine().getStatusCode() + " - " + response.getStatusLine().getReasonPhrase());
			}

		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
	
	private static String formatParametersMap(Map<String, Object> parameters) {	
		if(parameters != null && !parameters.isEmpty()) {			
			StringBuilder sbParams = new StringBuilder();
			
			for(String key : parameters.keySet()) {						
				sbParams.append(key).append("=").append(parameters.get(key)).append("&");
			}
			LOG.info("sbParams : " + sbParams.toString());
			
			return sbParams.substring(0,sbParams.length()-1).toString();
		}
		
		return null;
	}	
}
