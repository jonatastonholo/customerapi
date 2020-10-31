package br.com.customerapi.service;

import br.com.customerapi.CustomerAPIMain;
import br.com.customerapi.dao.CustomerDao;
import br.com.customerapi.model.Customer;
import br.com.customerapi.model.EAPIError;
import br.com.customerapi.module.AppModule;
import br.com.customerapi.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerService {
    final static Logger log = LoggerFactory.getLogger(CustomerAPIMain.class);
    Injector injector = Guice.createInjector(new AppModule());
    CustomerDao customerDao = injector.getInstance(CustomerDao.class);

    /**
     * Parse the customer from JSON and remove the mask of the fields
     * @param json a json with customer
     * @return a customer
     * @throws JsonProcessingException
     */
    private Customer parseCustomer(String json) throws JsonProcessingException {
        // Parse the customer from body
        Customer customer = JsonUtils.fromJson(json, Customer.class);
        // save without mask
        customer.setCpf(StringUtils.removeCpfMask(customer.getCpf()));
        return customer;
    }

    /***
     * Create a customer parsing from a JSON and save on database
     * @param json with customer
     * @return a created customer
     * @throws JsonProcessingException
     * @throws APIError
     */
    public Customer createCustomer(String json) throws APIError, JsonProcessingException {
        return saveCustomer(parseCustomer(json), false);
    }

    /***
     * Save the customer on database
     * @param customer The customer to save
     * @param update a flag indication for update transaction
     * @return the saved customer
     * @throws APIError
     */
    public Customer saveCustomer(Customer customer, boolean update) throws APIError {
        // Validate (will throw APIError exception on fail)
        if(customerBusinessRulesCheck(customer, update)) {
            if(update) {
                if(customerDao.updateCustomer(customer) <= 0) throw new APIError("Unable to update customer", EAPIError.UPDATE);
            }
            else {
                if(customerDao.createCustomer(customer) <= 0) throw new APIError("Unable to create customer", EAPIError.SAVE);
                // Get the created customer to get the customer ID
                customer = customerDao.getCustomerByCPF(customer.getCpf());
            }
        }
        return customer;
    }

    /**
     * List customers with or without filters
     * @param filters a optional filter to list customers
     * @return a list with customers
     */
    public List<Customer> listCustomers(Map<String, String[]> filters) {
        try {
            log.info("List customers");
            return customerDao.listCustomers(filters);
        }
        catch (Exception e) {
            log.error("Error on list customers");
            return new ArrayList<>();
        }
    }

    /***
     * Get the customer by ID
     * @param customerId the customer id
     * @return the customer or null
     */
    public Customer getCustomer(Long customerId) {
        try {
            log.info("Get customer by ID");
            return customerDao.getCustomer(customerId);
        }
        catch (Exception e) {
            log.error("Error on list customers");
            return null;
        }
    }

    /***
     * Update the customer
     * @param json a json with the customer to update
     * @return a updated customer
     * @throws JsonProcessingException
     * @throws APIError
     */
    public Customer updateCustomer(String json, Customer customer) throws JsonProcessingException, APIError {
        Customer parsedCustomer = parseCustomer(json);
        parsedCustomer.setCustomerId(customer.getCustomerId());
        parsedCustomer.setUuid(customer.getUuid());
        parsedCustomer.setCreatedAt(customer.getCreatedAt());
        parsedCustomer.setUpdatedAt(ClockUtils.getTimestampNow());
        return saveCustomer(parsedCustomer, true);
    }

    /**
     * Delete the customer by ID
     * @param customerId customer ID
     * @return the number of affected rows
     */
    public Integer deleteCustomer(Long customerId) {
        return customerDao.deleteCustomer(customerId);
    }

    /**
     * Validate the business rules for customer save
     * @param customer the customer to validate
     * @param update a flag indication of update transaction
     * @return true if the rules is valid
     */
    private boolean customerBusinessRulesCheck(Customer customer, boolean update) throws APIError {
        log.info("Validating Customer's Business Rules");
        if(ClockUtils.getAge(customer.getBirthDate()) > 100) throw new APIError("Age can't be more than 100 y", EAPIError.VALIDATION);
        if(!ValidaCPF.isCPF(customer.getCpf())) throw new APIError("Invalid CPF", EAPIError.VALIDATION);

        if(!update) {
            Customer c = customerDao.getCustomerByCPF(customer.getCpf());
            if(c != null) {
                throw new APIError("CPF is already saved on database for user id " + c.getCustomerId(), EAPIError.VALIDATION);
            }
        }
        return true;
    }
}
