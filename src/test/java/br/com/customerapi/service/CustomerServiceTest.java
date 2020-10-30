package br.com.customerapi.service;

import br.com.customerapi.model.Customer;
import br.com.customerapi.module.AppModule;
import br.com.customerapi.util.APIError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
class CustomerServiceTest {

    Injector injector = Guice.createInjector(new AppModule());
    CustomerService customerService = injector.getInstance(CustomerService.class);

    @Test
    void createCustomer() throws JsonProcessingException, APIError {
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
        Customer customer = customerService.createCustomer(json);
        assertNotNull(customer);
    }

    @Test
    void updateCustomer() throws JsonProcessingException, APIError {
        String json = "{\n" +
                "  \"name\": \"Adalberto Lobo\",\n" +
                "  \"email\": \"suporte@previdenciarista.com\",\n" +
                "  \"birthDate\": \"1989-10-03\",\n" +
                "  \"cpf\": \"999.249.340-71\",\n" +
                "  \"gender\": \"MASCULINO\",\n" +
                "  \"address\": {\n" +
                "    \"state\": \"SP\",\n" +
                "    \"city\": \"São Paulo\",\n" +
                "    \"neighborhood\": \"Tatuapé\",\n" +
                "    \"zipCode\": \"06432-444\",\n" +
                "    \"street\": \"Rua Moacir Franco\",\n" +
                "    \"number\": \"133\",\n" +
                "    \"additionalInformation\": \"Bloco BBB, Apto 33\",\n" +
                "    \"main\": true\n" +
                "  }\n" +
                "}";
        Customer c = customerService.getCustomer(2L);
        Customer customer = customerService.updateCustomer(json, c);
        assertNotNull(customer);

    }

}