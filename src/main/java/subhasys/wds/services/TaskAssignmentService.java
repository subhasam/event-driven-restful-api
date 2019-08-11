/**
 * 
 */
package subhasys.wds.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import subhasys.wds.dao.AgentDao;
import subhasys.wds.dao.TaskAssignmentDao;
import subhasys.wds.domain.Agent;
import subhasys.wds.domain.Skill;
import subhasys.wds.domain.Task;
import subhasys.wds.enums.TaskPriority;
import subhasys.wds.enums.TaskStatus;
import subhasys.wds.exception.WdsApiException;

/**
 * @author subhasis
 *
 */
public class TaskAssignmentService {

	private final TaskAssignmentDao taskAssignmentDao;
	private final AgentDao agentDao;
	public static final String COLUMN_REQUESTED_TASKS = "tasks";
	public static final String COLUMN_TASK_ID = "taskId";

	/**
	 * @param taskAssignmentDao
	 */
	public TaskAssignmentService(TaskAssignmentDao taskAssignmentDao, AgentDao agentDao) {
		this.taskAssignmentDao = taskAssignmentDao;
		this.agentDao = agentDao;
		System.out.println("TaskAssignmentService - DONE Creating TaskAssignmentService");
	}

	public boolean isValidateTaskRequest(RoutingContext wdsApiContext) {
		System.out.println("TaskAssignmentService : isValidateTaskRequest() - Received New Task ==> " + Json.encodePrettily(wdsApiContext.getBodyAsJson()));//getValue("task")
		// wdsApiContext.getBodyAsJson().getValue("task");
		Task requestedTask = null;
		try {
			requestedTask = Json.decodeValue(wdsApiContext.getBodyAsString("UTF-8"), Task.class);
		} catch (DecodeException decodExcp) {
			decodExcp.printStackTrace();
		}
		
		System.out.println("TaskAssignmentService : isValidateTaskRequest() - Parsed Task for Validation ==> " + Json.encodePrettily(requestedTask));
		System.out.println(" Task is Null ? : " + Objects.isNull(requestedTask) + ", Task ID Null ? : " + Objects.isNull(requestedTask.getTaskId())
		+ ", SkillSet Null ? : "+ Objects.isNull(requestedTask.getRequiredSkillSet()));
		return Objects.nonNull(requestedTask) && Objects.nonNull(requestedTask.getTaskId()) && Objects.nonNull(requestedTask.getRequiredSkillSet());

	}
	
