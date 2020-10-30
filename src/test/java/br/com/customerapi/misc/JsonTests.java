package br.com.customerapi.misc;

import br.com.customerapi.model.Address;
import br.com.customerapi.model.Customer;
import br.com.customerapi.model.Gender;
import br.com.customerapi.util.ClockUtils;
import br.com.customerapi.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Jônatas Ribeiro Tonholo
 */
@Disabled
public class JsonTests {

    @Test
    void test() throws JsonProcessingException {
        Customer customer = new Customer(
                1L,
                "45454asd54a3s5d4as584",
                "08798545621",
                "Teste Hoo",
                "hoo@gmail.com",
                ClockUtils.getTimestampFromStringDate("1989-02-22"),
                Gender.MALE.toString(),
                ClockUtils.getTimestampNow(),
                ClockUtils.getTimestampNow()
        );
        customer.getAddresses().add(
                new Address(
                    1L,
                    1L,
                    "TO",
                    "Palmas",
                    "Teste",
                    "1234596",
                    "Rua aaa",
                    66,
                    "AA",
                    true
                )
        );

        customer.getAddresses().add(
                new Address(
                        2L,
                        1L,
                        "TO",
                        "Palmas",
                        "Teste",
                        "1234596",
                        "Rua aaa",
                        66,
                        "AA",
                        true
                )
        );

        String c = JsonUtils.toJson(customer);
        System.out.println(c);
        assert true;
    }
}
