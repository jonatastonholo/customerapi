package br.com.customerapi.dao;

import br.com.customerapi.CustomerAPI;
import br.com.customerapi.factory.MySQLFactory;
import br.com.customerapi.model.Address;
import br.com.customerapi.model.Customer;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toList;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerDao {
    final static Logger log = LoggerFactory.getLogger(CustomerAPI.class);

    /***
     * Create new customer
     * @param customer
     * @return
     */
    public Integer createCustomer(Customer customer) {
        try{
            log.info("Create customer " + customer.getName() + "(uuid: " + customer.getUuid() + ")");
            String QUERY = "INSERT INTO CUSTOMER(" +
                    "cpf, "        +
                    "uuid, "       +
                    "name, "       +
                    "email, "      +
                    "birthDate, "  +
                    "gender, "     +
                    "createdAt, "  +
                    "updatedAt "   +
                    ") "           +
                    "VALUES("      +
                    ":cpf, "       +
                    ":uuid, "      +
                    ":name, "      +
                    ":email, "     +
                    ":birthDate, " +
                    ":gender, "    +
                    ":createdAt, " +
                    ":updatedAt "  +
                    ")";
            Jdbi jdbi = MySQLFactory.jdbi();
            return jdbi.withHandle(handle -> {
                return handle.createUpdate(QUERY)
                        .bind("cpf",customer.getCpf())
                        .bind("uuid",customer.getUuid())
                        .bind("name",customer.getName())
                        .bind("email",customer.getEmail())
                        .bind("birthDate",customer.getBirthDate())
                        .bind("gender",customer.getGender())
                        .bind("createdAt",customer.getCreatedAt())
                        .bind("updatedAt",customer.getUpdatedAt())
                        .execute();
            });
        }
        catch (Exception e) {
            log.error("Error on trying create new customer");
            log.debug(e.getMessage());
            return null;
        }
    }

    /**
     * List all customers saved on database
     * @return a list with all customers saved on database
     */
    public List<Customer> listCustomers() {
        log.info("List all customers");
        try {
            Jdbi jdbi = MySQLFactory.jdbi();
            String QUERY = "select * from CUSTOMER c LEFT JOIN ADDRESS a on c.customerId = a.addr_customerId";
            return jdbi.withHandle(handle ->
                    handle.createQuery(QUERY)
                            .registerRowMapper(ConstructorMapper.factory(Customer.class))
                            .registerRowMapper(ConstructorMapper.factory(Address.class))
                            .reduceRows(new LinkedHashMap<Long, Customer>(), (map, rowView) -> {
                                Customer customer = map.computeIfAbsent(rowView.getColumn("customerId", Long.class),
                                        id -> rowView.getRow(Customer.class));

                                if (rowView.getColumn("addressId", Long.class) != null) {
                                    customer.getAddresses().add(rowView.getRow(Address.class));
                                }

                                return map;
                            })
                            .values()
                            .stream()
                            .collect(toList()));

        }
        catch (Exception e) {
            log.error("Error on trying list all customers");
            log.debug(e.getMessage());
            return new ArrayList<Customer>();
        }
    }

    /***
     * Get customer by Id
     * @param customerId
     * @return
     */
    public Customer getCustomer(Long customerId) {
        try {
            log.info("Get customer id: " + customerId);

            Jdbi jdbi = MySQLFactory.jdbi();
            String QUERY = "select * from CUSTOMER c " +
                    "LEFT JOIN ADDRESS a on c.customerId = a.addr_customerId " +
                    "WHERE c.customerId = " + customerId;

            return jdbi.withHandle(handle ->
                    handle.createQuery(QUERY)
                            .registerRowMapper(ConstructorMapper.factory(Customer.class))
                            .registerRowMapper(ConstructorMapper.factory(Address.class))
                            .reduceRows(new LinkedHashMap<Long, Customer>(), (map, rowView) -> {
                                Customer customer = map.computeIfAbsent(rowView.getColumn("customerId", Long.class),
                                        id -> rowView.getRow(Customer.class));

                                if (rowView.getColumn("addressId", Long.class) != null) {
                                    customer.getAddresses().add(rowView.getRow(Address.class));
                                }

                                return map;
                            })
                            .values()
                            .stream()
                            .collect(toList())).get(0);
        }
        catch (Exception e) {
            log.error("Error on trying get customers id: " + customerId);
            log.debug(e.getMessage());
            return null;
        }
    }

    /***
     * Ipdate the customer
     * @param customer
     * @return
     */
    public Integer updateCustomer(Customer customer) {
        try{
            log.info("UPDATE customer " + customer.getName() + "(id: " + customer.getCustomerId() + ")");

            String QUERY = "UPDATE CUSTOMER "   +
                    "SET "                      +
                    "cpf = :cpf, "              +
                    "uuid = :uuid, "            +
                    "name = :name, "            +
                    "email = :email, "          +
                    "birthDate = :birthDate, "  +
                    "gender = :gender, "        +
                    "createdAt = :createdAt, "  +
                    "updatedAt = :updatedAt "   +
                    "WHERE "                    +
                    "customerId = :customerId ";

            Jdbi jdbi = MySQLFactory.jdbi();
            return jdbi.withHandle(handle -> {
                return handle.createUpdate(QUERY)
                        .bind("customerId",customer.getCustomerId())
                        .bind("cpf",customer.getCpf())
                        .bind("uuid",customer.getUuid())
                        .bind("name",customer.getName())
                        .bind("email",customer.getEmail())
                        .bind("birthDate",customer.getBirthDate())
                        .bind("gender",customer.getGender())
                        .bind("createdAt",customer.getCreatedAt())
                        .bind("updatedAt",customer.getUpdatedAt())
                        .execute();
            });
        }
        catch (Exception e) {
            log.error("Error on trying update customer");
            log.debug(e.getMessage());
            return null;
        }
    }

    /***
     * Delete a customer and all their addresses (Foreign key is set with on delete cascade)
     * @param customerId
     * @return
     */
    public Integer deleteCustomer(Long customerId) {
        try{
            log.info("DELETE customer id: " + customerId + ")");
            String QUERY = "DELETE FROM CUSTOMER WHERE customerId = :customerId ";
            Jdbi jdbi = MySQLFactory.jdbi();
            return jdbi.withHandle(handle -> {
                return handle.createUpdate(QUERY)
                        .bind("customerId",customerId)
                        .execute();
            });
        }
        catch (Exception e) {
            log.error("Error on trying delete customer id: " + customerId);
            log.debug(e.getMessage());
            return null;
        }
    }
}
