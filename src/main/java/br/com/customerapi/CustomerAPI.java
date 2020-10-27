package br.com.customerapi;
import static spark.Spark.*;

/**
 * The Customer Rest API main
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerAPI {

    public static void main(String[] args) {
        get("/", (req, res) -> {
            res.type("application/json");
            return "Hello, World!";
        });

        post("/test", (req, res) -> {
            res.type("application/json");
            System.out.println("Test of post method");
            res.status(200);
            return "{\"teste\":\"12345\"}";
//            return null;
        });
    }
}
