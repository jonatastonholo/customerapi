package br.com.customerapi.service;

import br.com.customerapi.model.Address;
import br.com.customerapi.module.AppModule;
import br.com.customerapi.util.APIError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
class AddressServiceTest {
    Injector injector = Guice.createInjector(new AppModule());
    AddressService addressService = injector.getInstance(AddressService.class);
    @Test
    void createAddress() throws JsonProcessingException, APIError {

        String json = "{\n" +
                "  \"name\": \"Adalberto Silva\",\n" +
                "  \"email\": \"suporte@previdenciarista.com\",\n" +
                "  \"birthDate\": \"1989-10-03\",\n" +
                "  \"cpf\": \"915.249.340-71\",\n" +
                "  \"gender\": \"MASCULINO\",\n" +
                "  \"address\": {\n" +
                "    \"state\": \"SP\",\n" +
                "    \"city\": \"São Paulo\",\n" +
                "    \"neighborhood\": \"Tatuapé\",\n" +
                "    \"zipCode\": \"06432-444\",\n" +
                "    \"street\": \"Rua Moacir Franco\",\n" +
                "    \"number\": \"133\",\n" +
                "    \"additionalInformation\": \"Bloco B, Apto 33\",\n" +
                "    \"main\": true\n" +
                "  }\n" +
                "}";

//        String json = "  \"address\": {\n" +
//                "    \"state\": \"SP\",\n" +
//                "    \"city\": \"São Paulo\",\n" +
//                "    \"neighborhood\": \"Tatuapé\",\n" +
//                "    \"zipCode\": \"06432-444\",\n" +
//                "    \"street\": \"Rua Moacir Franco\",\n" +
//                "    \"number\": \"133\",\n" +
//                "    \"additionalInformation\": \"Bloco B, Apto 33\",\n" +
//                "    \"main\": true\n" +
//                "  }\n";

        Address address = addressService.createAddress(json, 1L);
        assertNotNull(address);
    }

    @Test
    void updateAddress() throws JsonProcessingException, APIError {
        String json = "  \"address\": {\n" +
        "    \"state\": \"SP\",\n" +
        "    \"city\": \"São Paulo\",\n" +
        "    \"neighborhood\": \"Tatuapé\",\n" +
        "    \"zipCode\": \"06432-444\",\n" +
        "    \"street\": \"Rua Moacir Franco\",\n" +
        "    \"number\": \"133\",\n" +
        "    \"additionalInformation\": \"Bloco B, Apto 33\",\n" +
        "    \"main\": true\n" +
        "  }\n";
        Address address = addressService.updateAddress(json, 2L);
        assertNotNull(address);
    }

    @Test
    void listCustomerAddresses() {
    }
}