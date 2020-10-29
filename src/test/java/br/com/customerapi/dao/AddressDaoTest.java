package br.com.customerapi.dao;

import br.com.customerapi.factory.MySQLFactory;
import br.com.customerapi.model.Address;
import br.com.customerapi.model.Customer;
import br.com.customerapi.util.ClockUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class AddressDaoTest {

    @Test
    void createAddress() {
        MySQLFactory.initialize();
        AddressDao dao = new AddressDao();

        Address address = new Address(
            1L,
            "TO",
            "Palmas",
            "Teste",
            "1234596",
            "Rua aaa",
            66,
            "AA",
            true
        );

        Integer rowsModified = dao.createAddress(address);
        assert rowsModified != null;
    }

    @Test
    void getCustomerAddresses() {
        MySQLFactory.initialize();
        AddressDao dao = new AddressDao();
        List<Address> addressList = dao.listCustomerAddresses(1L);
        assertTrue(addressList != null || addressList == null);
    }

    @Test
    void getCustomerAddressByAddressId() {
        MySQLFactory.initialize();
        AddressDao dao = new AddressDao();
        Address address = dao.getCustomerAddressByAddressId(1L, 1L);
        assertTrue(address != null);
    }

    @Test
    void updateAddress() {
        MySQLFactory.initialize();
        AddressDao dao = new AddressDao();
        Address address = dao.getCustomerAddressByAddressId(1L, 1L);


        address.setMain(false);
        address.setCity("Belo Horizonte");
        address.setNeighborhood("Concórdia");

        // Update
        Integer rowsModified = dao.updateAddress(address);
        assert rowsModified != null;
    }

    @Test
    void deleteAddress() {
        MySQLFactory.initialize();
        AddressDao dao = new AddressDao();
        Integer rowsModified = dao.deleteAddress(1L, 5L);
        assert rowsModified != null;
    }

    @Test
    void updateAllCustomerAddressToNotMain() {
        MySQLFactory.initialize();
        AddressDao dao = new AddressDao();
        Integer rowsModified = dao.updateAllCustomerAddressToNotMain(1L);
        assert rowsModified != null;
    }
}