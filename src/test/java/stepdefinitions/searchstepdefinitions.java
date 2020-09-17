package stepdefinitions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class searchstepdefinitions
{
    private static String sessionToken = "f7y5o5eq9df8fq8eyf66d2txw4tglgke";
    private static String baseUri = "https://magento.abox.co.za/rest/V1/";
    private static Map<String, String> headers = new HashMap<>();// Hash Map
    private static RequestSpecification request;
    private static Response response;
    private static String responseString;
    private static String apiPassword;
    private static String apiUserName;
    private static String productCatalogPath;
    private static String loginPath;


    @Given("that the customer is on the Home page")
    public void that_the_customer_is_on_the_Home_page()
    {
        //Variable needed to log onto API
        baseUri = "https://magento.abox.co.za/rest/V1/";
        loginPath = "integration/admin/token";

        //String productCatalogPath = "products";
        productCatalogPath = "products";
        apiUserName = "training_api_user";
        apiPassword = "PtkekYqgRZW8pCVN";
        sessionToken = "";

        //get session token using rest assured
        sessionToken =
                given()
                        .baseUri(baseUri)
                        .basePath(loginPath)
                        .queryParam("username", apiUserName)
                        .queryParam("password", apiPassword)
                        .when()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body().asString();

        System.out.println(sessionToken.toString());

    }

    @Given("enters a product name in the search field")
    public void enters_a_product_name_in_the_search_field()
    {
        // Write code here that turns the phrase above into concrete actions
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", sessionToken);
        request = given()
                .headers(headers)
                .baseUri(baseUri)
                .basePath("search")
                .queryParam("searchCriteria[requestName]","quick_search_container")
                .queryParam("searchCriteria[filter_groups][0][filters][0][field]","search_term")
                .queryParam("searchCriteria[filter_groups][0][filters][0][value]","pants");


    }
    @When("the customer clicks the search icon to search")
    public void the_customer_clicks_the_search_icon_to_search() {
        // Write code here that turns the phrase above into concrete actions
                response = request.when().get();

    }

    @Then("the system should return a list of search results")
    public void the_system_should_return_a_list_of_search_results()
    {
        // Write code here that turns the phrase above into concrete actions
        responseString = response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("total_count",equalTo(13))
                .extract()
                .body().asString();
        System.out.println("Response String is: " +responseString);

    }
}