DROP DATABASE IF EXISTS CUSTOMER_API;
CREATE DATABASE IF NOT EXISTS CUSTOMER_API;
use CUSTOMER_API;
CREATE TABLE CUSTOMER (
    customerId  INT UNIQUE NOT NULL AUTO_INCREMENT,
    cpf         VARCHAR(11) UNIQUE NOT NULL,
    uuid        VARCHAR(36) UNIQUE NOT NULL,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    birthDate   TIMESTAMP NOT NULL,
    gender      VARCHAR(10) NOT NULL,
    createdAt   TIMESTAMP NOT NULL,
    updatedAt   TIMESTAMP NOT NULL,
    CONSTRAINT PK_CUSTOMER PRIMARY KEY (customerId, cpf)
);


CREATE TABLE ADDRESS (
    addressId               INT UNIQUE NOT NULL AUTO_INCREMENT,
    addr_customerId         INT NOT NULL,
    state                   VARCHAR(2) NOT NULL,
    city                    VARCHAR(100) NOT NULL,
    neighborhood            VARCHAR(100) NOT NULL,
    zipCode                 VARCHAR(8) NOT NULL,
    street                  VARCHAR(100) NOT NULL,
    number                  INT,
    additionalInformation   VARCHAR(100),
    main                    BOOLEAN NOT NULL,
    CONSTRAINT PK_ADDRESS PRIMARY KEY (addressId)
);

ALTER TABLE ADDRESS ADD CONSTRAINT FK_ADDRESS_CUSTOMER FOREIGN KEY (addr_customerId) REFERENCES CUSTOMER (customerId) ON DELETE CASCADE;