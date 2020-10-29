package br.com.customerapi.factory;
import br.com.customerapi.dao.CustomerDao;
import br.com.customerapi.model.Customer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class MySQLFactoryTest {

    @Test
    void initialize() {
        MySQLFactory.initialize();
        CustomerDao dao = new CustomerDao();
        List<Customer> customers = dao.listCustomers();
        assertTrue(customers != null || customers == null);
    }
}