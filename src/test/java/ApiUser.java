
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ApiUser extends parameters {
    public static Response response;

    @Test
    public void ConsultaUser() {
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

    @Test
    public void ConsultaTodosUsers() {

        InputStream createBookingJsonSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("consultaTodosUserSchema.json");

        response = given()
                .when()
                .get("/users?page=2");
        response.then().statusCode(200);
        response.then().extract().path("id");
       
        response.then().assertThat()
                .body (JsonSchemaValidator. matchesJsonSchema (createBookingJsonSchema))

                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("support.url", equalTo("https://reqres.in/#support-heading"))
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }
}