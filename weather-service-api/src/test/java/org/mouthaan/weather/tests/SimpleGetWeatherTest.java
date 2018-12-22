package org.mouthaan.weather.tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleGetWeatherTest {

    @Test
    void getWeatherDetails() {

        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = RestAssured.given();

        // Make a request to the server by specifying the method Type and the method URL.
        // This will return the Response from the server. Store the response in a variable.
        Response response = httpRequest.request(Method.GET, "/Amsterdam");

        // Now let us print the body of the message to see what response
        // we have recieved from the server
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
    }

    @Test
    void getWeatherDetailsAndValidateStatusCode() {

        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = RestAssured.given();

        // Make a request to the server by specifying the method Type and the method URL.
        // This will return the Response from the server. Store the response in a variable.
        Response response = httpRequest.request(Method.GET, "/Amsterdam");

        // Get the status code from the Response. In case of
        // a successfull interaction with the web service, we
        // should get a status code of 200.
        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        assertEquals(200, statusCode);

    }

    @Test
    void getWeatherDetailsInvalidCity() {

        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = RestAssured.given();

        // Make a request to the server by specifying the method Type and the method URL.
        // This will return the Response from the server. Store the response in a variable.
        Response response = httpRequest.request(Method.GET, "/78789798798");

        // Get the status code from the Response. In case of
        // a successfull interaction with the web service, we
        // should get a status code of 200.
        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        assertEquals(400, statusCode);

    }

    @Test
    void getWeatherStatusLine() {

        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = RestAssured.given();

        // Make a request to the server by specifying the method Type and the method URL.
        // This will return the Response from the server. Store the response in a variable.
        Response response = httpRequest.request(Method.GET, "/Hyderabad");

        // Get the status line from the Response and store it in a variable called statusLine
        String statusLine = response.getStatusLine();

        // Assert that correct status code is returned.
        assertEquals("HTTP/1.1 200 OK", statusLine);

    }

    @Test
    void getWeatherHeaders() {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/Hyderabad");

        // Reader header of a give name. In this line we will get
        // Header named Content-Type
        String contentType = response.getHeader("Content-Type");
        System.out.println("Content-Type value: " + contentType);

        // Reader header of a give name. In this line we will get
        // Header named Server
        String serverType =  response.getHeader("Server");
        System.out.println("Server value: " + serverType);

        // Reader header of a give name. In this line we will get
        // Header named Content-Encoding
        String acceptLanguage = response.getHeader("Content-Encoding");
        System.out.println("Content-Encoding: " + acceptLanguage);

    }

    @Test
    void iteratingOverHeaders()
    {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Hyderabad");

        // Get all the headers. Return value is of type Headers.
        // Headers class implements Iterable interface, hence we
        // can apply an advance for loop to go through all Headers
        // as shown in the code below
        Headers allHeaders = response.headers();

        // Iterate over all the Headers
        allHeaders.forEach(header -> System.out.println("key: " + header.getName() + " -> value: " + header.getValue()));
    }

    @Test
    void getWeatherHeadersAndValidate()
    {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/Hyderabad");

        // Reader header of a give name. In this line we will get
        // Header named Content-Type
        String contentType = response.getHeader("Content-Type");
        assertEquals("application/json", contentType);

        // Reader header of a give name. In this line we will get
        // Header named Server
        String serverType =  response.getHeader("Server");
        assertEquals("nginx/1.14.1", serverType);

        // Reader header of a give name. In this line we will get
        // Header named Content-Encoding
        String acceptLanguage = response.getHeader("Content-Encoding");
        assertEquals("gzip", acceptLanguage);
    }

    @Test
    void getWeatherMessageBody() {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        //Response response = httpRequest.request(Method.GET, "/Hyderabad");
        Response response = httpRequest.get("/Hyderabad");

        // Retrieve the body of the Response
        ResponseBody responseBody = response.getBody();

        System.out.println("Response Body is: " + responseBody.asString());
//        System.out.println("Response Body is: " + responseBody.prettyPrint());
    }

    @Test
    void getWeatherMessageBodyAndValidate() {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        //Response response = httpRequest.request(Method.GET, "/Hyderabad");
        Response response = httpRequest.get("/Hyderabad");

        // Retrieve the body of the Response
        ResponseBody body = response.getBody();

        // To check for sub string presence get the Response body as a String.
        // Do a String.contains
        String bodyAsString = body.asString();

        // convert the body into lower case and then do a comparison to ignore casing.
        assertTrue(bodyAsString.toLowerCase().contains("hyderabad"));
    }

    @Test
    void verifyCityInJsonResponse()
    {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Hyderabad");

        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Then simply query the JsonPath object to get a String value of the node
        // specified by JsonPath: City (Note: You should not put $. in the Java code)
        String city = jsonPathEvaluator.get("City");

        // Let us print the city variable to see what we got
        System.out.println("City received from Response " + city);

        // Validate the response
        assertEquals( "Hyderabad", city, "Correct city name received in the Response");
    }

    @Test
    void displayAllNodesInWeatherAPI() {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Amsterdam");

        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Let us print the city variable to see what we got
        System.out.println("City received from Response " + jsonPathEvaluator.get("City"));

        // Print the temperature node
        System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));

        // Print the humidity node
        System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));

        // Print weather description
        System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));

        // Print Wind Speed
        System.out.println("WindSpeed received from Response " + jsonPathEvaluator.get("WindSpeed"));

        // Print Wind Direction Degree
        System.out.println("Wind direction degree received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
    }
}