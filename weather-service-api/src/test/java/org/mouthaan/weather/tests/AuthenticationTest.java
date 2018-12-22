package org.mouthaan.weather.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

public class AuthenticationTest {

    @Test
    void AuthenticationBasics() {
        RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication";
        RequestSpecification request = RestAssured.given();

        Response response = request.get();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Status Message: " + response.getBody().asString());
    }
}
