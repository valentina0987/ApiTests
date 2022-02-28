package ApiTest;
import DataHelper.DataHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestAPI {
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/api")
            .setContentType(ContentType.JSON)
            .build();

    @Test
    public void checkGeorgeBluthMail() {
        given()
                .spec(requestSpec)
                .get("/users")
                .then()
                .statusCode(200)
                .body("data.findAll {it.first_name == 'George' && it.last_name == 'Bluth' }.email", hasItem("george.bluth@reqres.in"));
    }

    @Test
    public void checkMichaelLawsonhMail() {
        DataHelper dataHelper = new DataHelper();
        given()
                .spec(requestSpec)
                .get(dataHelper.getUriFromPaginationPage("Michael", "Lawson"))
                .then()
                .statusCode(200)
                .body("data.email", equalTo("michael.lawson@reqres.in"));
    }
}