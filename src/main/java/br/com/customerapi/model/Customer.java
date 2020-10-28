package br.com.customerapi.model;

import java.util.Date;
import java.util.List;

/**
 * The Customer Entity
 *
 * @author Jônatas Ribeiro Tonholo
 */
public class Customer {
    private Integer customerId;
    private String cpf;
    private String name;
    private String email;
    private Date birthDate;
    private String gender;
    private Date createdAt;
    private Date updatedAt;
    private List<Address> addresses;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Customer(Integer customerId, String cpf, String name, String email, Date birthDate, String gender, Date createdAt, Date updatedAt, List<Address> addresses) {
        this.customerId = customerId;
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.addresses = addresses;
    }
}