	public void assignTaskToAgent(RoutingContext wdsApiContext) {
		System.out.println("assignTaskToAgent() :: Going to pull Available Agents from DB.");
		// List<Agent> agentsList = agentDao.getAllAgents().stream().collect(Collectors.toList());
		//List<Agent> agentsList = Json.decodeValue(agentDao.getAllAgents().toString(), ArrayList.class);
		List<Agent> availableAgentList = new ArrayList<>();
		agentDao.getAllAgents(agentSearchHandler -> {
			if (agentSearchHandler.succeeded()) {
				System.out.println("assignTaskToAgent() : Total Agents from MongoDB = " + agentSearchHandler.result());
				Json.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					/*
					 * agentSearchHandler.result() .forEach(agent ->
					 * availableAgentList.add(Json.decodeValue(agent.encode(), Agent.class)));
					 */
					agentSearchHandler.result()
							.forEach(agent -> {
								try {
									availableAgentList.add(Json.mapper.readValue(agent.encodePrettily(), Agent.class));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							});
					/*availableAgentList.add(Json.mapper.readValue(agentSearchHandler.result().stream().toString(),
							new TypeReference<List<Agent>>() {
							}));*/
				//return;
				System.out.println("assignTaskToAgent() :: Got Agent List \n" + Json.encodePrettily(availableAgentList));
			} else {
				System.out.println("assignTaskToAgent() : No Agents found or Query Failed");
				agentSearchHandler.otherwiseEmpty();
			}
			System.out.println("assignTaskToAgent() : Got Agent List");
			try {
				taskAssignment(availableAgentList, wdsApiContext);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		// Agent selectedAgent = null;
		/*
		 * for (Agent availableAgent : agentsList) { if
		 * (isAgentEligibleForTask(availableAgent, taskRequested)) { selectedAgent =
		 * availableAgent; taskRequested.setAssignedAgent(selectedAgent); break; }
		 * 
		 * }
		 */
		// Find the Eligible Agent - moved to taskAssignment()
		
		/*
		Optional<Agent> selectedAgent = availableAgentList.stream()
				.filter(availableAgent -> isAgentEligibleForTask(availableAgent, taskRequested)).findFirst();
		// No Eligible Agent found with the required Skill
		if (Objects.isNull(selectedAgent)) {
			System.out.println("assignTaskToAgent() : Sorry, No Agent with the required skill found for the Task.");
			wdsApiContext.response().setStatusCode(500).end("Opps !! Sorry, No Agent with the required skill found for the Task.");
			wdsApiContext.fail(500);
		}
		// Is Agent with required Skill Available ?
		if ( null != selectedAgent && selectedAgent.get().isAvailable()) {
			taskRequested.setAssignedAgent(selectedAgent.get());
		} else {

			// Get Agent with LOWER Priority Task Compared to Requested Task
			Optional<Agent> agentWithLowPriorityTask = availableAgentList.stream()
					.filter(agent -> agent.getAssignedTaskPriority().getPriority() < taskRequested.getTaskPriority()
							.getPriority()
							&& TaskPriority.HIGH.name().equalsIgnoreCase(taskRequested.getTaskPriority().name()))
					.findFirst();

			// With Same Task Priority : Get Agent with Most Recent Task Priority
			taskRequested.setAssignedAgent(Optional.ofNullable(agentWithLowPriorityTask.get())
					.orElse(taskAssignmentDao.getAgentWithMostRecentTask().getAssignedAgent()));
		}

		// Save Task with assigned Agent
		taskRequested.setStartTime(LocalDateTime.now());
		taskAssignmentDao.createTask(taskRequested, taskCreator -> {
			if (taskCreator.succeeded()) {
				System.out.println("Task Creation successful");
			}
		});
		
		// selectedAgent.get().setAvailable(false);
		// selectedAgent.get().setAssignedTaskPriority(taskRequested.getTaskPriority());
		

		// Update Agent with Current Assigned Task
		agentDao.updateAgentInfo(taskRequested.getAssignedAgent(), updateHander -> {
			if (updateHander.succeeded()) {
				wdsApiContext.response().end(Json.encode(taskRequested));
			}
		});
		*/
	}
	
	private void taskAssignment(List<Agent> availableAgentList, RoutingContext wdsApiContext) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("taskAssignment() :: Getting Task from RoutingContext.");
		Task taskRequested = Json.mapper.readValue(wdsApiContext.getBodyAsString("UTF-8"), Task.class);
		//Optional<Agent> selectedAgent
		Agent selectedAgent = availableAgentList.stream()
				.filter(availableAgent -> isAgentEligibleForTask(availableAgent, taskRequested)).findFirst().orElse(null);
		// No Eligible Agent found with the required Skill
		if (Objects.isNull(selectedAgent)) {
			System.out.println("taskAssignment() : Sorry, No Agent with the required skill found for the Task.");
			//wdsApiContext.response().setStatusCode(500).end("Opps !! Sorry, No Agent with the required skill found for the Task.");
			//wdsApiContext.fail(500);
			wdsApiContext.fail(500, new WdsApiException("Opps !! Sorry, No Agent with the required skill found for the Task.", null));
			return;
		} else {
			System.out.println("taskAssignment() :: Got Agent with matching skill =>" + Json.encodePrettily(selectedAgent));//selectedAgent.get()
		}
		// Is Agent with required Skill Available ?
		// if ( selectedAgent.isPresent() && selectedAgent.get().isAvailable()) {
		if ( selectedAgent.isAvailable()) {
			taskRequested.setAssignedAgent(selectedAgent); //selectedAgent.get()
			System.out.println("taskAssignment() :: Task with Agent =>" + Json.encodePrettily(taskRequested));
			mapAgentWithTask(taskRequested, wdsApiContext);
			wdsApiContext.response().setStatusMessage(Json.encodePrettily(taskRequested));
		} else {
			// Get Agent with LOWER Priority Task Compared to Requested Task
			Optional<Agent> agentWithLowPriorityTask = availableAgentList.stream()
					.filter(agent -> agent.getAssignedTaskPriority().getPriority() < taskRequested.getTaskPriority()
							.getPriority()
							&& TaskPriority.HIGH.getPriority() == taskRequested.getTaskPriority().getPriority())
					.findFirst();
			if (agentWithLowPriorityTask.isPresent()) {
				mapAgentWithTask(taskRequested, wdsApiContext);
			}
			// With Same Task Priority : Get Agent with Most Recent Task Priority
			taskRequested.setAssignedAgent(Optional.ofNullable(agentWithLowPriorityTask.get())
					.orElse(taskAssignmentDao.getAgentWithMostRecentTask().getAssignedAgent()));
		}
	}
	
	private void mapAgentWithTask(Task taskRequested, RoutingContext wdsApiContext) {
		System.out.println("mapAgentWithTask() :: Creating Task");
		// Save Task with assigned Agent
		taskAssignmentDao.createTask(taskRequested, taskCreator -> {
			if (taskCreator.succeeded()) {
				System.out.println("mapAgentWithTask() :: Task Creation SUCCESSFUL");
			} else {
				System.out.println("mapAgentWithTask() :: Task Creation FAILED ==>"+ taskCreator.cause().getMessage());
				wdsApiContext.fail(500);
			}
		});
		
		/*
		selectedAgent.get().setAvailable(false);
		selectedAgent.get().setAssignedTaskPriority(taskRequested.getTaskPriority());
		*/
		// Update Agent with Current Assigned Task
		agentDao.updateAgentInfo(taskRequested.getAssignedAgent(), updateHander -> {
			if (updateHander.succeeded()) {
				System.out.println("mapAgentWithTask() :: Updating Agent Info with Assigned Task in DB ==>" + Json.encode(taskRequested));
				wdsApiContext.response().end(Json.encode(taskRequested));
			} else {
				System.out.println("mapAgentWithTask() :: Agent info update in DB FAILED ==>" + updateHander.cause().getMessage());
			}
		});

	}

	public boolean isAgentEligibleForTask(Agent agent, Task task) {

		Set<Skill> skillSetsRequiredByTask = task.getRequiredSkillSet();
		String agentSkill = agent.getSkill();
		boolean agentEligibility = false;

		// Verify if either Task Skills OR Agent Skills are null
		if (Objects.isNull(agentSkill) || Objects.isNull(skillSetsRequiredByTask)) {
			return agentEligibility;
		}
		
		/*
		 * for (Skill requiredSkill : skillSetsRequiredByTask) { if (null !=
		 * requiredSkill && !agentSkillsSet.contains(requiredSkill)) { return false;
		 * 
		 * } }
		 */
		/*
		agentEligibility = skillSetsRequiredByTask.stream()
				.anyMatch(requiredSkill -> Objects.nonNull(requiredSkill) && !agentSkillsSet.contains(requiredSkill));
		*/
		agentEligibility = skillSetsRequiredByTask.stream()
				.anyMatch(requiredSkill -> Objects.nonNull(requiredSkill) && agentSkill.equalsIgnoreCase(requiredSkill.getSkillName()));
		System.out.println("isAgentEligibleForTask() :: Agent Elibility ==> "+agentEligibility);

		return agentEligibility;

	}

	public void markTaskCompleted(RoutingContext wdsApiContext) {
		// TODO - Database Call to Mark Task COMPLETE
		String taskId = wdsApiContext.request().getParam("taskId");
		taskAssignmentDao.findTaskById(taskId, asyncTaskhandler -> {
			if (asyncTaskhandler.failed()) {
				System.out.println("markTaskCompleted :: Find Task By ID failed ==>" + asyncTaskhandler.failed());
				wdsApiContext.fail(asyncTaskhandler.cause());
			} else {
				JsonObject existingTask = asyncTaskhandler.result();
				if (null == existingTask) {
					wdsApiContext.fail(500);
				} else {
					// new JsonObject(Json.encode(completedTask))
					existingTask.put("taskStatus", TaskStatus.COMPLETED.name());
					taskAssignmentDao.updateTask(existingTask, taskUpdateHandler -> {
						if (taskUpdateHandler.succeeded()) {
							wdsApiContext.response().end(existingTask.encodePrettily());
						}
					});
					// wdsApiContext.put("task", existingTask);
					// wdsApiContext.next();
				}
			}
		});
		/*
		 * if (Objects.nonNull(existingTask)) {
		 * existingTask.setTaskStatus(TaskStatus.COMPLETED);
		 * taskAssignmentDao.updateTask(existingTask, null); return existingTask; } else
		 * { throw new WdsApiException("500", "Request Task does't exist", null); }
		 */
	}
}
