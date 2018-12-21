package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class GoogleBookSearch {
    private static final URI ENDPOINT_GET_BOOK_BY_ISBN = URI.create("https://www.googleapis.com/books/v1/volumes");
    private RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    @Given("a book exists with an isbn of {string}")
    public void aBookExistsWithAnIsbnOf(String isbn) {
        request = given().param("q", "isbn:" + isbn);
    }

    @When("a user retrieves the book by isbn")
    public void aUserRetrievesTheBookByIsbn() {
        response = request.when().get(ENDPOINT_GET_BOOK_BY_ISBN);
        System.out.println("response: " + response.prettyPrint());
    }

    @Then("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        json = response.then().statusCode(statusCode);
    }

    /**
     * asserts on json fields with single values
     */
    @And("response includes the following")
    public void responseIncludesTheFollowing(Map<String, String> responseFields) {
        responseFields.forEach((k, v) -> {
            if (StringUtils.isNumeric(v)) {
                json.body(k, equalTo(Integer.parseInt(v)));
            } else {
                json.body(k, equalTo(v));
            }
        });
    }

    @And("response includes the following in any order")
    public void responseIncludesTheFollowingInAnyOrder(Map<String, String> responseFields) {
        responseFields.forEach((k, v) -> {
            if (StringUtils.isNumeric(v)) {
                json.body(k, containsInAnyOrder(Integer.parseInt(v)));
            } else {
                json.body(k, containsInAnyOrder(v));
            }
        });
    }
}
