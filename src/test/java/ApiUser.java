import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ApiUser extends parameters {
    public static Response response;
    String parm1 = "";
    String parm2 = "";

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

        InputStream createBookingJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("consultaTodosUserSchema.json");

        response = given()
                .when()
                .get("/users?page=2");
        response.then().statusCode(200);
        response.then().extract().path("id");

        assert createBookingJsonSchema != null;
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createBookingJsonSchema))

                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("support.url", equalTo("https://reqres.in/#support-heading"))
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void NotFoundUser() {
        response = given()
                .when()
                .get("/usuários/99");
        response.then().statusCode(404);
        System.out.println("Observ : Rotina NotFoundUser Retornou Status Code :  " + response.then().extract().response().statusCode());
    }

    //Criar o delete do processo de criação
    @Test
    public void CreateUser() {

        given().header("Content-type", "application/json")

                //adding post method with payload from external file
                .body(payload.loadCreateUser()).when()
                .post("/users")
                .then().log().all()
                .assertThat().statusCode(201)

                .body("name", is(equalTo(payloadOut.ReponseNameCreateUser())))
                .body("job", is(equalTo(payloadOut.ReponsejobCreateUser())))
                .body("id", (not(emptyOrNullString())))
                .body("createdAt", (not(emptyOrNullString())));

    }

    @Test
    public void UpdateUser() {

        given().header("Content-type", "application/json")

                //adding post method with payload from external file
                .body(payload.updateUser()).when()
                .put("/users/2")
                .then().log().all()

                .assertThat().statusCode(200)


                .body("updatedAt", (not(emptyOrNullString())))
                .body("name", is(equalTo(payloadOut.ReponseUpdateUser())))
                .body("job", is(equalTo(payloadOut.ReponseUpdateJob())));


    }

    @Test
    public void DeleteUser() {

        given().header("Content-type", "application/json")

                //adding post method with payload from external file
                .when()
                .delete("/users/2")
                .then().log().all()

                .assertThat().statusCode(204);
//
    }

    @Test
    public void LoginUser() {

        given().header("Content-type", "application/json")

                //adding post method with payload from external file
                .body(payload.loginUser()).when()
                .post("/login")
                .then().log().all()

                .assertThat()
                .statusCode(400)
                .body("error", is(equalTo(payloadOut.errorReponseLoginUser())));


    }

    @Test
    public void ParamDelayed() {

        InputStream createBookingJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("consultaTodosUserSchema.json");

        response = given().log().all()
                .when()
                .queryParam("delay", parameters.parmdelayed())
                .get("/users/");
        response.then().statusCode(200);

        assert createBookingJsonSchema != null;
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createBookingJsonSchema))

                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("support.url", equalTo("https://reqres.in/#support-heading"))
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }
}