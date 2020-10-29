package br.com.customerapi.dao;

import br.com.customerapi.CustomerAPI;
import br.com.customerapi.factory.MySQLFactory;
import br.com.customerapi.model.Address;
import br.com.customerapi.model.AddressMapper;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class AddressDao {
    final static Logger log = LoggerFactory.getLogger(CustomerAPI.class);

    /***
     * Create new address
     * @param address a address to save on database
     * @return the number of affected rows
     */
    public Integer createAddress(Address address) {
        try{
            log.info("Create Address id:" + address.getAddressId() + "(customerId: " + address.getCustomerId() + ")");
            String QUERY = "INSERT INTO ADDRESS(" +
                    "addr_customerId, "         +
                    "state, "                   +
                    "city, "                    +
                    "neighborhood, "            +
                    "zipCode, "                 +
                    "street, "                  +
                    "number, "                  +
                    "additionalInformation, "   +
                    "main "                     +
                    ") "                        +
                    "VALUES("                   +
                    ":addr_customerId, "        +
                    ":state, "                  +
                    ":city, "                   +
                    ":neighborhood, "           +
                    ":zipCode, "                +
                    ":street, "                 +
                    ":number, "                 +
                    ":additionalInformation, "  +
                    ":main "                    +
                    ")";
            Jdbi jdbi = MySQLFactory.jdbi();
            return jdbi.withHandle(handle -> handle.createUpdate(QUERY)
                        .bind("addr_customerId",address.getCustomerId())
                        .bind("state",address.getState())
                        .bind("city",address.getCity())
                        .bind("neighborhood",address.getNeighborhood())
                        .bind("zipCode",address.getZipCode())
                        .bind("street",address.getStreet())
                        .bind("number",address.getNumber())
                        .bind("additionalInformation",address.getAdditionalInformation())
                        .bind("main",address.isMain())
                        .execute()
            );
        }
        catch (Exception e) {
            log.error("Error on trying create new address");
            log.debug(e.getMessage());
            return null;
        }
    }

    /**
     * List all customer's addresses
     * @param customerId the customer id
     * @return a list with all customer addresses
     */
    public List<Address> listCustomerAddresses(Long customerId) {
        try {
            log.info("Get customer's addresses list (customerId: " +customerId+ ")");

            Jdbi jdbi = MySQLFactory.jdbi();
            String QUERY = "SELECT * FROM ADDRESS WHERE addr_customerId = " + customerId ;

            return jdbi.withHandle(handle ->
                    handle.createQuery(QUERY)
                            .map(new AddressMapper())
                            .list()
            );
        }
        catch (Exception e) {
            log.error("Error on trying Get customer's addresses list (customerId:" +customerId + ")");
            log.debug(e.getMessage());
            return new ArrayList<>();
        }
    }

    /***
     * Receive a Customer ID and an Address ID, find and return the address if exists
     * @param customerId The customer ID
     * @param addressId The Address ID
     * @return The saved address or null if not exists
     */
    public Address getCustomerAddressByAddressId(Long customerId, Long addressId) {
        try {
            log.info("Get customer's addresses by id (addressId: " + addressId + ") (customerId: " +customerId + ") ");

            Jdbi jdbi = MySQLFactory.jdbi();
            String QUERY = "SELECT * FROM ADDRESS " +
                    "WHERE  addressId = " + addressId +
                    " AND addr_customerId = " + customerId;

            return jdbi.withHandle(handle ->
                    handle.createQuery(QUERY)
                            .map(new AddressMapper())
                            .list()
            ).get(0);
        }
        catch (Exception e) {
            log.error("Error on trying Get customer's addresses by id (addressId: " + addressId + ") (customerId: " +customerId + ") ");
            log.debug(e.getMessage());
            return null;
        }
    }

    /***
     * Update the address on database
     * @param address The address to update
     * @return the number of affected rows
     */
    public Integer updateAddress(Address address) {
        try {
            log.info("Update customer's addresses by id (addressId: " + address.getAddressId() + ") (customerId: " + address.getCustomerId() + ") ");
            Jdbi jdbi = MySQLFactory.jdbi();
            String QUERY = "UPDATE ADDRESS " +
                    "SET " +
                    "state = :state, " +
                    "city = :city, " +
                    "neighborhood = :neighborhood, " +
                    "zipCode = :zipCode, " +
                    "street = :street, " +
                    "number = :number, " +
                    "additionalInformation = :additionalInformation, " +
                    "main = :main " +
                    "WHERE " +
                    "addressId = :addressId AND addr_customerId = :addr_customerId";

            return jdbi.withHandle(handle -> handle.createUpdate(QUERY)
                        .bind("addressId",address.getAddressId())
                        .bind("addr_customerId",address.getCustomerId())
                        .bind("state",address.getState())
                        .bind("city",address.getCity())
                        .bind("neighborhood",address.getNeighborhood())
                        .bind("zipCode",address.getZipCode())
                        .bind("street",address.getStreet())
                        .bind("number",address.getNumber())
                        .bind("additionalInformation",address.getAdditionalInformation())
                        .bind("main",address.isMain())
                        .execute()
            );
        }
        catch (Exception e) {
            log.error("Error on trying update customer's addresses by id (addressId: " + address.getAddressId() + ") (customerId: " + address.getCustomerId() + ") ");
            log.debug(e.getMessage());
            return null;
        }
    }

    /***
     * Receive an address ID to delete from customer's address list
     * @param customerId the customer id to delete
     * @return the number of affected rows
     */
    public Integer deleteAddress(Long customerId, Long addressId) {
        try{
            log.info("Delete customer's addresses by id (addressId: " + customerId + ") (customerId: " + addressId + ") ");
            String QUERY = "DELETE FROM ADDRESS WHERE addressId = :addressId AND addr_customerId = :customerId ";
            Jdbi jdbi = MySQLFactory.jdbi();
            return jdbi.withHandle(handle -> handle.createUpdate(QUERY)
                    .bind("addressId",addressId)
                    .bind("customerId",customerId)
                    .execute()
            );
        }
        catch (Exception e) {
            log.error("Error on trying delete customer's addresses by id (addressId: " + customerId + ") (customerId: " + addressId + ") ");
            log.debug(e.getMessage());
            return 0;
        }
    }

    public Address getCustomerMainAddress(Long customerId, Long addressId) {
        String QUERY = "SELECT * FROM ADDRESS " +
                "WHERE  addressId = " + addressId +
                " AND addr_customerId = " + customerId +
                " AND main is true";

        Jdbi jdbi = MySQLFactory.jdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery(QUERY)
                        .map(new AddressMapper())
                        .list()
        ).get(0);
    }

    public Integer updateAllCustomerAddressToNotMain(Long customerId) {
        try {
            log.info("Updating all address of customer id " + customerId + " to not main");
            String QUERY = "UPDATE ADDRESS SET main = FALSE WHERE addr_customerId = :customerId";
            Jdbi jdbi = MySQLFactory.jdbi();
            return jdbi.withHandle(handle -> handle.createUpdate(QUERY)
                    .bind("customerId",customerId)
                    .execute()
            );

        }
        catch (Exception e) {
            log.error("Error on trying updating all address of customer id " + customerId + " to not main");
            log.debug(e.getMessage());
            return 0;
        }
    }
}
