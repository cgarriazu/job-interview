# Capitole

Back-end service for Capitole's job interview

## Stack 🛠️
- maven
- java 11
- spring-boot 2.7.7
- spring jdbc
- spring web
- H2 database
- spring test
- JUnit 5

## Build 🔧
To compile, build and install the build result of the project, run this command:
```bash
$ mvn clean install
```

## Run
To run the project:
```bash
$ mvn spring-boot:run
```

Local environment runs upon:
<http://localhost:8080>

The endpoint to get price by brand_id, product_id and date is:
<http://localhost:8080/capitole/prices/brands/{brand_id}/products/{product_id}/date/{date}>

To check H2 database instanced in memory use:
<http://localhost:8080/h2-console>

## Unit Tests
To run Unit test's suites:
```bash
$ mvn clean test
```
