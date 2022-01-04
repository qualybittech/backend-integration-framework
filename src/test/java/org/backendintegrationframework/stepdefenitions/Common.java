package org.backendintegrationframework.stepdefenitions;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import org.backendintegrationframework.util.Util;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Map;
import java.util.logging.Level;

public class Common {

    protected RequestSpecification request;
    protected Response response;
    protected ValidatableResponse json;
    protected RestAssuredConfig config;
    protected Integer timeOut ;
    protected WebDriver driver;
    protected Util util;

    protected String baseURI;
    protected String usersAPIPath;
    	
    public Common(Util util) {
        baseURI = util.getValueFromAppProperties("baseURI");
        usersAPIPath = util.getValueFromAppProperties("usersAPIPath");
    }

    protected WebDriver getDriver(){
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        options.setExperimentalOption("w3c",false);
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1200");

        String os = System.getProperty("os.name");
        System.out.println("Using System Property: " + os);
        if(os.toLowerCase().contains("win")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        }
        driver = new ChromeDriver(options);
        return driver;
    }

    protected RequestSpecification setRequestSpecification(String baseURI){
        config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection.timeout", timeOut).
                setParam("http.socket.timeout", timeOut).
                setParam("http.connection-manager.timeout", timeOut));
        return  RestAssured.given().baseUri(baseURI).config(config);
    }

   
    protected String getDataFromNetworkLogs(WebDriver driver,String logRowIdentifier, String startIndexStr, String endIndexStr) {
        String output = null;
        LogEntries les = driver.manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry le : les) {
            //System.out.println(le.getMessage());
            if(le.getMessage().contains(logRowIdentifier)){
                String authTokenLine = le.getMessage();
                int start = authTokenLine.indexOf(startIndexStr);
                int end = authTokenLine.indexOf(endIndexStr);

                output = authTokenLine.substring(start+13,end);
                break;
            }
        }
        return output;
    }

    protected JSONObject setPayloadBody(Map<String, String> mutableMapInputParameters, Map<String, String> inputParameters){
        String jwtTokenType, payloadInputs, profileId;

        JSONObject payload = new JSONObject();
        jwtTokenType = inputParameters.get("payload");
        payloadInputs = jwtTokenType;

            //To process if Payload has multiple data
            if(payloadInputs.contains(",")){
                String [] payloadInputsArr = payloadInputs.split(",");
                jwtTokenType = payloadInputsArr[0];
                profileId = payloadInputsArr[1];
                payload.put("id",profileId);
            }

        JSONObject requestPayload = new JSONObject(mutableMapInputParameters);
        requestPayload.put("payload",payload);
        return  requestPayload;
    }

    protected JSONObject setPayloadBodyWithSameInputData(Map<String, String> inputParameters){
        String  name, requestType;
        JSONObject payload = new JSONObject();

        for (Map.Entry<String, String> pair : inputParameters.entrySet()) {
            if(!(pair.getKey().contains("name")  || pair.getKey().contains("requestType"))){
                payload.put(pair.getKey(), pair.getValue());
            }
        }

        name = inputParameters.get("name");
        requestType = inputParameters.get("requestType");

        JSONObject requestPayload = new JSONObject();
        requestPayload.put("name",name);
        requestPayload.put("requestType",requestType);
        requestPayload.put("payload",payload);
        return  requestPayload;
    }

    protected Response sendEventRequestwithWait(Common common,String endPoint) throws InterruptedException {
        common.request.when().get(endPoint);
        Thread.sleep(2000);
        return common.request.when().get(endPoint);
    }
}
