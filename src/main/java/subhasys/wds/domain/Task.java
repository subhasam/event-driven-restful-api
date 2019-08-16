/**
 * 
 */
package subhasys.wds.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import subhasys.wds.enums.TaskPriority;

/**
 * @author subhasis
 * 
 *         A task is defined by a unique identifier, a priority, a set of skills
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9169178187316484884L;

	private String taskId;
	@JsonEnumDefaultValue
	private int taskPriority;
	private Set<Skill> requiredSkillSet;
	private Agent assignedAgent;
	private String taskStatus;
	private LocalDateTime startTime;

	/**
	 * 
	 */
	public Task() {
		super();
	}

	/**
	 * @param taskId
	 * @param taskPriority
	 * @param requiredSkillSet
	 * @param assignedAgent
	 * @param taskStatus
	 */
	public Task(String taskId, int taskPriority, Set<Skill> requiredSkillSet, Agent assignedAgent,
			String taskStatus) {
		this.taskId = taskId;
		this.taskPriority = taskPriority; //TaskPriority.fromString(taskPriority);
		this.requiredSkillSet = requiredSkillSet;
		this.assignedAgent = assignedAgent;
		this.taskStatus = taskStatus;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the taskPriority
	 */
	public int getTaskPriority() {
		return taskPriority;
	}

	/**
	 * @param taskPriority the taskPriority to set
	 */
	public void setTaskPriority(int taskPriority) {
		this.taskPriority = taskPriority;
	}

	/**
	 * @return the requiredSkillSet
	 */
	public Set<Skill> getRequiredSkillSet() {
		return requiredSkillSet;
	}

	/**
	 * @param requiredSkillSet the requiredSkillSet to set
	 */
	public void setRequiredSkillSet(Set<Skill> requiredSkillSet) {
		this.requiredSkillSet = requiredSkillSet;
	}

	/**
	 * @return the assignedAgent
	 */
	public Agent getAssignedAgent() {
		return assignedAgent;
	}

	/**
	 * @param assignedAgent the assignedAgent to set
	 */
	public void setAssignedAgent(Agent assignedAgent) {
		this.assignedAgent = assignedAgent;
	}

	/**
	 * @return the taskStatus
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return String.format(
				"Task [taskId=%s, taskPriority=%s, taskStatus=%s, startTime=%s]",
				taskId, taskPriority, taskStatus, startTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(taskId, taskPriority, taskStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Task other = (Task) obj;
		return Objects.equals(taskId, other.taskId) && taskPriority == other.taskPriority
				&& taskStatus == other.taskStatus;
	}

	// Test
	public static void main(String args[]) {
		Skill skill = new Skill("ProblemSolving", "s1");
		Set<Skill> skillSet = new HashSet<>();
		skillSet.add(skill);
		Agent assignedAgent = new Agent("9999", "John Doe", "Engineering", skillSet, 2, true);
		String reqTask = Json.encode(new Task("1111", 2, skillSet, assignedAgent, "NEW"));
		System.out.println(reqTask);
		JsonObject taskJson = new JsonObject(reqTask);
		//taskJson.put("taskPriority", TaskPriority.fromString(2));
		System.out.println(Json.decodeValue(taskJson.encode(), Task.class));
		System.out.println(TaskPriority.NA.getPriority());
	}

}
