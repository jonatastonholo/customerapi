package br.com.customerapi;
import static spark.Spark.*;

import br.com.customerapi.dao.CustomerDao;
import br.com.customerapi.factory.MySQLFactory;
import br.com.customerapi.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

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

            MySQLFactory.initialize();
            CustomerDao dao = new CustomerDao();
            List<Customer> customerNames = dao.listCustomers();
            log.info(customerNames.get(0).getName());
            return customerNames.toString();
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
