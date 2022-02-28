package DataHelper;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class DataHelper {
    /**
     * Метод помогает найти email пользователя на страницах с пагинацией.
     */
    public String getUriFromPaginationPage(String first_name, String last_name) {
        String uri = "";
        int total = given()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .extract()
                .path("total");

        for(int i=1; i<total; i++) {
            String number = Integer.toString(i);
            uri = "https://reqres.in/api/users/" + number;
            String a = given()
                    .contentType(ContentType.JSON)
                    .get(uri).then()
                    .statusCode(200)
                    .extract().asString();
            if (a.contains(String.format("\"first_name\":\"%s\",\"last_name\":\"%s\"", first_name, last_name))) {
                return uri;
            }
        }
        return uri;
    }
}
