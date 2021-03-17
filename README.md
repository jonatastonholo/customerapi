# Customer REST API
Version: 1.0.0

This is a REST API, developed using **Java**, **Spark** **Framework**, **Gradle**, **Jackson**, **Google Guice**, **JDBI**, **Flyway** and **Docker**.
The database used is MySQL running on Docker.

This API implements a CRUD for a customer with an address list.



The database creation script can be found in **"/src/main/resources/db/V1__create-database.sql"**. Use flyway to migrate.
Docker must be installed. To start the migration, you must first run the command docker-compose the flyway migrate.

---

# Setup

- The spark configured to run on http://localhost:4567
- The MySQL version configured to 5.7, using root password 123456 in port 3306.
    - Run "**docker-compose up -d**" to create / run this database.
- The project uses Flyway to control version of database.

---

# API

## CustomerAPI

The Customer API have 5 Endpoints:
 - [POST] create customer 
 - [GET] List all customers        
 - [GET] Get customer by id
 - [PUT] Update customer by id
 - [DELETE] Delete customer by id
  
### Endpoints
 
#### [POST] Create new customer

Receives a JSON with customer and address and save the customer on database.
  
##### Path: 

>   /customers

 
##### Validations:

 - Valid CPF
 - Age must be less than or equal to 100 years
 - CPF must be unique.
 - Only one main address allowed (when received new main address, will update the other to not main)
 
##### Example:

```
{
  "name": "Plínio Rocha Salgado",
  "email": "pp@gg.com",
  "birthDate": "1989-10-23",
  "cpf": "770.144.163-83",
  "gender": "MASCULINO",
  "address": {
    "state": "SP",
    "city": "São Paulo",
    "neighborhood": "Atibaia",
    "zipCode": "06432-444",
    "street": "Rua Moacir Franco",
    "number": "133",
    "additionalInformation": "Bloco B, Apto 33",
    "main": true
  }
}
```

##### Return

Will return a JSON with created customer or error message.

##### HTTP Status return:

* 201: Success on customer creation
* 400: Invalid request parameters
* 500: Server internal error

 
#### [GET] List all customers

##### Path: 

>    /customers


If called without parameters, will return all customers. Otherwise, will run a query with AND logic of received parameters.
Parameters accepted:
- name
- birthDate
- state
- city
- sortBy
    - (use one of the parameters above)
- sortOrder
    - User *ASC* or *DESC*

##### Example:

###### Without parameters
> /customer

###### Curl 
```
curl -X GET "http://localhost:8080/customers?name=Jonatas&birthDate=1990-01-01&state=MG&city=Belo%20Horizonte&sortBy=name&sortOrder=ASC" -H  "accept: application/json"
```
 
###### Request URL
```
http://localhost:4567/customers?name=Jonatas&birthDate=1990-01-01&state=MG&city=Belo%20Horizonte&sortBy=name&sortOrder=ASC
```

##### Return

Will return a JSON with customers or customer (if pass a filter) or error message.

##### HTTP Status return:

* 200: Success on list customers
* 204: No customers saved on database
* 500: Server internal error
 
 
#### [GET] Get customer by id

##### Path:

>    /customers/{id}
    
##### Return

Will return a JSON with the customer or error .

##### HTTP Status return:

* 200: Success on list customers
* 404: Not found - no customers saved on database with the received ID
* 500: Server internal error
 
#### [PUT] Update customer by id

##### Path:

>    /customers/{id}
    
##### Return

Will return a JSON with the updated customer or error message.

##### HTTP Status return:

* 200: Success on update
* 404: Not found - no customers saved on database with the received ID
* 500: Server internal error

#### [DELETE] Delete customer by id

##### Path:

>    /customers/{id}
    
##### Return

Will return a success or error message.

##### HTTP Status return:

* 200: Success on delete
* 404: Not found - no customers saved on database with the received ID
* 500: Server internal error


---

## AddressAPI

The Address API have 5 Endpoints:
 - [POST] create address 
 - [GET] List all customer address        
 - [GET] Get an address by id of a customer
 - [PUT] Update an address by id of a customer
 - [DELETE] Delete an address by id of a customer
  
### Endpoints
 
#### [POST] Create new address for a customer

Receives a JSON with address and save the address on customer of received id on database.

##### Path

>    /customers/{id}/addresses

##### Example

```
{
  "state": "RJ",
  "city": "Rio de Janeiro",
  "neighborhood": "Tijuca",
  "zipCode": "06432-444",
  "street": "Rua Moacir ",
  "number": "115",
  "additionalInformation": "Apto 1330",
  "main": true
}
```

##### Return

Will return a JSON with created address with created address id, or error message.
 
##### HTTP Status return:

*  201: Success on address creation
*  400: Invalid request parameters
*  404: Customer not found
*  500: Server internal error

#### [GET] List all customer's addresses

Receive a customer id and return all customer addresses as JSON.

##### Path

>    /customers/{id}/addresses
    
##### Return

Will return a JSON with all customer addresses, or error message.
 
##### HTTP Status return:

*  200: Success on list customer's addresses
*  404: No content - no customer's address saved on database
*  500: Server internal error

#### [GET] Get an address by id of a customer 

Receive a customer id and an address id, and return the customer address as JSON.

##### Path

>    /customers/{id}/addresses/{address_id}
    
##### Return

Will return a JSON with address, or error message.
 
##### HTTP Status return:

*  200: Address found
*  404: Address / Customer Not found
*  500: Server internal error

#### [PUT] Update an address by id of a customer 

Receive a customer id and an address id. Update the customer address and return the created address as JSON.

##### Path

>    /customers/{id}/addresses/{address_id}
    
##### Return

Will return a JSON with address, or error message.
 
##### HTTP Status return:

*  200: Address found
*  404: Address / Customer Not found
*  500: Server internal error

#### [DELETE] Delete an address by id of a customer

Receive a customer id and an address id, and delete the address.

##### Path

>    /customers/{id}/addresses/{address_id}
    
##### Return

Will return a success or error message.
 
##### HTTP Status return:

* 200: Success on update
* 404: Address Not found
* 500: Server internal error


# License

GNU General Public License, version 3 (GPLv3)
