import groovy.lang.GString;
import io.restassured.RestAssured;
import org.apache.groovy.parser.antlr4.GroovyParser;
import org.testng.annotations.BeforeTest;

public class parameters {
   @BeforeTest
    public static void urlApi() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    public static String parmdelayed() {

        return "3";
    }
}

