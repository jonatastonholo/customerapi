package br.com.customerapi.dao;

import br.com.customerapi.CustomerAPI;
import br.com.customerapi.factory.MySQLFactory;
import br.com.customerapi.model.Address;
import br.com.customerapi.model.Customer;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerDao {
    final static Logger log = LoggerFactory.getLogger(CustomerAPI.class);

    /***
     * Create new customer
     * @param customer the customer to create
     * @return the number of affected rows
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
            return jdbi.withHandle(handle -> handle.createUpdate(QUERY)
                        .bind("cpf",customer.getCpf())
                        .bind("uuid",customer.getUuid())
                        .bind("name",customer.getName())
                        .bind("email",customer.getEmail())
                        .bind("birthDate",customer.getBirthDate())
                        .bind("gender",customer.getGender())
                        .bind("createdAt",customer.getCreatedAt())
                        .bind("updatedAt",customer.getUpdatedAt())
                        .execute()
            );
        }
        catch (Exception e) {
            log.error("Error on trying create new customer");
            log.debug(e.getMessage());
            return 0;
        }
    }

    /***
     * A select all customers with left join address table
     * @param FILTER An optional filter to add in join selection
     * @return a list with customers
     */
    private List<Customer> selectJoin(String FILTER) {
        String QUERY = "SELECT * FROM CUSTOMER c LEFT JOIN ADDRESS a ON c.customerId = a.addr_customerId";
        Jdbi jdbi = MySQLFactory.jdbi();
        return jdbi.withHandle(handle ->
                new ArrayList<>(handle.createQuery(QUERY + (FILTER == null ? "" : " " + FILTER))
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
                        .values()));
    }

    /**
     * List all customers saved on database
     * @return a list with all customers saved on database
     */
    public List<Customer> listCustomers() {
        try {
            log.info("List all customers");
            return selectJoin(null);
        }
        catch (Exception e) {
            log.error("Error on trying list all customers");
            log.debug(e.getMessage());
            return new ArrayList<>();
        }
    }

    /***
     * Get customer by Id
     * @param customerId The customer id to search
     * @return the customer
     */
    public Customer getCustomer(Long customerId) {
        try {
            log.info("Get customer id: " + customerId);
            String FILTER = "WHERE c.customerId = " + customerId;
            return selectJoin(FILTER).get(0);
        }
        catch (Exception e) {
            log.error("Error on trying get customers id: " + customerId);
            log.debug(e.getMessage());
            return null;
        }
    }

    /***
     * Update the customer
     * @param customer the customer to update
     * @return the number of affected rows
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
            return jdbi.withHandle(handle -> handle.createUpdate(QUERY)
                        .bind("customerId",customer.getCustomerId())
                        .bind("cpf",customer.getCpf())
                        .bind("uuid",customer.getUuid())
                        .bind("name",customer.getName())
                        .bind("email",customer.getEmail())
                        .bind("birthDate",customer.getBirthDate())
                        .bind("gender",customer.getGender())
                        .bind("createdAt",customer.getCreatedAt())
                        .bind("updatedAt",customer.getUpdatedAt())
                        .execute()
            );
        }
        catch (Exception e) {
            log.error("Error on trying update customer");
            log.debug(e.getMessage());
            return 0;
        }
    }

    /***
     * Delete a customer and all their addresses (Foreign key is set with on delete cascade)
     * @param customerId the customer id to delete
     * @return the number of affected rows
     */
    public Integer deleteCustomer(Long customerId) {
        try{
            log.info("DELETE customer id: " + customerId + ")");
            String QUERY = "DELETE FROM CUSTOMER WHERE customerId = :customerId ";
            Jdbi jdbi = MySQLFactory.jdbi();
            return jdbi.withHandle(handle -> handle.createUpdate(QUERY)
                        .bind("customerId",customerId)
                        .execute()
            );
        }
        catch (Exception e) {
            log.error("Error on trying delete customer id: " + customerId);
            log.debug(e.getMessage());
            return 0;
        }
    }

    /***
     * Get the customer from cpf or null if the cpf is not saved yet
     * @param cpf the customer cpf to get
     * @return the customer if cpf exists or null
     */
    public Customer getCustomerByCPF(String cpf) {
        try {
            log.info("Get customer cpf: " + cpf);
            String FILTER = "WHERE c.cpf = " + cpf;
            return selectJoin(FILTER).get(0);
        }
        catch (Exception e) {
            log.error("Error on trying get customers cpf: " + cpf);
            log.debug(e.getMessage());
            return null;
        }
    }
}
