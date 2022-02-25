package ApiTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import io.restassured.http.ContentType;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.*;

public class TestAPI {
    public String getUri() {
        String Uri = "";
        for(int i=1; i<12; i++) {
            String number = Integer.toString(i);
            Uri = "https://reqres.in/api/users/" + number;
            String a = given()
                    .contentType(ContentType.JSON)
                    .get(Uri).then()
                    .statusCode(200)
                    .extract().asString();
            if (a.contains("\"first_name\":\"Michael\",\"last_name\":\"Lawson\"")) {
                return Uri;
            }
        }
        return Uri;
    }

    @Test
    public void checkGeorgeBluthMail() {
        final String BASE_URI = "https://reqres.in/api";
        RestAssured.
                given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .get("/users")
                .then()
                .statusCode(200)
                .body("data.findAll {it.first_name == \"George\" && it.last_name == \"Bluth\" }.email", hasItem("george.bluth@reqres.in"));

    }

    @Test
    public void checkMichaelLawsonhMail() throws InterruptedException {
        String Uri = getUri();
        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users/7")
                .then()
                .statusCode(200)
                .body("data.email", equalTo("michael.lawson@reqres.in"));

    }


}
