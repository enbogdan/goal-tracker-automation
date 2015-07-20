package com.bogdan.goal.domain;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class RestPost extends Request{
	
	public RestPost(String requestUrl, String jsonPayload) throws ClientProtocolException, IOException {
		super(requestUrl);
		request = new HttpPost(requestUrl);

		StringEntity se = new StringEntity(jsonPayload);
		se.setContentType("application/json");
		((HttpPost)request).setEntity(se);	
		executeRequest();
	}
	
	public RestPost(String requestUrl, String jsonPayload, String mediaType) throws ClientProtocolException, IOException {
		super(requestUrl);
		request = new HttpPost(requestUrl);

		StringEntity se = new StringEntity(jsonPayload);
		se.setContentType(mediaType);
		((HttpPost)request).setEntity(se);	
		executeRequest();
	}
}
