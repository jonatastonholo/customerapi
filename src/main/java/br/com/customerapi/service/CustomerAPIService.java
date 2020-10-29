package br.com.customerapi.service;

import br.com.customerapi.CustomerAPI;
import br.com.customerapi.dao.CustomerDao;
import br.com.customerapi.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static spark.Spark.*;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerAPIService {
    final static Logger log = LoggerFactory.getLogger(CustomerAPI.class);
    private static CustomerAPIService instance;

    private CustomerAPIService() {
        initializeAPI();
    }

    /***
     * Initialize the Customer REST API
     */
    public static void initialize() {
        try {
            if(instance == null) {
                log.info("Initializing Customer API Service");
                instance = new CustomerAPIService();
                log.info("Customer API Service initialization complete");
            }
            else {
                log.warn("Customer API Service is already initialized. It's running!");
            }

        }
        catch (Exception e) {
            log.error("Error on Customer API Service initializing");
            log.debug(e.getMessage());
        }
    }

    /**
     * Initialize the REST API
     */
    private void initializeAPI() {
        /**
         * Endpoint: Create Customer
         * path: /customers
         * type: post
         *
         * Response:
         *  201: Success on customer creation
         *  400: Invalid request parameters
         *  500: Server internal error
         */
        post("/customers", (req, res) -> {
            res.type("application/json");
            return "{\"teste\":\"12345\"}";
        });

        /**
         * Endpoint: List all customers
         * path: /customers
         * type: get
         *
         * Response:
         *  200: Success on list customers
         *  204: No content - no customers saved on database
         *  500: Server internal error
         */
        get("/customers", (req, res) -> {
            res.type("application/json");
            log.info("Test get method");

            CustomerDao dao = new CustomerDao();
            List<Customer> customerNames = dao.listCustomers();
            log.info(customerNames.get(0).getName());
            return customerNames.toString();
        });

        /**
         * Endpoint: Get customer by ID
         * path: /customers/{id}
         * type: get
         *
         * Response:
         *  200: Success on list customers
         *  404: Not found - no customers saved on database with the received ID
         *  500: Server internal error
         */
        get("/customers/{id}", (req, res) -> {
            res.type("application/json");
            return "";
        });

        /**
         * Endpoint: Update customer
         * path: /customers/{id}
         * type: put
         *
         * Response:
         *  200: Success on update
         *  404: Not found - no customers saved on database with the received ID
         *  500: Server internal error
         */
        put("/customers/{id}", (req, res) -> {
            res.type("application/json");
            return "";
        });

        /**
         * Endpoint: Delete customer
         * path: /customers/{id}
         * type: delete
         *
         * Response:
         *  200: Success on update
         *  404: Not found - no customers saved on database with the received ID
         *  500: Server internal error
         */
        delete("/customers/{id}", (req, res) -> {
            res.type("application/json");
            return "";
        });
    }
}
