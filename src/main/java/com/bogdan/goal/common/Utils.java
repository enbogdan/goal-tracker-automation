package com.bogdan.goal.common;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.bogdan.goal.domain.Goal;
import com.bogdan.goal.domain.Request;
import com.bogdan.goal.domain.RestGet;
import com.bogdan.goal.domain.RestPost;

public class Utils {
	
	private static Request request;

	public static List<Goal> deserializeGoals(String jsonString)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Goal> goals = mapper.readValue(jsonString, mapper.getTypeFactory()
				.constructCollectionType(List.class, Goal.class));

		return goals;
	}

	public static String serializeGoal(Goal goal)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		mapper.setSerializationInclusion(Inclusion.NON_DEFAULT);

		return mapper.writeValueAsString(goal);
	}

	public static String randomStr(int len) {
		String AB = "abcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	
	public static String randomInt(int start, int end) {
		return Integer.toString((int) (Math.random()*(end-start)) + start);
	}
	
	public static void checkGoal(Goal goal, List<Goal> goals) {
		boolean found = false;
		for(Goal g : goals) {
			if(g.equals(goal))
				found = true;	
		}
		Assert.assertTrue("Goal >" + goal.toShortString() + "< not found in list!", found);
	}
	
	public static void checkDuplicates(Goal goal, List<Goal> goals) {
		int instances = 0;		
		for(Goal g : goals)
			if(g.equals(goal))
				instances++;
		Assert.assertTrue("Duplicates >"+instances+"< found for goal >"+goal.toShortString()+"<", instances<2);
	}
	
	public static void restGetGoals() throws ClientProtocolException, IOException {
		request = new RestGet(TestBase.goalsServiceUrl);
	}
	
	public static void restGetGoals(String target) throws ClientProtocolException, IOException {
		request = new RestGet(target);
	}
	
	public static void restGetGoals(String target, Header header) throws ClientProtocolException, IOException {
		request = new RestGet(target, header);
	}
	
	public static void restPostGoal(String payload) throws ClientProtocolException, IOException {
		request = new RestPost(TestBase.goalServiceUrl, payload);
	}
	
	public static void restPostGoal(Goal goal) throws ClientProtocolException, IOException {
		request = new RestPost(TestBase.goalServiceUrl, serializeGoal(goal));
	}
	
	public static void restPostGoal(String target, Goal goal) throws ClientProtocolException, IOException {
		request = new RestPost(target, serializeGoal(goal));
	}
	
	public static void restPostGoal(String target, Goal goal, String contentType) throws ClientProtocolException, IOException {
		request = new RestPost(target, serializeGoal(goal), contentType);
	}
	
	public static int getStatusCode() {
		return request.getStatusCode();
	}
	
	public static String getJsonResponse() {
		return request.getJsonResponse();
	}
	
	public static String getContentType() {
		return request.getResponseType();
	}
	
	public static List<Goal> getDeserializedGoals() throws JsonParseException, JsonMappingException, IOException {
		return deserializeGoals(request.getJsonResponse());
	}
}
