package org.mouthaan.reqres.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserSteps {
    private RequestSpecification request;
    private Response response;

    @Given("a user exists with an id {int}")
    public void aUserExistsWithAnId(int id) {
        RestAssured.baseURI = "https://reqres.in/api/users";
        request = given().pathParam("userid", id);
    }


    @When("a user retrieves the user by id")
    public void aUserRetrievesTheUserById() {
        response = request.when().get("/{userid}");
    }


    @Then("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        System.out.println("Response: " + response.prettyPrint());
        //assertEquals(statusCode, response.getStatusCode());
        response.then().statusCode(equalTo(statusCode));
    }

    @And("the status line is {string}")
    public void theStatusLineIs(String statusLine) {
        //assertEquals(statusLine, response.getStatusLine());
        response.then().statusLine(equalTo(statusLine));
    }

    @And("the response header include the following")
    public void theResponseHeaderIncludeTheFollowing(Map<String, String> responseHeaderFields) {

        System.out.println("- Print all headers -");
        response.headers().forEach(header -> {
            System.out.println(header.getName() + " -> " + header.getValue());
        });

        responseHeaderFields.forEach((k, v) -> {
            response.then().header(k, equalTo(v));
        });
    }

    @And("response includes the following")
    public void responseIncludesTheFollowing(Map<String, String> responseFields) {
        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Using ValidatableResponse
        ValidatableResponse jsonResponse = response.then();

        responseFields.forEach((k, v) -> {

            if (StringUtils.isNumeric(v)) {
                assertEquals(Integer.valueOf(v), jsonPathEvaluator.get(k));
                jsonResponse.body(k, equalTo(Integer.parseInt(v)));
            } else {
                assertEquals(v, jsonPathEvaluator.get(k));
                jsonResponse.body(k, equalTo(v));
            }

        });


    }


}
