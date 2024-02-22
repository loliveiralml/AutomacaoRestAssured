import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class parameters {
   @BeforeTest
    public static void urlApi() {
        RestAssured.baseURI = "https://reqres.in/api";
    }
}