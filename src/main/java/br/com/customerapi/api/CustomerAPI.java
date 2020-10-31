package br.com.customerapi.api;

import br.com.customerapi.CustomerAPIMain;
import br.com.customerapi.model.Address;
import br.com.customerapi.model.Customer;
import br.com.customerapi.module.AppModule;
import br.com.customerapi.service.AddressService;
import br.com.customerapi.service.CustomerService;
import br.com.customerapi.util.APIError;
import br.com.customerapi.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerAPI {
    final static Logger log = LoggerFactory.getLogger(CustomerAPIMain.class);

    Injector injector = Guice.createInjector(new AppModule());
    CustomerService customerService = injector.getInstance(CustomerService.class);
    AddressService addressService = injector.getInstance(AddressService.class);

    private static CustomerAPI instance;

    private CustomerAPI() {
        initializeAPI();
    }

    /***
     * Initialize the Customer REST API
     */
    public static void initialize() {
        try {
            if (instance == null) {
                log.info("Initializing Customer API Service");
                instance = new CustomerAPI();
                log.info("Customer API Service initialization complete");
            } else {
                log.warn("Customer API Service is already initialized. It's running!");
            }

        } catch (Exception e) {
            log.error("Error on Customer API Service initializing");
            log.debug(e.getMessage());
        }
    }

    /**
     * Initialize the REST API
     */
    private void initializeAPI() {
        createCustomerEndpoint();
        listCustomersEndpoint();
        getCustomerByIdEndpoint();
        updateCustomerEndpoint();
        deleteCustomerEndpoint();
    }

    /**
     * Endpoint: Create Customer
     * path: /customers
     * type: post
     * <p>
     * Response:
     * 201: Success on customer creation
     * 400: Invalid request parameters
     * 500: Server internal error
     */
    private void createCustomerEndpoint() {
        post("/customers", (req, res) -> {
            res.type("application/json");
            try {
                log.info("POST:/customers");
                Customer customer;
                Address address;
                try {
                    String body = req.body();
                    // Create the customer
                    customer = customerService.createCustomer(body);
                    address = addressService.createAddress(body, customer.getCustomerId());
                    customer.getAddresses().add(address);
                    res.status(201);
                    return JsonUtils.toJson(customer);
                } catch (JsonProcessingException e) {
                    log.error("POST:/customers > Error while parsing JSON");
                    log.debug(e.getMessage());
                    res.status(500);
                    return "ERROR 500 - Server internal error";
                } catch (APIError e) {
                    res.status(400);
                    return e.getMessage();
                }
            } catch (Exception e) {
                res.status(500);
                log.error("Error on POST:/customers");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: List all customers
     * path: /customers
     * type: get
     * <p>
     * Response:
     * 200: Success on list customers
     * 204: No content - no customers saved on database
     * 500: Server internal error
     */
    private void listCustomersEndpoint() {
        get("/customers", (req, res) -> {
            res.type("application/json");
            try {
                log.info("GET:/customers");
                Map<String, String[]> params = req.queryMap().toMap();
                log.info("params keys: " + params.keySet().toString());
                List<Customer> customers = customerService.listCustomers(params);
                if (customers.isEmpty()) {
                    res.status(204);
                    return "No customers found";
                }

                return JsonUtils.toJson(customers);
            } catch (Exception e) {
                res.status(500);
                log.error("Error on GET:/customers");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: Get customer by ID
     * path: /customers/{id}
     * type: get
     * <p>
     * Response:
     * 200: Success on list customers
     * 404: Not found - no customers saved on database with the received ID
     * 500: Server internal error
     */
    private void getCustomerByIdEndpoint() {
        get("/customers/:id", (req, res) -> {
            String param = req.params("id");
            res.type("application/json");
            try {
                Long id = Long.parseLong(param);
                log.info("GET:/customers/" + id);
                Customer customer = customerService.getCustomer(id);
                if (customer == null) {
                    res.status(404);
                    return "No customer with id " + id + " found";
                }

                return JsonUtils.toJson(customer);
            } catch (Exception e) {
                res.status(500);
                log.error("Error on GET:/customers/" + param);
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: Update customer
     * path: /customers/{id}
     * type: put
     * <p>
     * Response:
     * 200: Success on update
     * 404: Not found - no customers saved on database with the received ID
     * 500: Server internal error
     */
    private void updateCustomerEndpoint() {
        put("/customers/:id", (req, res) -> {
            try {
                log.info("PUT:/customers/{id}");
                res.type("application/json");
                try {
                    String param = req.params("id");
                    Long id = Long.parseLong(param);
                    log.info("Search customer id " + id);
                    Customer customer = customerService.getCustomer(id);
                    if (customer == null) {
                        res.status(404);
                        return "Customer id " + id + " not found";
                    }
                    String body = req.body();
                    // Update the customer
                    customer = customerService.updateCustomer(body, customer);

                    // Update the customer addresses
                    if (body.contains("address")) addressService.updateAddress(body, customer.getCustomerId());
                    // Get the customer addresses list
                    final List<Address> addresses = addressService.listCustomerAddresses(customer.getCustomerId());
                    customer.getAddresses().addAll(addresses);
                    res.status(201);
                    return JsonUtils.toJson(customer);
                } catch (JsonProcessingException e) {
                    log.error("POST:/customers > Error while parsing JSON");
                    log.debug(e.getMessage());
                    res.status(500);
                    return "ERROR 500 - Server internal error";
                } catch (APIError e) {
                    res.status(400);
                    return e.getMessage();
                }
            } catch (Exception e) {
                res.status(500);
                log.error("Error on PUT:/customers");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: Delete customer
     * path: /customers/{id}
     * type: delete
     * <p>
     * Response:
     * 200: Success on delete
     * 404: Not found - no customers saved on database with the received ID
     * 500: Server internal error
     */
    private void deleteCustomerEndpoint() {
        delete("/customers/:id", (req, res) -> {
            log.info("DELETE:/customers/{id}");
            res.type("application/json");
            String param = req.params("id");
            Long id = Long.parseLong(param);
            try {
                log.info("Find the customer id " + id);
                Customer customer = customerService.getCustomer(id);
                if (customer == null) {
                    res.status(404);
                    return "Customer id " + id + " not found";
                }

                String msg = "";
                log.info("Delete customer id " + id);
                if (customerService.deleteCustomer(id) > 0) {
                    res.status(200);
                    msg = "Customer id " + id + " successfully deleted";
                    log.info(msg);
                    return msg;
                } else {
                    res.status(404);
                    msg = "Cannot delete customer id " + id;
                    log.warn(msg);
                    return msg;
                }
            } catch (Exception e) {
                res.status(500);
                log.error("Error on GET:/customers/" + param);
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }
}
