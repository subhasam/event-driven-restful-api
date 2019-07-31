Work Distribution API
======================
# An Event Driven RESTful API using Vertx
![docs/Event-Driven-WDS-API.png](docs/Event-Driven-WDS-API.png)

## Build and Deployment
```
cd work-distribution-api
mvn clean install vertx:runMod
```
API Status Check: 
```
http://localhost:8080/workdistribution/v1/health-check
```

API End Points :
================

| METHOD |            End-Point              |    Description   |
|--------|-----------------------------------|------------------|
| GET    | /workdistribution/v1/health-check | API Health Check | 
| POST   | /workdistribution/v1/task         | Create a Task    |
| PUT    | /workdistribution/v1/task/id      | Update a Task    |
