Work Distribution API
======================

@author : Subhasis Samal
# An Event Driven RESTful API using Vertx with basic CRUD Operation
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
* **MongoDB installation and Test Data Set Up**
1. Refer the instructions to install MongoDB : https://docs.mongodb.com/manual/installation/
2. Make sure Mongo DB is running on the default port 27017
3. Insert Agent related data with this query  db.agents.insert() and passing the agent data present in this file- ![docs/agentData.json]

* **Clone the Repo from GitHub ***
```
$ git clone https://github.com/subhasam/work-distribution-api.git
$ cd work-distribution-api
$ mvn clean install vertx:runMod
```
After install wait until this message appears in the console which means API is ready to process request.

```
Congratulations !! Work Distribution API is ready to process your request at http://localhost:9090
```

## API Status Check: 
Hit this URL on browser or through any REST Client
```
http://localhost:9090/workdistribution/v1/health-check
Response : Health-Check : Work Distribution API is Up Running...
```
## Testing the Work Distribution API

## You can use any Rest Client like Postman/Insomnia/Chrome's Rest Client Extension to test the API 

API End Points :
================

| METHOD |            End-Point               |    Description   |  Test Data Reference               |
|--------|------------------------------------|------------------|------------------------------------|
| GET    | /workdistribution/v1/health-check  | API Health Check | ![docs/WdsApiAuthentication.json]  |
| GET    | /workdistribution/v1/skills        | Lists Available  | ![docs/WdsApiAvailablesSkills.json]|
|        |                                    | Skills           |                                    |
| POST   | /workdistribution/v1/tasks         | Create a Task    | ![docs/WdsTaskCreationReqRes.json] |
| PATCH  | /workdistribution/v1/task/<taskId> | Mark a Task as   | ![docs/WdsMarkTaskComplete.json]   |
|		 |                                    | Complete         |                                    |

## API Sample Request and Response
Sample Test Data - Request and Response are listed under "docs" directory.

## Work-In-Progress and Future Enhancements

* Integration with Redis
* Logging
* Integration with Message Queue(Active MQ)/Streaming Middleware(Kafka)
* Event Bus with Verticles Developed in Multiple Languages
* Integration with PostgreSQL
* Integration with SaaS
* Integration with CouchBase
* Additional Microservicesx
