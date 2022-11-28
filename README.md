# Mortgage Calculator

Moneybin Mortgage Calculator is used by Moneybin to calculate mortgage loans for customers.  

Application consist of the following components:
1. Mysql database
2. REST services exposed for mortgage calculation
3. Web interface for adding new mortgages or editing/removing existing ones

## Compilation
А
Use the build automation tool [maven](https://maven.apache.org/) to work with Moneybin Mortgage Calculator.
Run the following:
```bash
mvn clean install
```

## Deployment

Use Docker and Docker Compose tools to deploy Moneybin Mortgage Calculator. It will create two containers, one for Database, another for the Spring Boot Application
Run the following command to run the application:
```bash
docker-compose up
```

## Running and testing

### Web interface
Application is deployed and available under the URL http://194.195.248.78:8080/

### REST endpoints
There are three REST endpoints available:
#### 1. /api/moneybin/mortgage/instalment/customer
   Used to calculate mortgage loan per one customer.
```json
    {
        "customerName": "Pekka Parikka",
        "loanAmount": 1000.0,
        "interestRate": 1.0,
        "loanDurationYears": 3
    }
```
#### 2. /api/moneybin/mortgage/instalment/customers
   Used to calculate mortgage loan per list of customers.
```json
[
    {
        "customerName": "Pekka Parikka",
        "loanAmount": 1000.0,
        "interestRate": 1.0,
        "loanDurationYears": 3
    },
    {
        "customerName": "Johan Sigerstadt",
        "loanAmount": 3500.0,
        "interestRate": 2.0,
        "loanDurationYears": 10
    }
]
```
#### 3. /api/moneybin/mortgage/instalment/customers/bulk
   Used to calculate mortgage loan per attached csv file with customers data. 
   ```csv
Customer,Total mortgageLoan,Interest,Years
Juha,1000,5,2
Karvinen,4356,1.27,6
Claes Månsson,1300.55,8.67,2
"Clarencé,Andersson",2000,6,4










.
```
   


## Important note

Docker image arm64v8/mysql:oracle is used for MySql Database due to setup for MacBook M1 chip. In case of application running at another processor version, change image provider to mysql/latest.
