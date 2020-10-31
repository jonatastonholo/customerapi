package br.com.customerapi;
import br.com.customerapi.api.*;

import static spark.Spark.get;

/**
 * The Customer Rest API main
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerAPIMain {

    public static void main(String[] args) {
        // Initialize the API REST
        CustomerAPI.initialize();
        AddressAPI.initialize();

        get("/", (req, res) -> {
            res.type("application/json");
            res.status(418);
            String json = "{\n" +
                    "\t\"author\" : \"Jonatas Ribeiro Tonholo\",\n" +
                    "\t\"firstReleaseDate\" : \"2020-10-31\",\n" +
                    "\t\"linkedin\" : \"https://www.linkedin.com/in/jonatastonholo/?locale=en_US\",\n" +
                    "\t\"message\" : \"There are no Easter Eggs in this program. ;)\"\n" +
                    "}";
            return json;
        });
    }
}
