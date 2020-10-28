package br.com.customerapi;
import static spark.Spark.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Customer Rest API main
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerAPI {
    final static Logger log = LoggerFactory.getLogger(CustomerAPI.class);

    public static void main(String[] args) {

        log.info("Customer REST API initializing...");

        get("/", (req, res) -> {
            res.type("application/json");
            log.info("Test get method");
            return "Hello, World!";
        });

        post("/test", (req, res) -> {
            res.type("application/json");
            log.debug("Test of post method");
            res.status(200);
            return "{\"teste\":\"12345\"}";
//            return null;
        });
    }
}
