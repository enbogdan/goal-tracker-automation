package com.bogdan.goal;

import static com.bogdan.goal.common.Utils.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.bogdan.goal.common.TestBase;
import com.bogdan.goal.domain.Goal;

public class GoalTrackerPositiveTest extends TestBase {

	@Test
	public void shouldSaveSingleGoal() {
		Goal goal = new Goal(randomStr(10), Long.toString(System.currentTimeMillis()), randomStr(20), randomInt(1, 100));
		
		try {
			restPostGoal(goal);		
			Assert.assertEquals("Save goal >"+goal.toShortString()+"< : wrong status code!", 200, getStatusCode());
			Assert.assertEquals("Save goal >"+goal.toShortString()+"< : wrong content type !", "application/json;charset=UTF-8", getContentType());
			checkGoal(goal, getDeserializedGoals());
			 
			restGetGoals();
			Assert.assertEquals("Get goals: wrong status code!", 200, getStatusCode());
			Assert.assertEquals("Get goals : wrong content type !", "application/json", getContentType());
			checkGoal(goal, getDeserializedGoals());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldSaveMultipleGoals() {
		
		
		try {
			restGetGoals();
			Assert.assertEquals("Get goals: wrong status code!", 200, getStatusCode());
			Assert.assertEquals("Get goals : wrong content type !", "application/json", getContentType());
			int goalsCount = getDeserializedGoals().size();
			
			for(int i=0;i<5;i++){
				Goal goal = new Goal(randomStr(10), Long.toString(System.currentTimeMillis()), randomStr(20), randomInt(1, 100));
				restPostGoal(goal);		
				Assert.assertEquals("Save goal >"+goal.toShortString()+"< : wrong status code!", 200, getStatusCode());	
				Assert.assertEquals("Save goal >"+goal.toShortString()+"< : wrong content type !", "application/json;charset=UTF-8", getContentType());
				checkGoal(goal, getDeserializedGoals());
			}
			 
			restGetGoals();
			Assert.assertEquals("Get goals: wrong status code!", 200, getStatusCode());
			Assert.assertEquals("Get goals : wrong content type !", "application/json", getContentType());
			List<Goal> goals = getDeserializedGoals();
			Assert.assertEquals("Invalid goals count after save", goalsCount+5, goals.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldSaveDefaultValues() {
		Goal goal = new Goal();
		goal.setName(randomStr(20));
		
		try {
			restPostGoal(goal);	
			Assert.assertEquals("Save goal >"+goal.toShortString()+"<: wrong status code!", 200, getStatusCode());	
			Assert.assertEquals("Save goal >"+goal.toShortString()+"< : wrong content type !", "application/json;charset=UTF-8", getContentType());
			List<Goal> goals = getDeserializedGoals();
			checkGoal(goal, goals);
			 
			restGetGoals();
			Assert.assertEquals("Get goals: wrong status code!", 200, getStatusCode());
			Assert.assertEquals("Get goals : wrong content type !", "application/json", getContentType());
			goals = getDeserializedGoals();
			checkGoal(goal, goals);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldSaveUniqueIDs() {
		Goal goal = new Goal(randomStr(10), Long.toString(System.currentTimeMillis()), randomStr(20), randomInt(1, 100));
		
		try {
			restPostGoal(goal);		
			Assert.assertEquals("Save goal >"+goal.toShortString()+"<: wrong status code!", 200, getStatusCode());	
			Assert.assertEquals("Save goal >"+goal.toShortString()+"< : wrong content type !", "application/json;charset=UTF-8", getContentType());
			List<Goal> goals = getDeserializedGoals();
			checkGoal(goal, goals);
			 
			Set<String> set = new HashSet<String>();
			for(Goal g:goals)
				set.add(g.getId());
			
			Assert.assertEquals("Not all IDs are unique!", goals.size(), set.size());	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
