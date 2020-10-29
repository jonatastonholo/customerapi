package br.com.customerapi.dao;
import br.com.customerapi.factory.MySQLFactory;
import br.com.customerapi.model.Customer;
import br.com.customerapi.util.ClockUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class CustomerDaoTest {

    @Test
    void createCustomer() {
        Customer customer = new Customer(
                "08798545621",
                "Teste Hoo",
                "hoo@gmail.com",
                ClockUtils.getTimestampFromStringDate("1989-02-22"),
                "algum genero qualquer"
        );

        MySQLFactory.initialize();
        CustomerDao dao = new CustomerDao();
        Integer rowsModified = dao.createCustomer(customer);
        assert rowsModified != null;
    }

    @Test
    void listCustomers() {
        MySQLFactory.initialize();
        CustomerDao dao = new CustomerDao();
        List<Customer> customers = dao.listCustomers();
        assertNotNull(customers);
    }

    @Test
    void getCustomer() {
        MySQLFactory.initialize();
        CustomerDao dao = new CustomerDao();
        Customer customer = dao.getCustomer(4L);
        assertNotNull(customer);
    }

    @Test
    void updateCustomer() {
        MySQLFactory.initialize();
        CustomerDao dao = new CustomerDao();
        Customer customer = dao.getCustomer(4L);

        customer.setName("Juarez ho ho ho");
        customer.setUpdatedAt(ClockUtils.getTimestampNow());

        // Update
        Integer rowsModified = dao.updateCustomer(customer);
        assert rowsModified != null;
    }

    @Test
    void deleteCustomer() {
        MySQLFactory.initialize();
        CustomerDao dao = new CustomerDao();
        Integer rowsModified = dao.deleteCustomer(4L);
        assert rowsModified != null;
    }

    @Test
    void getCustomerByCPF() {
        MySQLFactory.initialize();
        CustomerDao dao = new CustomerDao();
        Customer customer = dao.getCustomerByCPF("98521566658");
        assertNotNull(customer);
    }
}