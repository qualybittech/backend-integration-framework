package org.backendintegrationframework.stepdefenitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetUserStepDefenitions {

private Common common;


    public GetUserStepDefenitions(Common common) {
        this.common = common;
    }


    @Given("the GetUser API request URL is configured")
    public void setGetUSerConfig(){
        common.request = common.setRequestSpecification(common.baseURI);
    }

    @Given("the client sets headers to GetUser API with:")
    public void clientGetUserServiceSetsHeaders(Map<String, String> parameters) {
        common.request = common.request.given().headers(parameters);
    }

    @When("the GetUser API request sent")
    public void profileServiceRequest() {
        common.response = common.request.when().get(common.usersAPIPath);
        System.out.println("response: " + common.response.prettyPrint());
    }

    @Then("^the GetUser API status code is (\\d+)$")
    public void profileServiceResCodeCheck(int statusCode) {
        common.json = common.response.then().statusCode(statusCode);
    }

    @And("GetUser API response includes the following$")
    public void profile_Service_response_equals(Map<String, String> responseFields) {
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if (StringUtils.isNumeric(field.getValue())) {
                common.json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
            } else {
                common.json.body(field.getKey(), equalTo(field.getValue()));
            }
        }
    }
}