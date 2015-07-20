package com.bogdan.goal.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class TestBase {
	
	protected static final String appURL = "APP_URL";
	protected static final String appPort = "APP_PORT";
	
	protected static String goalServiceUrl;
	protected static String goalsServiceUrl;
	
	private static Properties properties;
	
	@BeforeClass
	public static void setUp() {
		System.out.println("@BeforeClass - setUp");
		loadProperties();
		goalServiceUrl = getAppUrl() + ":"+ getAppPort() + "/services/goal";
		goalsServiceUrl = getAppUrl() + ":"+ getAppPort() + "/services/goals";
	}
	
	@AfterClass
	public static void tearDown() {
		System.out.println("@AfterClass - tearDown");
	}
	
	@BeforeMethod 
	public static void beforeTest(Method method) {
		System.out.println("@BeforeTest: " + method.getName());
	}
	
	public static String getAppUrl() {
		return properties.getProperty(appURL);
	}
	
	public static String getAppPort() {
		return properties.getProperty(appPort);
	}
	
	private static void loadProperties() {		
		String propsPath = System.getProperty("test.config");
		
		try {
			FileInputStream fis = new FileInputStream(new File(propsPath));
			properties = new Properties();
			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
}
