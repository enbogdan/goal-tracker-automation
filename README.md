To run, you will need:

* [Maven 3](http://maven.apache.org) or above installed

Tests run automatically with default configuration after starting the build:

    mvn clean install
	
Optional Maven parameters:
* test.config : name of the properties file to be used, allows easy switching to other environments
* testng.suite : name of the testng xml suite, allows easy switching between test suites

	eg: mvn clean install -Dtest.config=env_preprod -Dtestng.suite=suite_regression
    
Assumption
- goal-tracker-java app is up and running by default on localhost:8080 
(host can be specified in properties file)
- payload content type is application/json
- goal service saves single goal
- goals service returns all goals
- no empty goal names should be allowed
- no duplicate (name, date, notes, weight) goals should be allowed
- no negative weight should be allowed
- solution is intended to run single threaded
- performance degradation over time as there is no service to allow clean up

Trade-off
- step failure stops current test execution and continues with next test 
(eg: if invalid status code we won't check if processed further as expected)

