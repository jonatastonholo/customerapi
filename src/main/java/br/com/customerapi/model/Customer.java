package br.com.customerapi.model;

import br.com.customerapi.util.ClockUtils;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Customer Entity
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class Customer implements Serializable {
    @ColumnName(value = "customerId")
    private int customerId;
    private final String uuid;
    private String cpf;
    private String name;
    private String email;
    private Timestamp birthDate;
    private String gender;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private final List<Address> addresses;

    /***
     * A constructor for create new Customer
     * @param cpf
     * @param name
     * @param email
     * @param birthDate
     * @param gender
     */
    public Customer(String cpf, String name, String email, Timestamp birthDate, String gender) {
        this.cpf = cpf;
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.createdAt = ClockUtils.getTimestampNow();
        this.updatedAt = this.createdAt;
        this.addresses = new ArrayList<>();
    }

    /***
     * Full constructor for get from database
     * @param customerId
     * @param cpf
     * @param name
     * @param email
     * @param birthDate
     * @param gender
     * @param createdAt
     * @param updatedAt
     */
    @ConstructorProperties({"customerId", "uuid", "cpf", "name", "email", "birthDate", "gender", "createdAt", "updatedAt"})
    public Customer(int customerId, String uuid, String cpf, String name, String email, Timestamp birthDate, String gender, Timestamp createdAt, Timestamp updatedAt) {
        this.customerId = customerId;
        this.uuid = uuid;
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.addresses = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String getUuid() {
        return uuid;
    }
}
