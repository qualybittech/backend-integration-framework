package org.backendtestframework.util;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;

public class Util {

    public String getValueFromAppProperties(String key){
        String value = null;
        String environment = "qa";
        try {
            if(System.getProperty("env") != null){
                environment =System.getProperty("env") ;
            }
            InputStream input = new FileInputStream("src/test/resources/application-"+environment+".properties");
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            value = prop.getProperty(key);
        } catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public String getValueFromJson(Response response, String key){
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get(key);
    }

    public void validateContentInResponse(Map<String, String> responseFields, ValidatableResponse response){
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if (StringUtils.isNumeric(field.getValue())) {
                response.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
            } else {
                response.body(field.getKey(), equalTo(field.getValue()));
            }
        }
    }

    public void validateContentInResponsePayload(Map<String, String> responseFields, Response response){
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            //response.body("payload",containsString(field.getValue()));
            //response.body(path,containsString(field.getValue()));
            String actual = response.jsonPath().getString(field.getKey());
            String expected =  field.getValue();
            Assert.assertEquals("Verify Response content",expected,actual);
        }
    }
}
