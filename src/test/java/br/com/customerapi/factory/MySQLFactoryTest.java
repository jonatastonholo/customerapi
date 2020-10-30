package br.com.customerapi.factory;
import br.com.customerapi.dao.CustomerDao;
import br.com.customerapi.model.Customer;
import br.com.customerapi.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class MySQLFactoryTest {
    Injector injector = Guice.createInjector(new AppModule());
    CustomerDao dao = injector.getInstance(CustomerDao.class);

    @Test
    void initialize() {
        List<Customer> customers = dao.listCustomers();
        assertNotNull(customers);
    }
}