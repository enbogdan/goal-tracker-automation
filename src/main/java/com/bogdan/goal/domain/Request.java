package com.bogdan.goal.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

public abstract class Request {
	protected HttpUriRequest request;
	
	private CloseableHttpClient client;	
	private HttpResponse response;
	private String jsonResponse;
	
	public Request(String requestUrl) {
		client = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
	}
	
	public void executeRequest() throws ClientProtocolException, IOException {
		response = client.execute(request);
		
		BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
		StringBuilder sb = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		br.close();
		jsonResponse = sb.toString();
	}
	
	public int getStatusCode() {
		return response.getStatusLine().getStatusCode();
	}
	
	public String getJsonResponse() {
		return jsonResponse;
	}
	
	public String getResponseType() {
		String contentType = "";
		for(org.apache.http.Header h:response.getAllHeaders())
			if(h.getName().equals("Content-Type"))
				contentType = h.getValue();
		return contentType;
	}
	
}
	
