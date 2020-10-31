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

import static spark.Spark.*;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class AddressAPI {
    final static Logger log = LoggerFactory.getLogger(CustomerAPIMain.class);

    Injector injector = Guice.createInjector(new AppModule());
    AddressService addressService = injector.getInstance(AddressService.class);
    CustomerService customerService = injector.getInstance(CustomerService.class);

    private static AddressAPI instance;

    private AddressAPI() {
        initializeAPI();
    }

    /***
     * Initialize the Address REST API
     */
    public static void initialize() {
        try {
            if(instance == null) {
                log.info("Initializing Address API Service");
                instance = new AddressAPI();
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
        createAddressEndpoint();
        listAddressByCustomerIdEndpoint();
        getAddressByIdEndpoint();
        updateAddressEndpoint();
        deleteAddressEndpoint();
    }

    /***
     * Get the customer by id
     * @param id the customer id
     * @return the customer if is saved or null
     */
    private Customer getCustomerById(Long id) {
        log.info("Find custom id " + id);
        Customer customer = customerService.getCustomer(id);
        return customer;
    }

    /**
     * Endpoint: Create new address
     * path: /customers/{id}/addresses
     * type: post
     *
     * Response:
     *  201: Success on address creation
     *  400: Invalid request parameters
     *  404: Customer not found
     *  500: Server internal error
     */
    private void createAddressEndpoint() {
        post("/customers/:id/addresses", (req, res) -> {
            res.type("application/json");
            try {
                log.info("POST:/customers/{id}/addresses");
                // Get the id from params
                Long customerId = Long.parseLong(req.params("id"));
                // Get the customer
                Customer customer = getCustomerById(customerId);
                if (customer == null) {
                    res.status(404);
                    String msg = "No customer with id " + customerId + " found";
                    log.info(msg);
                    return msg;
                }

                try {
                    String body = req.body();
                    // Create the address
                    Address address = addressService.createAddress(body, customer.getCustomerId());
                    res.status(201);
                    return JsonUtils.toJson(address);

                } catch (JsonProcessingException e) {
                    log.error("POST:/customers/"+customerId+"/addresses > Error while parsing JSON");
                    log.debug(e.getMessage());
                    res.status(500);
                    return "ERROR 500 - Server internal error";
                } catch (APIError e) {
                    res.status(400);
                    log.error(e.getMessage());
                    return e.getMessage();
                }
            } catch (Exception e) {
                res.status(500);
                log.error("Error on POST:/customers/{id}/addresses");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: List all customer's addresses
     * path: /customers/{id}/addresses
     * type: get
     *
     * Response:
     *  200: Success on list customer's addresses
     *  404: No content - no customer's address saved on database
     *  500: Server internal error
     */
    private void listAddressByCustomerIdEndpoint() {
        get("/customers/:id/addresses", (req, res) -> {
            res.type("application/json");
            try {
                log.info("GET:/customers/{id}/addresses");
                // Get the id from params
                Long customerId = Long.parseLong(req.params("id"));
                // Get the customer
                Customer customer = getCustomerById(customerId);
                if (customer == null) {
                    res.status(404);
                    String msg = "No customer with id " + customerId + " was found";
                    log.info(msg);
                    return msg;
                }

                // Find the addresses
                List<Address> addresses = addressService.listCustomerAddresses(customer.getCustomerId());
                if (addresses.isEmpty()) {
                    res.status(404);
                    String msg = "No addresses found for customer id " + customerId;
                    log.info(msg);
                    return msg;
                }

                res.status(200);
                return JsonUtils.toJson(addresses);


            } catch (Exception e) {
                res.status(500);
                log.error("Error on GET:/customers/{id}/addresses");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: Get customer's address by address ID
     * path: /customers/{id}/addresses/{address_id}
     * type: get
     *
     * Response:
     *  200: Address found
     *  404: Address / Customer Not found
     *  500: Server internal error
     */
    private void getAddressByIdEndpoint() {
        get("/customers/:id/addresses/:address_id", (req, res) -> {
            res.type("application/json");
            try {
                log.info("GET:/customers/{id}/addresses/{address_id}");
                // Get the id from params
                Long customerId = Long.parseLong(req.params("id"));
                // Get the customer
                Customer customer = getCustomerById(customerId);
                if (customer == null) {
                    res.status(404);
                    String msg = "No customer with id " + customerId + " was found";
                    log.info(msg);
                    return msg;
                }
                Long addressId = Long.parseLong(req.params("address_id"));
                // Get the address
                Address address = addressService.getCustomerAddressByAddressId(customerId, addressId);
                if (address == null) {
                    res.status(404);
                    String msg = "No address with id " + addressId + " was found for customer id " + customerId;
                    log.info(msg);
                    return msg;
                }

                res.status(200);
                return JsonUtils.toJson(address);


            } catch (Exception e) {
                res.status(500);
                log.error("Error on GET:/customers/{id}/addresses");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: Update address
     * path: /customers/{id}/addresses/{address_id}
     * type: put
     *
     * Response:
     *  200: Success on update
     *  404: Customer / Address Not found
     *  500: Server internal error
     */
    private void updateAddressEndpoint() {
        put("/customers/:id/addresses/:address_id", (req, res) -> {
            res.type("application/json");
            try {
                log.info("PUT:/customers/{id}/addresses/{address_id}");
                // Get the id from params
                Long customerId = Long.parseLong(req.params("id"));
                // Get the customer
                Customer customer = getCustomerById(customerId);
                if (customer == null) {
                    res.status(404);
                    String msg = "No customer with id " + customerId + " was found";
                    log.info(msg);
                    return msg;
                }
                Long addressId = Long.parseLong(req.params("address_id"));
                // Get the address
                Address address = addressService.getCustomerAddressByAddressId(customerId, addressId);
                if (address == null) {
                    res.status(404);
                    String msg = "No address with id " + addressId + " was found for customer id " + customerId;
                    log.info(msg);
                    return msg;
                }

                try {
                    String body = req.body();
                    // update the address
                    address = addressService.updateAddress(body, customer.getCustomerId(), addressId);
                    res.status(200);
                    return JsonUtils.toJson(address);

                } catch (JsonProcessingException e) {
                    log.error("PUT:/customers/"+customerId+"/addresses/"+addressId+" > Error while parsing JSON");
                    log.debug(e.getMessage());
                    res.status(500);
                    return "ERROR 500 - Server internal error";
                } catch (APIError e) {
                    res.status(400);
                    log.error(e.getMessage());
                    return e.getMessage();
                }
            } catch (Exception e) {
                res.status(500);
                log.error("Error on PUT:/customers/{id}/addresses");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }

    /**
     * Endpoint: Delete address
     * path: /customers/{id}/addresses/{address_id}
     * type: delete
     *
     * Response:
     *  200: Success on update
     *  404: Address Not found
     *  500: Server internal error
     */
    private void deleteAddressEndpoint() {
        delete("/customers/:id/addresses/:address_id", (req, res) -> {
            res.type("application/json");
            try {
                log.info("DELETE:/customers/{id}/addresses/{address_id}");
                // Get the id from params
                final Long customerId = Long.parseLong(req.params("id"));
                // Get the customer
                Customer customer = getCustomerById(customerId);
                if (customer == null) {
                    res.status(404);
                    String msg = "No customer with id " + customerId + " was found";
                    log.info(msg);
                    return msg;
                }
                final Long addressId = Long.parseLong(req.params("address_id"));
                // Get the address
                final Address address = addressService.getCustomerAddressByAddressId(customerId, addressId);
                if (address == null) {
                    res.status(404);
                    String msg = "No address with id " + addressId + " was found for customer id " + customerId;
                    log.info(msg);
                    return msg;
                }

                // update the address
                String msg = "";
                log.info("Delete address id " + addressId);
                if (addressService.deleteAddress(customerId, addressId) > 0) {
                    res.status(200);
                    msg = "Address id " + addressId + " from customer id " + customerId +" successfully deleted";
                    log.info(msg);
                    return msg;
                } else {
                    res.status(404);
                    msg = "Cannot delete Address id " + addressId + " from customer id " + customerId;
                    log.warn(msg);
                    return msg;
                }

            } catch (Exception e) {
                res.status(500);
                log.error("Error on PUT:/customers/{id}/addresses");
                log.debug(e.getMessage());
                return "ERROR 500 - Server internal error";
            }
        });
    }
}
