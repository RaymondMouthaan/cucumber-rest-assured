package org.mouthaan.weather.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.mouthaan.weather.model.RegistrationFailureResponse;
import org.mouthaan.weather.model.RegistrationSuccessResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationPostTest {

    @Test
    void RegistrationSuccessful() {
        RestAssured.baseURI ="http://restapi.demoqa.com/customer";
        RequestSpecification request = RestAssured.given();

        // JSONObject is a class that represents a Simple JSON.
        // We can add Key - Value pairs using the put method
        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender2");
        requestParams.put("LastName", "Singh2");

        requestParams.put("UserName", "simpleuser002");
        requestParams.put("Password", "password2");
        requestParams.put("Email",  "someuser2@gmail.com");

        // Add a header stating the Request body is a JSON
        request.header("Content-Type", "application/json");

        // Add the Json to the body of the request
        request.body(requestParams.toJSONString());

        // Post the request and check the response
        Response response = request.post("/register");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String successCode = response.jsonPath().get("SuccessCode");

        System.out.println(response.asString());

        assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
    }

    @Test
    void RegistrationUnsuccessful() {
        RestAssured.baseURI ="http://restapi.demoqa.com/customer";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender"); // Cast
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "sdimpleuser2dd2011");
        requestParams.put("Password", "password1");
        requestParams.put("Email",  "sample2ee26d9@gmail.com");

        request.body(requestParams.toJSONString());
        Response response = request.get("/register"); // <- wrong, GET method instead of POST method

        int statusCode = response.getStatusCode(); // <- results in status 405
        System.out.println("The status code received: " + statusCode);

        System.out.println("Response body: " + response.body().asString());
    }

    @Test
    void RegistrationUnsuccessfulWithDeserializeJSONResponseToClass () {
        RestAssured.baseURI ="http://restapi.demoqa.com/customer";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender"); // Cast
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "63userf2d3d2011");
        requestParams.put("Password", "password1");
        requestParams.put("Email",  "ed26dff39@gmail.com");

        request.body(requestParams.toJSONString());
        Response response = request.post("/register");

        ResponseBody body = response.getBody();

        // Deserialize the Response body into RegistrationFailureResponse
        RegistrationFailureResponse responseBody = body.as(RegistrationFailureResponse.class);

        // Use the RegistrationFailureResponse class instance to Assert the values of
        // Response.
        assertEquals("User already exists", responseBody.FaultId);
        assertEquals("FAULT_USER_ALREADY_EXISTS", responseBody.fault);
    }

    @Test
    void RegistrationSuccessfulAnFailure()
    {
        RestAssured.baseURI ="http://restapi.demoqa.com/customer";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender"); // Cast
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "63userf2d3d2011");
        requestParams.put("Password", "password1");
        requestParams.put("Email",  "ed26dff39@gmail.com");

        request.body(requestParams.toJSONString());
        Response response = request.post("/register");

        ResponseBody body = response.getBody();
        System.out.println(response.body().asString());

        if(response.statusCode() == 200)
        {
            // Deserialize the Response body into RegistrationFailureResponse
            RegistrationFailureResponse responseBody = body.as(RegistrationFailureResponse.class);

            // Use the RegistrationFailureResponse class instance to Assert the values of
            // Response.
            assertEquals("User already exists", responseBody.FaultId);
            assertEquals("FAULT_USER_ALREADY_EXISTS", responseBody.fault);
        }
        else if (response.statusCode() == 201)
        {
            // Deserialize the Response body into RegistrationSuccessResponse
            RegistrationSuccessResponse responseBody = body.as(RegistrationSuccessResponse.class);
            // Use the RegistrationSuccessResponse class instance to Assert the values of
            // Response.
            assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode);
            assertEquals("Operation completed successfully", responseBody.Message);
        }
    }

}
