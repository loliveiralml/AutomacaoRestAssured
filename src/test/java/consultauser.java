import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class consultauser {

    @BeforeClass
    public static void urlApi() {
        baseURI = "https://reqres.in/api";
    }

    @Test
    public void testStatusCode() {

        //RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
                .when()
                .get("/usuários/2");

       response.then().log().body();
       response.then().statusCode(200);

       response.then().assertThat().body("data.id", equalTo(2))
            .body("data.name", equalTo("fuchsia rose"))
            .body("data.year", equalTo(2001))
            .body("data.color", equalTo("#C74375"))
            .body("data.pantone_value", equalTo("17-2031"));

    }

}
