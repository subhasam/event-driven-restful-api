Work Distribution API
======================
# An Event Driven RESTful API using Vertx


Build and Deployment : `mvn clean install vertx:runMod`

API Status Check: 
```
http://localhost:8080/workdistribution/v1/health-check
```
## To run the service
```
cd work-distribution-api
mvn clean install vertx:runMod
```

API End Points :
================
GET : /workdistribution/v1/health-check

POST : /workdistribution/v1/task

PUT : /workdistribution/v1/task/id

| METHOD |            End-Point              |    Description   |
|--------|-----------------------------------|------------------|

| GET    | /workdistribution/v1/health-check | API Health Check |
 
| POST   | /workdistribution/v1/task         | Create a Task    |

| PUT    | /workdistribution/v1/task/id      | Update a Task    |