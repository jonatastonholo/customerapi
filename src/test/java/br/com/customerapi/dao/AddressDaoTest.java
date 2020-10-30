package br.com.customerapi.dao;

import br.com.customerapi.model.Address;
import br.com.customerapi.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class AddressDaoTest {

    Injector injector = Guice.createInjector(new AppModule());
    AddressDao dao = injector.getInstance(AddressDao.class);

    @Test
    void createAddress() {
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
        List<Address> addressList = dao.listCustomerAddresses(1L);
        assertNotNull(addressList);
    }

    @Test
    void getCustomerAddressByAddressId() {
        Address address = dao.getCustomerAddressByAddressId(1L, 1L);
        assertNotNull(address);
    }

    @Test
    void updateAddress() {
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
        Integer rowsModified = dao.deleteAddress(1L, 5L);
        assert rowsModified != null;
    }

    @Test
    void updateAllCustomerAddressToNotMain() {
        Integer rowsModified = dao.updateAllCustomerAddressToNotMain(1L);
        assert rowsModified != null;
    }
}