package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class GoogleBookFirstSearch {
    private static final URI ENDPOINT_GET_BOOK_BY_ISBN = URI.create("https://www.googleapis.com/books/v1/volumes");
    private StepData stepData;

    public GoogleBookFirstSearch(StepData stepData) {
        this.stepData = stepData;
    }

    @Given("a book exists with an isbn of {string}")
    public void aBookExistsWithAnIsbnOf(String isbn) {
        stepData.request = given().param("q", "isbn:" + isbn);
    }

    @When("a user retrieves the book by isbn")
    public void aUserRetrievesTheBookByIsbn() {
        stepData.response = stepData.request.when().get(ENDPOINT_GET_BOOK_BY_ISBN);
        System.out.println("response: " + stepData.response.prettyPrint());
    }

    @And("response includes the following in any order")
    public void responseIncludesTheFollowingInAnyOrder(Map<String, String> responseFields) {
        responseFields.forEach((k, v) -> {
            if (StringUtils.isNumeric(v)) {
                stepData.json.body(k, containsInAnyOrder(Integer.parseInt(v)));
            } else {
                stepData.json.body(k, containsInAnyOrder(v));
            }
        });
    }
}
