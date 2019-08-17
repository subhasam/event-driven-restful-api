Work Distribution API
======================
# An Event Driven RESTful API using Vertx
![docs/Event-Driven-WDS-API.png](docs/Event-Driven-WDS-API.png)

### Technology Stack
1. Vertx 3.x
2. Java 8
3. MongoDB (Database)
4. Redis (Cache)
5. Docker (Container)
6. Junit (Testing)
7. Mockito
8. Maven (Build)

## Build and Deployment
* ***Clone the Repo from GitHub ***
```
$ git clone https://github.com/subhasam/work-distribution-api.git
$ cd work-distribution-api
$ mvn clean install vertx:runMod
```

### API Status Check: 
```
http://localhost:8080/workdistribution/v1/health-check
```

API End Points :
================

| METHOD |            End-Point               |    Description   |
|--------|------------------------------------|------------------|
| GET    | /workdistribution/v1/health-check  | API Health Check | 
| POST   | /workdistribution/v1/tasks         | Create a Task    |
| PUT    | /workdistribution/v1/task/<taskId> | Update a Task    |
