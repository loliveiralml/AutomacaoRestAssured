
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class consultauser extends parameters {
    public static Response response;

    @Test
    public void testStatusCode() {
        response = given()
                .when()
                .get("/usuários/2");
        response.then().statusCode(200);

        response.then().assertThat().body("data.id", equalTo(2))
                .body("data.name", equalTo("fuchsia rose"))
                .body("data.year", equalTo(2001))
                .body("data.color", equalTo("#C74375"))
                .body("data.pantone_value", equalTo("17-2031"));
    }
}