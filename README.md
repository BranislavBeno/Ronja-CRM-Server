[![Application Tests](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/tests.yml/badge.svg)](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/tests.yml)
[![Application Deploy](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/deploy.yml/badge.svg)](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/deploy.yml)  
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_RonjaServer&metric=security_rating)](https://sonarcloud.io/dashboard?id=BranislavBeno_RonjaServer)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_RonjaServer&metric=coverage)](https://sonarcloud.io/dashboard?id=BranislavBeno_RonjaServer)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_RonjaServer&metric=ncloc)](https://sonarcloud.io/dashboard?id=BranislavBeno_RonjaServer)  
[![](https://img.shields.io/badge/Java-21-blue)](/build.gradle)
[![](https://img.shields.io/badge/Spring%20Boot-3.2.1-blue)](/build.gradle)
[![](https://img.shields.io/badge/Testcontainers-1.19.3-blue)](/build.gradle)
[![](https://img.shields.io/badge/Gradle-8.5-blue)](/gradle/wrapper/gradle-wrapper.properties)
[![](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)  

## Ronja CRM server
Application is a simple REST-API server, which provides basic CRUD operations over a connected database for a simple CRM
system and by default, listens on port 8087. 

### Installation
Preferred way of installation is to pull and run prepared docker image `docker pull beo1975/ronja-server:1.3.3`.  
Precondition is to have `docker` installed on the hosting OS.

Alternatively is possible to build and run the application as a fat jar on any hosting OS with `Java 21` installed.

> Application expects running instance of MySQL database engine.
> It's recommended to install and run MySQL as a docker image by usage of prepared `docker-compose.yml` file.
> No other services are required.

### Usage
For sending requests and receiving responses use `Postman`, `curl` or any web browser.

### API description
Following endpoints are available for usage:

#### Customers
- **GET /customers/list** - return list of all customers or empty list when no customer was found.
- **POST /customers/add** - add new customer.
- **PUT /customers/update** - update existing customer. Update is refused when a customer with given ID doesn't exist.
- **DELETE /customers/delete/{id}** - delete customer with given ID. Deletion is refused when customer with given ID doesn't exist.  

Request body example for customer:
```json
{
  "companyName": "FirstCorp",
  "category": "LEVEL_1",
  "focus": "BUILDER",
  "status": "ACTIVE"
}
```
Allowed values for:
- `category`: LEVEL_1, LEVEL_2, LEVEL_3
- `focus`: BUILDER, MANUFACTURE, SPECIALIZED_MANUFACTURE, TRADE
- `status`: ACTIVE, INACTIVE

#### Representatives
- **GET /representatives/list** - return list of all representatives or empty list when no representative was found.
- **GET /representatives/search?customerId={id}** - return list of all representatives with matching customer relation or empty list in case of searched id doesn't match to any customer.
- **GET /representatives/scheduled?days={dayCount}** - return list of all representatives with scheduled visit within today and today + `dayCount`.
- **POST /representatives/add** - add new representative.
- **PUT /representatives/update** - update existing representative. Update is refused when a representative with given ID doesn't exist.
- **DELETE /representatives/delete/{id}** - delete representative with given ID. Deletion is refused when representative with given ID doesn't exist.

Request body example for representative:
```json
{
  "firstName": "Employee",
  "lastName": "First",
  "position": "CFO",
  "region": "EMEA",
  "notice": "",
  "status": "ACTIVE",
  "lastVisit": "2021-11-08",
  "scheduledVisit": "2022-12-21",
  "phoneNumbers": [
    {
      "contact": "+456789123",
      "primary": true,
      "type": "work"
    }
  ],
  "emails": [
    {
      "contact": "john@example.com",
      "primary": true,
      "type": "work"
    },
    {
      "contact": "bob@example.com",
      "primary": false,
      "type": "home"
    }
  ],
  "customer": {
    "id": 1,
    "companyName": "FirstCorp",
    "category": "LEVEL_1",
    "focus": "BUILDER",
    "status": "ACTIVE"
  },
  "contactType": "PERSONAL"
}
```
Allowed values for:
- `status`: ACTIVE, INACTIVE
- `contactType`: PERSONAL, MAIL, PHONE, ON_LINE
- `lastVisit`: date must be past or present
- `scheduledVisit`: date must be present or in the future

#### Metal prices
- **GET /metals/list** - return list of metal prices for copper, aluminum and lead on respective date from a connected database or empty list when no metal prices were found.
- **GET /metals/exchange** - return metal prices for copper, aluminum and lead on respective date fetched from London Metal Exchange. This endpoint is used for a scheduled job, which runs from Monday till Friday at 23:00
