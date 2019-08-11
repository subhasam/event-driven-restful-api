/**
 * 
 */
package subhasys.wds.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.core.json.Json;
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
	private TaskPriority taskPriority;
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
	public Task(String taskId, TaskPriority taskPriority, Set<Skill> requiredSkillSet, Agent assignedAgent,
			String taskStatus) {
		this.taskId = taskId;
		this.taskPriority = taskPriority;
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
	public TaskPriority getTaskPriority() {
		return taskPriority;
	}

	/**
	 * @param taskPriority the taskPriority to set
	 */
	public void setTaskPriority(TaskPriority taskPriority) {
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
				"Task [taskId=%s, taskPriority=%s, requiredSkillSet=%s, taskStatus=%s, startTime=%s, endTime=%s]",
				taskId, taskPriority, requiredSkillSet, taskStatus, startTime);
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
		Skill skill = new Skill("s1", "Painting");
		Set<Skill> skillSet = new HashSet<Skill>();
		skillSet.add(skill);
		System.out.println(Json.encodePrettily(new Task("1111", TaskPriority.HIGH, skillSet, null, "NEW")));
	}

}
