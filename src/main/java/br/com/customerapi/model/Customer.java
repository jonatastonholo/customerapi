package br.com.customerapi.model;

import br.com.customerapi.util.ClockUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The Customer Entity
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class Customer implements Serializable {
    static final long serialVersionUID = -5801299974913389122L;

    @ColumnName(value = "customerId")
    @JsonProperty(value = "id")
    private Long customerId;

    private String uuid;
    private String cpf;
    private String name;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp birthDate;
    private String gender;

    private final List<Address> addresses;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    /***
     * A constructor for create new Customer
     * @param cpf the customer cpf number (only string numbers - size 11)
     * @param name the customer name
     * @param email the customer email
     * @param birthDate the customer birthdate
     * @param gender the customer gender
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
     * @param customerId the customer id
     * @param cpf the customer cpf number (only string numbers - size 11)
     * @param name the customer name
     * @param email the customer email
     * @param birthDate the customer birthdate
     * @param gender the customer gender
     * @param createdAt the creation timestamp
     * @param updatedAt the last update timestamp
     */
    @ConstructorProperties({"customerId", "uuid", "cpf", "name", "email", "birthDate", "gender", "createdAt", "updatedAt"})
    public Customer(Long customerId, String uuid, String cpf, String name, String email, Timestamp birthDate, String gender, Timestamp createdAt, Timestamp updatedAt) {
        this.customerId = customerId;
        this.uuid = uuid == null ? UUID.randomUUID().toString() : uuid;
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.createdAt = createdAt == null ? ClockUtils.getTimestampNow() : createdAt;
        this.updatedAt = updatedAt == null ? ClockUtils.getTimestampNow() : updatedAt;
        this.addresses = new ArrayList<>();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId) &&
                uuid.equals(customer.uuid) &&
                cpf.equals(customer.cpf) &&
                name.equals(customer.name) &&
                email.equals(customer.email) &&
                birthDate.equals(customer.birthDate) &&
                gender.equals(customer.gender) &&
                createdAt.equals(customer.createdAt) &&
                updatedAt.equals(customer.updatedAt) &&
                addresses.equals(customer.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, uuid, cpf, name, email, birthDate, gender, createdAt, updatedAt, addresses);
    }
}
