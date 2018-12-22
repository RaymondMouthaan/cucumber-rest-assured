package org.mouthaan.jphapi.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;


import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserByIdSteps {
    private RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("a user exists with an id of {string}")
    public void aUserExistsWithAnIdOf(String id) {
        request = given().param("id", id);
    }

    @When("a user retrieves the user by id")
    public void aUserRetrievesTheUserById() {
        response = request.when().get("https://jsonplaceholder.typicode.com/users");
        System.out.println("response: " + response.prettyPrint());
    }

    @Then("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        json = response.then().statusCode(statusCode);
    }

    @And("response includes the following")
    public void responseIncludesTheFollowing(Map<String, String> responseFields) {
        responseFields.forEach((key, val) -> {
            if (StringUtils.isNumeric(val)) {
                json.body(key + "[0]", equalTo(Integer.parseInt(val)));
            }
            else {
                json.body(key + "[0]", equalTo(val));
            }
        });
    }
}
