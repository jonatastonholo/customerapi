package br.com.customerapi.service;

import br.com.customerapi.CustomerAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class AddressAPIService {
    final static Logger log = LoggerFactory.getLogger(CustomerAPI.class);
    private static AddressAPIService instance;

    private AddressAPIService() {
        initializeAPI();
    }

    /***
     * Initialize the Address REST API
     */
    public static void initialize() {
        try {
            if(instance == null) {
                log.info("Initializing Address API Service");
                instance = new AddressAPIService();
                log.info("Address API Service initialization complete");
            }
            else {
                log.warn("Address API Service is already initialized. It's running!");
            }

        }
        catch (Exception e) {
            log.error("Error on Address API Service initializing");
            log.debug(e.getMessage());
        }
    }

    /**
     * Initialize the REST API
     */
    private void initializeAPI() {
        /**
         * Endpoint: Create new address
         * path: /customers/{id}/addresses
         * type: post
         *
         * Response:
         *  201: Success on address creation
         *  400: Invalid request parameters
         *  500: Server internal error
         */
        post("/customers/{id}/addresses", (req, res) -> {
            res.type("application/json");
            return "{\"teste\":\"12345\"}";
        });


        /**
         * Endpoint: List all customer's addresses
         * path: /customers/{id}/addresses
         * type: get
         *
         * Response:
         *  200: Success on list customer's addresses
         *  204: No content - no customer's address saved on database
         *  500: Server internal error
         */
        get("/customers/{id}/addresses", (req, res) -> {
            res.type("application/json");
            log.info("Test get method");

//            CustomerDao dao = new CustomerDao();
//            List<Customer> customerNames = dao.listCustomers();
//            log.info(customerNames.get(0).getName());
//            return customerNames.toString();
            return "";
        });

        /**
         * Endpoint: Get customer's address by address ID
         * path: /customers/{id}/addresses/{address_id}
         * type: get
         *
         * Response:
         *  200: Address found
         *  404: Address Not found
         *  500: Server internal error //////
         */
        get("/customers/{id}/addresses/{address_id}", (req, res) -> {
            res.type("application/json");
            return "";
        });

        /**
         * Endpoint: Update address
         * path: /customers/{id}/addresses/{address_id}
         * type: put
         *
         * Response:
         *  200: Success on update
         *  404: Address Not found
         *  500: Server internal error /////
         */
        put("/customers/{id}/addresses/{address_id}", (req, res) -> {
            res.type("application/json");
            return "";
        });

        /**
         * Endpoint: Delete address
         * path: /customers/{id}/addresses/{address_id}
         * type: delete
         *
         * Response:
         *  200: Success on update
         *  404: Address Not found
         *  500: Server internal error ///
         */
        delete("/customers/{id}/addresses/{address_id}", (req, res) -> {
            res.type("application/json");
            return "";
        });
    }
}
