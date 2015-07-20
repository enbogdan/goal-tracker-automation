package com.bogdan.goal.domain;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

public class RestGet extends Request{
	
	public RestGet(String requestUrl) throws ClientProtocolException, IOException {
		super(requestUrl);
		request = new HttpGet(requestUrl);
		request.addHeader("accept", "application/json");
		executeRequest();
	}
	
	public RestGet(String requestUrl, Header header) throws ClientProtocolException, IOException {
		super(requestUrl);
		request = new HttpGet(requestUrl);
		request.addHeader(header);
		executeRequest();
	}
}
