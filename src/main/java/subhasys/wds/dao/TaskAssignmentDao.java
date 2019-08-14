package subhasys.wds.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import subhasys.wds.domain.Task;
import subhasys.wds.enums.TaskStatus;

public class TaskAssignmentDao {

	public static final String WDS_AGENT_TABLE = "agents";
	public static final String WDS_TASK_TABLE = "tasks";
	public static final String COLUMN_SUBSCRIPTIONS = "subscriptions";
	public static final String COLUMN_SUBSCRIBER_COUNT = "subscriber_count";
	private final MongoClient mongoDbClient;

	public TaskAssignmentDao(MongoClient mongoDbClient) {
		this.mongoDbClient = mongoDbClient;
		System.out.println("TaskAssignmentDao - DONE Creating TaskAssignmentDao");
	}

	public void findAgentBySkill(String agentSkill, Handler<AsyncResult<JsonObject>> handler) {
		JsonObject agentSearchQuery = new JsonObject();
		//agentSearchQuery.put("_id", agentSkill);
		agentSearchQuery.put("skill", agentSkill);
		mongoDbClient.findOne(WDS_AGENT_TABLE, agentSearchQuery, null, handler);
	}
	
	public void findTaskById(String taskId, Handler<AsyncResult<JsonObject>> handler) {
		JsonObject taskSerachQuery = new JsonObject();
		//taskSerachQuery.put("_id", taskId);
		System.out.println("TaskAssignmentDAO :: findTaskById() :: Find Task By ID =>" +taskId);
		taskSerachQuery.put("taskId", taskId);
		mongoDbClient.findOne(WDS_TASK_TABLE, taskSerachQuery, null, handler);
	}
	
	public void findAgentBySkill(String requiredSkill, Task taskForExecution, Handler<AsyncResult<JsonObject>> agentSearchHandler) {
		JsonObject agentSearchQuery = new JsonObject(Json.encode(taskForExecution));
		//TODO
		//agentSearchQuery.put("available", Boolean.TRUE);
		//agentSearchQuery.put("skillSet", requiredSkill);
		agentSearchQuery.put("skill", taskForExecution.getAssignedAgent().getSkill());
		mongoDbClient.findOne(WDS_AGENT_TABLE, agentSearchQuery, null, agentSearchHandler);
	}
	
	public Task getAgentWithMostRecentTask() {
		List<Task> wdsTaskList = Json.decodeValue(getAllTasksInProgress().toString(), ArrayList.class);
		Optional<Task> agentWithMostRecentTask = wdsTaskList.stream().sorted(Comparator.comparing(Task::getStartTime)).findFirst();
		
		return agentWithMostRecentTask.get();

	}
	
	public List<JsonObject> getAllTasksInProgress() { //AsyncResult<JsonObject> agentRetrievalHandler
		List<JsonObject> wdsTaskList = new ArrayList<>();
		JsonObject taskListQuery = new JsonObject();
		taskListQuery.put("taskStatus", TaskStatus.INPROGRESS.toString());
		mongoDbClient.find(WDS_AGENT_TABLE, taskListQuery, resultHandler -> {
			if (resultHandler.succeeded()) {
				wdsTaskList.addAll(resultHandler.result());
				return;
			} else {
				resultHandler.otherwiseEmpty();
			}
		});
		return wdsTaskList;
	}

	public void createTask(Task requestedTask, Handler<AsyncResult<String>> addTaskHandler) {//Task requestedTask, Handler<AsyncResult<String>> addTaskHandler
		requestedTask.setStartTime(LocalDateTime.now());
		final JsonObject taskRequested = new JsonObject(Json.encode(requestedTask));
		System.out.println("TaskAssignmentDao : createTask() - Inderting Task to DB =>"+ taskRequested.encodePrettily());
		mongoDbClient.insert(WDS_TASK_TABLE, taskRequested, addTaskHandler);

	}

	/*public void updateTask(JsonObject updatedTask, Handler<AsyncResult<JsonObject>> taskHandler) {
		JsonObject taskUpdateQuery = new JsonObject();
		System.out.println("TaskAssignmentDao: updateTask() - updatedTask =>" + Json.encodePrettily(updatedTask));
		// System.out.println("updatedTask OID =>" + updatedTask.getJsonObject("_id").getString("$oid")); //.getJsonObject("$oid")
		taskUpdateQuery.put("taskId", updatedTask.getString("taskId"));
		// taskUpdateQuery.put("_id", new JsonObject().put("$oid", updatedTask.getJsonObject("_id").getString("$oid")));
		System.out.println("TaskAssignmentDao : updateTask() - Update Query ==>"+ Json.encodePrettily(taskUpdateQuery));
		updatedTask.put("$set", new JsonObject().put("taskStatus", updatedTask.getString("taskStatus")));
		System.out.println("TaskAssignmentDao :: updateTask(), task Update Query =>"+ Json.encodePrettily(taskUpdateQuery) +" , Task to be marked Completed is =>" +Json.encodePrettily(updatedTask));
		mongoDbClient.findOneAndUpdate(WDS_TASK_TABLE, taskUpdateQuery, updatedTask, taskUpdateRes -> {
			if (taskUpdateRes.failed()) {
				System.out.println("TaskAssignmentDAO :: updateTask() Failed to close task ==>" + taskUpdateRes.cause().getMessage());
				taskHandler.handle(taskUpdateRes);
				return;
			} else {
				System.out.println("TaskAssignmentDao :: updateTask(), Task Update SUCCESSFUL");
			}
		});
	}*/
	
	public void updateTask(String taskId, Handler<AsyncResult<JsonObject>> taskHandler) {
		JsonObject taskSearchQuery = new JsonObject();
		taskSearchQuery.put("taskId", taskId);
		JsonObject taskUpdateQuery = new JsonObject();
		// taskUpdateQuery.put("_id", new JsonObject().put("$oid", updatedTask.getJsonObject("_id").getString("$oid")));
		System.out.println("TaskAssignmentDao : updateTask() - Update Query ==>"+ Json.encodePrettily(taskSearchQuery));
		taskUpdateQuery.put("$set", new JsonObject().put("taskStatus", TaskStatus.COMPLETED.name()));
		System.out.println("TaskAssignmentDao :: updateTask(), task Update Query =>"+ Json.encodePrettily(taskSearchQuery) +" , Task to be marked Completed is =>" +Json.encodePrettily(taskUpdateQuery));
		mongoDbClient.findOneAndUpdate(WDS_TASK_TABLE, taskSearchQuery, taskUpdateQuery, taskUpdateRes -> {
			if (taskUpdateRes.failed()) {
				System.out.println("TaskAssignmentDAO :: updateTask() Failed to close task ==>" + taskUpdateRes.cause().getMessage());
				taskHandler.handle(taskUpdateRes);
				return;
			} else {
				System.out.println("TaskAssignmentDao :: updateTask(), Task Update SUCCESSFUL" + Json.encodePrettily(taskUpdateRes.result()));
			}
		});
	}

}
