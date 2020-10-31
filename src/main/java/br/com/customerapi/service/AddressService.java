package br.com.customerapi.service;

import br.com.customerapi.CustomerAPIMain;
import br.com.customerapi.dao.AddressDao;
import br.com.customerapi.model.Address;
import br.com.customerapi.model.EAPIError;
import br.com.customerapi.module.AppModule;
import br.com.customerapi.util.APIError;
import br.com.customerapi.util.JsonUtils;
import br.com.customerapi.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class AddressService {
    final static Logger log = LoggerFactory.getLogger(CustomerAPIMain.class);

    Injector injector = Guice.createInjector(new AppModule());
    AddressDao addressDao = injector.getInstance(AddressDao.class);

    /***
     * Create an address for an customer ID
     * @param json the address json
     * @param customerId the customer ID
     * @return the created address
     * @throws JsonProcessingException an exception while json parsing
     * @throws APIError an exception for this API
     */
    public Address createAddress(String json, Long customerId) throws JsonProcessingException, APIError {
        return saveAddress(parseAddress(json), customerId, false);
    }

    /**
     * Receive a json and parse to Address
     * @param json json with address object
     * @return Address object
     * @throws JsonProcessingException
     */
    private Address parseAddress(String json) throws JsonProcessingException {
        String addressJson = "";

        // Extract address object from the body
        if(json.contains("address")) {
            addressJson = json.substring(json.indexOf("\"address\""));
            addressJson = addressJson.substring(addressJson.indexOf(":")+1);
        }
        else {
            addressJson = json;
        }

        if(addressJson.replaceAll("[^}]", "").length() > 1) {
            addressJson = addressJson.substring(0, addressJson.indexOf("}")+1);
        }
        return JsonUtils.fromJson(addressJson, Address.class);
    }

    /**
     * Parse the received address and put the address id received from API
     * @param json json with address
     * @param addressId the received address id
     * @return the address with the received id
     * @throws JsonProcessingException
     */
    private Address parseAddress(String json, Long customerId, Long addressId) throws JsonProcessingException {
        Address address = parseAddress(json);
        address.setAddressId(addressId);
        address.setCustomerId(customerId);
        return address;
    }

    /**
     * Save the address on database
     * @param address the address to save
     * @param customerId the customer id
     * @param update if is updating operation
     * @return the saved Address
     * @throws APIError
     */
    private Address saveAddress(Address address, Long customerId, boolean update) throws APIError {
        log.info((update ? "Updating " : "Save new ") + "address for user id: " + customerId);

        address.setZipCode(StringUtils.removeCepMask(address.getZipCode()));
        address.setCustomerId(customerId);

        // verify if the address is main
        if(address.isMain()) {
            log.info("Set other address as not main");
            addressDao.updateAllCustomerAddressToNotMain(customerId);
        }

        if(update && address.getAddressId() == null) {
            log.info("Search for the address ID on customer addresses");
            try {
                List<Address> addresses = listCustomerAddresses(customerId);
                if(addresses.size() == 0) throw new Exception("Address not found");
                Address find = addresses.stream().filter(address::isEqualsIgnoringID).findFirst().get();
                address.setAddressId(find.getAddressId());
            }
            catch (Exception e) {
                log.info("Not found the address on customer addresses list, will create new");
                update = false;

            }
        }

        if(update) {
            if( addressDao.updateAddress(address) <= 0) throw new APIError("Unable to update address", EAPIError.UPDATE);
        }
        else {
            Long addressId = addressDao.createAddress(address);
            if( addressId <= 0) throw new APIError("Unable to create address", EAPIError.SAVE);
            address.setAddressId(addressId);
        }
        return address;
    }

    /***
     * Update the received address
     * @param json the json of address
     * @param customerId the customer id to update
     * @return the updated address
     * @throws JsonProcessingException
     * @throws APIError
     */
    public Address updateAddress(String json, Long customerId) throws JsonProcessingException, APIError {
        return saveAddress(parseAddress(json), customerId, true);
    }

    /***
     * Update the received address
     * @param json the json of address
     * @param customerId the customer id to update
     * @return the updated address
     * @throws JsonProcessingException
     * @throws APIError
     */
    public Address updateAddress(String json, Long customerId, Long addressId) throws JsonProcessingException, APIError {
        return saveAddress(parseAddress(json, customerId, addressId), customerId, true);
    }

    /**
     * List all customer's addresses
     * @param customerId the customer ID
     * @return a list with customer's address
     */
    public List<Address> listCustomerAddresses(Long customerId) {
        return addressDao.listCustomerAddresses(customerId);
    }

    /**
     * Get the customer id address by address id
     * @param customerId the customer id
     * @param addressId the address id
     * @return the address or null
     */
    public Address getCustomerAddressByAddressId(Long customerId, Long addressId) {
        return addressDao.getCustomerAddressByAddressId(customerId, addressId);
    }

    /**
     * Delete the address from customer's addresses list
     * @param customerId the customer id to remove the address
     * @param addressId the address id to remove
     * @return number of rows affected
     */
    public Integer deleteAddress(Long customerId, Long addressId) {
        return addressDao.deleteAddress(customerId,addressId);
    }
}
