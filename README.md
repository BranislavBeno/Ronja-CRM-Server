[![Application Tests](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/tests.yml/badge.svg)](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/tests.yml)
[![Application Deploy](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/deploy.yml/badge.svg)](https://github.com/BranislavBeno/Ronja-CRM-Server/actions/workflows/deploy.yml)  
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_RonjaServer&metric=security_rating)](https://sonarcloud.io/dashboard?id=BranislavBeno_RonjaServer)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_RonjaServer&metric=coverage)](https://sonarcloud.io/dashboard?id=BranislavBeno_RonjaServer)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_RonjaServer&metric=ncloc)](https://sonarcloud.io/dashboard?id=BranislavBeno_RonjaServer)  
[![](https://img.shields.io/badge/Java-19-blue)](/build.gradle)
[![](https://img.shields.io/badge/Spring%20Boot-3.0.0-blue)](/build.gradle)
[![](https://img.shields.io/badge/Testcontainers-1.17.6-blue)](/build.gradle)
[![](https://img.shields.io/badge/Gradle-7.6-blue)](/gradle/wrapper/gradle-wrapper.properties)
[![](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)  

## Ronja CRM server
Application is simple REST-API server, which provides basic CRUD operations over connected database for simple CRM
system and by default, listens on port 8087. 

### Installation
Preferred way of installation is to pull and run prepared docker image `docker pull beo1975/ronja-server:1.3.3`.  
Precondition is to have `docker` installed on the hosting OS.

Alternatively is possible to build and run the application as a fat jar on any hosting OS with `Java 19` installed.

> Application expects running instance of MySQL database engine.
> It's recommended to install and run MySQL as a docker image by usage of prepared `docker-compose.yml` file.
> No other services are required.

### Usage
For sending requests and receiving responses use `Postman`, `curl` or any web browser.

### API description
Following endpoints are available for usage:

- **GET /customers/list** - returns list of all customers or empty list when no customer was found.
- **POST /customers/add** - adds new customer.
- **PUT /customers/update** - updates existing customer. Update is refused when a customer with given ID doesn't exist.
- **DELETE /customers/delete/{id}** - deletes customer with given ID. Deletion is refused when customer with given ID doesn't exist.


- **GET /representatives/list** - returns list of all representatives or empty list when no representative was found.
- **GET /representatives/search?customerId={id}** - returns list of all representatives with matching customer relation or empty list in case of searched id doesn't match to any customer.
- **GET /representatives/scheduled?days={count of days}** - returns list of all representatives with .
- **POST /representatives/add** - adds new representative.
- **PUT /representatives/update** - updates existing representative. Update is refused when a representative with given ID doesn't exist.
- **DELETE /representatives/delete/{id}** - deletes representative with given ID. Deletion is refused when representative with given ID doesn't exist.