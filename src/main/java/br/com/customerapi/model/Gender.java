package br.com.customerapi.model;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public enum Gender {
    MALE("MASCULINO"),
    FEMALE("FEMININO");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
