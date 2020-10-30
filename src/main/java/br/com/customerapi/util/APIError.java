package br.com.customerapi.util;

import br.com.customerapi.model.EAPIError;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public class APIError extends Exception{
    private EAPIError type;
    public APIError(String message, EAPIError type) {
        super(message);
        this.type = type;
    }


}
