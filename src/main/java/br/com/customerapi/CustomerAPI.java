package br.com.customerapi;
import br.com.customerapi.service.AddressAPIService;
import br.com.customerapi.service.CustomerAPIService;

/**
 * The Customer Rest API main
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class CustomerAPI {

    public static void main(String[] args) {
        // Initialize the API REST
        CustomerAPIService.initialize();
        AddressAPIService.initialize();
    }
}
