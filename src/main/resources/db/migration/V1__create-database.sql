DROP DATABASE IF EXISTS CUSTOMER_API;
CREATE DATABASE IF NOT EXISTS CUSTOMER_API;
use CUSTOMER_API;
CREATE TABLE CUSTOMER (
    customerId  INT NOT NULL,
    cpf         VARCHAR(11) NOT NULL,
    uuid        VARCHAR(36),
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100),
    birthDate   DATETIME,
    gender      VARCHAR(100),
    createdAt   DATETIME NOT NULL,
    updatedAt   DATETIME NOT NULL,
    addressId       INT NOT NULL,
    CONSTRAINT PK_CUSTOMER PRIMARY KEY (customerId, cpf)
);


CREATE TABLE ADDRESS (
    addressId       INT NOT NULL,
    state           VARCHAR(50) NOT NULL,
    city            VARCHAR(50) NOT NULL,
    neighborhood    VARCHAR(50) NOT NULL,
    zipCode         VARCHAR(8) NOT NULL,
    street          VARCHAR(50) NOT NULL,
    number          INT,
    main            BOOLEAN,
    CONSTRAINT PK_ADDRESS PRIMARY KEY (addressId)
);

ALTER TABLE CUSTOMER ADD CONSTRAINT FK_CUSTOMER_ADDRESS FOREIGN KEY (customerId) REFERENCES ADDRESS (addressId);