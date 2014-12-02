package com.example.apidemowjy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class HttpTestActivity extends Activity {
	
	private static String file_url = "http://test-d.1qianbao.com:1080/youqian/ver/android_version.json";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_test);
		
		findViewById(R.id.btn_http1).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							URL url = new URL(file_url);
							HttpURLConnection connection = (HttpURLConnection)url.openConnection();
							//connection.connect();
							Log.i(MyApplication.TAG, "the length of content is " + connection.getContentLength());
							Log.i(MyApplication.TAG, "the type of content is " + connection.getContentType());
							InputStream inStream = connection.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
		                    String content = reader.readLine();
		                    Log.i(MyApplication.TAG, "content = " + content);
		                    reader.close();
		                    inStream.close();
		                    connection.disconnect();
						} catch (Exception e) {
							Log.e(MyApplication.TAG, e.getMessage());
						}
					}
					
				}).start();
			}
			
		});
		
		findViewById(R.id.btn_http2).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){

					@Override
					public void run() {
						HttpEntity entity = null;
				        HttpClient client = new DefaultHttpClient();
				        HttpUriRequest request = null;
				        HttpResponse response = null;
				        
				        request = new HttpGet(file_url);
						try {
							response = client.execute(request);
						} catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
				        if (response.getStatusLine().getStatusCode() == 200) {
				            entity = response.getEntity();
				        }
				        if (entity != null){
				        	try {
				        		InputStream inStream = entity.getContent();
				        		Log.i(MyApplication.TAG, "content length = " + entity.getContentLength());
				        		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
				        		String content = reader.readLine();
				        		Log.i(MyApplication.TAG, "content = " + content);
				        	} catch (IOException e) {
								e.printStackTrace();
							}
				        }
					}
					
				}).start();
			}
		});
		
		findViewById(R.id.btn_http3).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							URL url = new URL("https://www.wikipedia.org");
							URLConnection urlConnection = url.openConnection();
							InputStream in = urlConnection.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		                    String content = "---- begin html ----";
		                    while(content != null){
		                    	Log.i(MyApplication.TAG, "content = " + content);
		                    	content = reader.readLine();
		                    }
		                    reader.close();
		                    in.close();
						}catch (Exception e) {
							Log.e(MyApplication.TAG, e.getMessage());
						}
						
					}
					
				}).start();
			}
			
		});
		
		findViewById(R.id.btn_http4).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
							Log.i(MyApplication.TAG, "default algorithm of trust manager factory: " + TrustManagerFactory.getDefaultAlgorithm());
							// Using null here initialises the TMF with the default trust store.
							tmf.init((KeyStore)null);
							// Get hold of the default trust manager
							X509TrustManager x509Tm = null;
							for(TrustManager tm : tmf.getTrustManagers()){
								if(tm instanceof X509TrustManager){
									x509Tm = (X509TrustManager) tm;
									Log.i(MyApplication.TAG, "we get a default x509 trust manager");
									break;
								}
							}
							// Wrap it in your own class.
							final X509TrustManager finalTm = x509Tm;
							X509TrustManager customTm = new X509TrustManager(){

								@Override
								public void checkClientTrusted(
										X509Certificate[] chain, String authType)
										throws CertificateException {
									// TODO Auto-generated method stub
									//finalTm.checkClientTrusted(chain, authType);
								}

								@Override
								public void checkServerTrusted(
										X509Certificate[] chain, String authType)
										throws CertificateException {
									// TODO Auto-generated method stub
									Log.i(MyApplication.TAG, "enter checkServerTrusted()");
									//finalTm.checkServerTrusted(chain, authType);
									if(chain.length > 0) {
										Log.i(MyApplication.TAG, "IssuerDN = " + chain[0].getIssuerDN().getName());
										Log.i(MyApplication.TAG, "SubjectDN = " + chain[0].getSubjectDN().getName());
										X509Certificate cert = chain[0];
										//cert.checkValidity();
										Log.i(MyApplication.TAG, "Certificate base64 string = " + Base64.encodeToString(cert.getEncoded(), Base64.NO_WRAP));
									}
									
								}

								@Override
								public X509Certificate[] getAcceptedIssuers() {
									// TODO Auto-generated method stub
									//return finalTm.getAcceptedIssuers();
									return null;
								}
								
							};
							
							SSLContext sslContext = SSLContext.getInstance("TLS");
							sslContext.init(null, new TrustManager[]{customTm}, null);
							
							//SSLContext.setDefault(sslContext);
							URL url = new URL("https://test-ms.1qianbao.com:2443/youqian/pageconfig/1qb_welcome.json");
							//URL url = new URL("https://www.1qianbao.com");
							//URL url = new URL("http://www.baidu.com");
							//URL url = new URL("https://certs.cac.washington.edu/CAtest/");
							HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
							urlConnection.setRequestProperty("If-Modified-Since", "Mon, 22 Sep 2014 05:57:20 GMT");
							//urlConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
							urlConnection.setHostnameVerifier(new HostnameVerifier(){

								@Override
								public boolean verify(String hostname,
										SSLSession session) {
									// TODO Auto-generated method stub
									Log.i(MyApplication.TAG, "Approving certificate for " + hostname);
									return true;
								}
								
							});
							urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
							Log.i(MyApplication.TAG, "response header:  " + urlConnection.getHeaderFields());
							int responseCode = urlConnection.getResponseCode();
							if(responseCode == HttpURLConnection.HTTP_OK){
								Log.i(MyApplication.TAG, "http ok, content not modified since: " + urlConnection.getHeaderField("Last-Modified"));
								InputStream in = urlConnection.getInputStream();
								BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			                    String content = "---- begin content ----";
			                    String json = "";
			                    while(content != null){
			                    	Log.i(MyApplication.TAG, "content = " + content);
			                    	content = reader.readLine();
			                    	if (content != null){
			                    		json += content.trim();
			                    	}
			                    	
			                    }
			                    Log.i(MyApplication.TAG, "the json string = " + json);
			                    reader.close();
			                    in.close();
							} else if(responseCode == HttpURLConnection.HTTP_NOT_MODIFIED){
								Log.i(MyApplication.TAG, "http not modified, content not modified since: " + urlConnection.getHeaderField("Last-Modified"));
							} else{
								
							}
							

						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							Log.e(MyApplication.TAG, e1.getMessage());
						} catch (KeyStoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.e(MyApplication.TAG, e	.getMessage());
						} catch (KeyManagementException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.e(MyApplication.TAG, e	.getMessage());
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.e(MyApplication.TAG, e	.getMessage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.e(MyApplication.TAG, e	.getMessage());
						}			
					}
					
				}).start();
			}
			
		});
	}
}
