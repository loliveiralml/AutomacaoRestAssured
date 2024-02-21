import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class parameters {
   @BeforeClass
    public static void urlApi() {
        RestAssured.baseURI = "https://reqres.in/api";
    }
}