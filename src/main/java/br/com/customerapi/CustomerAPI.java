package br.com.customerapi;
import br.com.customerapi.api.AddressAPI;

/**
 * The Customer Rest API main
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerAPI {

    public static void main(String[] args) {
        // Initialize the API REST
        br.com.customerapi.api.CustomerAPI.initialize();
        AddressAPI.initialize();
    }
}
