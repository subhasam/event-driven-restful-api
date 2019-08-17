package subhasys.wds.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import subhasys.wds.domain.Task;
import subhasys.wds.enums.TaskStatus;

public class TaskAssignmentDao {

	public static final String WDS_AGENT_TABLE = "agents";
	public static final String WDS_TASK_TABLE = "tasks";
	private final MongoClient mongoDbClient;

	public TaskAssignmentDao(MongoClient mongoDbClient) {
		this.mongoDbClient = mongoDbClient;
	}

	public void findAgentBySkill(String agentSkill, Handler<AsyncResult<JsonObject>> handler) {
		JsonObject agentSearchQuery = new JsonObject();
		agentSearchQuery.put("skill", agentSkill);
		mongoDbClient.findOne(WDS_AGENT_TABLE, agentSearchQuery, null, handler);
	}
	
	public void findTaskById(String taskId, Handler<AsyncResult<JsonObject>> handler) {
		JsonObject taskSerachQuery = new JsonObject();
		taskSerachQuery.put("taskId", taskId);
		mongoDbClient.findOne(WDS_TASK_TABLE, taskSerachQuery, null, handler);
	}
	
	public Task getAgentWithMostRecentTask() {
		List<Task> wdsTaskList = Json.decodeValue(getAllTasksInProgress().toString(), ArrayList.class);
		Optional<Task> agentWithMostRecentTask = wdsTaskList.stream().sorted(Comparator.comparing(Task::getStartTime)).findFirst();
		
		return agentWithMostRecentTask.isPresent() ? agentWithMostRecentTask.get() : null;

	}
	
	private List<JsonObject> getAllTasksInProgress() {
		List<JsonObject> wdsTaskList = new ArrayList<>();
		JsonObject taskListQuery = new JsonObject();
		taskListQuery.put("taskStatus", TaskStatus.INPROGRESS.toString());
		final FindOptions taskSearchOption = new FindOptions();
		taskSearchOption.setSort(new JsonObject().put("startTime", -1));
		taskSearchOption.setLimit(1);
		// db.tasks.find().sort({"startTime": -1}).limit(1)
		mongoDbClient.findWithOptions(WDS_AGENT_TABLE, taskListQuery, taskSearchOption, resultHandler -> {
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
		requestedTask.getAssignedAgent().setAvailable(false);
		requestedTask.getAssignedAgent().setAssignedTaskPriority(requestedTask.getTaskPriority());
		final JsonObject taskRequested = new JsonObject(Json.encode(requestedTask));
		mongoDbClient.insert(WDS_TASK_TABLE, taskRequested, addTaskHandler);

	}

	public void updateTask(final String taskId, final String taskStatus, Handler<AsyncResult<JsonObject>> taskHandler) {
		JsonObject taskSearchQuery = new JsonObject();
		taskSearchQuery.put("taskId", taskId);
		JsonObject taskUpdateQuery = new JsonObject();
		taskUpdateQuery.put("$set", new JsonObject().put("taskStatus", taskStatus));
		mongoDbClient.findOneAndUpdate(WDS_TASK_TABLE, taskSearchQuery, taskUpdateQuery, taskHandler);
	}

}
