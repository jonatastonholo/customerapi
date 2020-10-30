package br.com.customerapi.dao;

import br.com.customerapi.model.Customer;
import br.com.customerapi.model.Gender;
import br.com.customerapi.module.AppModule;
import br.com.customerapi.util.ClockUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
class CustomerDaoTest {

    Injector injector = Guice.createInjector(new AppModule());
    CustomerDao dao = injector.getInstance(CustomerDao.class);

    @Test
    void createCustomer() {
        Customer customer = new Customer(
                "08798545621",
                "Teste Hoo",
                "hoo@gmail.com",
                ClockUtils.getTimestampFromStringDate("1989-02-22"),
                Gender.MALE.toString()
        );

        Integer rowsModified = dao.createCustomer(customer);
        assert rowsModified != null;
    }

    @Test
    void listCustomers() {
        List<Customer> customers = dao.listCustomers();
        assertNotNull(customers);
    }

    @Test
    void getCustomer() {
        Customer customer = dao.getCustomer(4L);
        assertNotNull(customer);
    }

    @Test
    void updateCustomer() {
        Customer customer = dao.getCustomer(4L);

        customer.setName("Juarez ho ho ho");
        customer.setUpdatedAt(ClockUtils.getTimestampNow());

        // Update
        Integer rowsModified = dao.updateCustomer(customer);
        assert rowsModified != null;
    }

    @Test
    void deleteCustomer() {
        Integer rowsModified = dao.deleteCustomer(4L);
        assert rowsModified != null;
    }

    @Test
    void getCustomerByCPF() {
        Customer customer = dao.getCustomerByCPF("98521566658");
        assertNotNull(customer);
    }

    @Test
    void listCustomersWithFilters() {
        Map<String, String[]> map = new HashMap<>();
        String[] nome = {"JoNaTas"};
        String[] birthDate = {"1989-02-22"};
        String[] state = {"MG"};
        String[] city = {"B"};
        String[] sortBy = {"name"};
        String[] sortOrder = {"DESC"};


        map.put("name", nome);
        map.put("birthDate", birthDate);
        map.put("state", state);
        map.put("city", city);
        map.put("sortBy", sortBy);
        map.put("sortOrder", sortOrder);

        List<Customer> customers = dao.listCustomers(map);

        assertNotNull(customers);
    }
}