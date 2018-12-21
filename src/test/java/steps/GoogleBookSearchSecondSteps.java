package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class GoogleBookSearchSecondSteps {

    private final StepData stepData;

    public GoogleBookSearchSecondSteps(StepData stepData) {
        this.stepData = stepData;
    }

    @Then("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        stepData.json = stepData.response.then().statusCode(statusCode);
    }

    /**
     * asserts on json fields with single values
     */
    @And("response includes the following")
    public void responseIncludesTheFollowing(Map<String, String> responseFields) {
        responseFields.forEach((k, v) -> {
            if (StringUtils.isNumeric(v)) {
                stepData.json.body(k, equalTo(Integer.parseInt(v)));
            } else {
                stepData.json.body(k, equalTo(v));
            }
        });
    }
}
