package com.bogdan.goal;

import java.util.List;

import junit.framework.Assert;

import org.apache.http.message.BasicHeader;
import org.testng.annotations.Test;

import com.bogdan.goal.common.TestBase;

import static com.bogdan.goal.common.Utils.*;

import com.bogdan.goal.domain.Goal;

public class GoalTrackerNegativeTest extends TestBase {
	
	@Test
	public void shouldNotSaveNullGoal() {
		Goal goal = null;
		try {
			restPostGoal(goal);			
			Assert.assertEquals("Save goal >null<: wrong status code!", 500, getStatusCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotSaveMalformedPayload() {
		try {
			restPostGoal(randomStr(50));			
			Assert.assertEquals("Save malformed payload: wrong status code!", 400, getStatusCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotSaveInvalidWeight() {
		Goal goal = new Goal(randomStr(10), Long.toString(System.currentTimeMillis()), randomStr(20), "cdadasd");
		try {
			restPostGoal(goal);			
			Assert.assertEquals("Save invalid weight goal >"+goal.toShortString()+"<: wrong status code!", 400, getStatusCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotSaveNegativeWeight() {
		Goal goal = new Goal(randomStr(10), Long.toString(System.currentTimeMillis()) , randomStr(20), "-50");
		
		try {
			restPostGoal(goal);			
			Assert.assertEquals("Save negative weight goal >"+goal.toShortString()+"<: wrong status code!", 400, getStatusCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotSaveEmptyName() {
		Goal goal = new Goal(null, Long.toString(System.currentTimeMillis()) , randomStr(20), randomInt(1, 100));
		
		try {
			restPostGoal(goal);			
			Assert.assertEquals("Save empty name goal >"+goal.toShortString()+"<: wrong status code!", 400, getStatusCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotSaveInvalidDate() {
		Goal goal = new Goal(randomStr(10), "asdgds", randomStr(20), "0");
		try {
			restPostGoal(goal);			
			Assert.assertEquals("Save invalid date goal >"+goal.toShortString()+"<: wrong status code!", 400, getStatusCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotSaveDuplicates() {
		Goal goal = new Goal(randomStr(10), Long.toString(System.currentTimeMillis()), randomStr(20), randomInt(1, 100));
		
		try {
			for(int i=0;i<10;i++){				
				restPostGoal(goal);	
				Assert.assertEquals("Save goal >"+goal.toShortString()+"<: wrong status code!", 200, getStatusCode());			
				List<Goal> goals = getDeserializedGoals();
				checkGoal(goal, goals);
			}
			 
			restGetGoals();
			Assert.assertEquals("Get goals: wrong status code!", 200, getStatusCode());
			List<Goal> goals = getDeserializedGoals();
			checkDuplicates(goal, goals);				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldNotAllowGetOnSaveService() {
		try {			
			restGetGoals(goalServiceUrl);	
			Assert.assertEquals("Get instead of post: wrong status code!", 405, getStatusCode());							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotAllowPostOnReadService() {
		try {			
			restPostGoal(goalsServiceUrl, null);	
			Assert.assertEquals("Get instead of post: wrong status code!", 405, getStatusCode());							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotAllowEmptyContentType() {
		try {			
			restPostGoal(goalServiceUrl, null, null);	
			Assert.assertEquals("Get instead of post: wrong status code!", 415, getStatusCode());							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotAllowTextContentType() {
		try {			
			restPostGoal(goalServiceUrl, null, "text/html");	
			Assert.assertEquals("Get instead of post: wrong status code!", 415, getStatusCode());							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldNotAcceptTextResponse() {
		try {			
			restGetGoals(goalsServiceUrl, new BasicHeader("accept", "text/html"));	
			Assert.assertEquals("Get instead of post: wrong status code!", 406, getStatusCode());							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
