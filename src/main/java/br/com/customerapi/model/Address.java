package br.com.customerapi.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.beans.ConstructorProperties;

/**
 * The Address Entity
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class Address {
    private Long addressId;
    @ColumnName(value = "addr_customerId")
    private Long customerId;
    private String state;
    private String city;
    private String neighborhood;
    private String zipCode;
    private String street;
    private Integer number;
    private String additionalInformation;
    boolean main;

    /***
     * A constructor to create new address to save on database
     * @param customerId the customer id
     * @param state the state initials (size 2)
     * @param city the city name
     * @param neighborhood the neighborhood name
     * @param zipCode the zip code (string with numbers only)
     * @param street the street name
     * @param number the number of the house (int)
     * @param additionalInformation the additional information for the address
     * @param main if this address is main
     */
    public Address(Long customerId, String state, String city, String neighborhood, String zipCode, String street, Integer number, String additionalInformation, boolean main) {
        this.customerId = customerId;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.additionalInformation = additionalInformation;
        this.main = main;
    }

    /***
     * A constructor do load address from database
     * @param addressId the address id
     * @param customerId the customer id
     * @param state the state initials (size 2)
     * @param city the city name
     * @param neighborhood the neighborhood name
     * @param zipCode the zip code (string with numbers only)
     * @param street the street name
     * @param number the number of the house (int)
     * @param additionalInformation the additional information for the address
     * @param main if this address is main
     */
    @ConstructorProperties({"addressId", "addr_customerId", "state", "city", "neighborhood", "zipCode", "street", "number", "additionalInformation", "main"})
    public Address(Long addressId, Long customerId, String state, String city, String neighborhood, String zipCode, String street, Integer number, String additionalInformation, boolean main) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.additionalInformation = additionalInformation;
        this.main = main;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
