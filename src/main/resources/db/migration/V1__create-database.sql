DROP DATABASE IF EXISTS CUSTOMER_API;
CREATE DATABASE IF NOT EXISTS CUSTOMER_API;
use CUSTOMER_API;
CREATE TABLE CUSTOMER (
    customerId  INT NOT NULL AUTO_INCREMENT,
    cpf         VARCHAR(11) NOT NULL,
    uuid        VARCHAR(36),
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100),
    birthDate   TIMESTAMP,
    gender      VARCHAR(50),
    createdAt   TIMESTAMP NOT NULL,
    updatedAt   TIMESTAMP NOT NULL,
    CONSTRAINT PK_CUSTOMER PRIMARY KEY (customerId, cpf)
);


CREATE TABLE ADDRESS (
    addressId               INT NOT NULL AUTO_INCREMENT,
    addr_customerId         INT NOT NULL,
    state                   VARCHAR(50) NOT NULL,
    city                    VARCHAR(100) NOT NULL,
    neighborhood            VARCHAR(100) NOT NULL,
    zipCode                 VARCHAR(8) NOT NULL,
    street                  VARCHAR(100) NOT NULL,
    number                  INT,
    additionalInformation   VARCHAR(100),
    main                    BOOLEAN,
    CONSTRAINT PK_ADDRESS PRIMARY KEY (addressId)
);

ALTER TABLE ADDRESS ADD CONSTRAINT FK_ADDRESS_CUSTOMER FOREIGN KEY (addr_customerId) REFERENCES CUSTOMER (customerId) ON DELETE CASCADE;