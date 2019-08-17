/**
 * 
 */
package subhasys.wds.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author subhasis
 * 
 * @description An agent is defined by a unique identifier, and a set of skills
 *              they possess.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Agent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8830454874828357275L;

	private String agentId;
	private String fullName;
	private String skill;
	private Set<Skill> skillSet;
	private int assignedTaskPriority;
	private boolean available;

	/**
	 * 
	 */
	public Agent() {
		super();
	}

	/**
	 * @param agentId
	 * @param fullName
	 * @param skill
	 * @param skillSet
	 * @param assignedTaskPriority
	 * @param available
	 */
	public Agent(String agentId, String fullName, String skill, Set<Skill> skillSet, int assignedTaskPriority,
			boolean available) {
		super();
		this.agentId = agentId;
		this.fullName = fullName;
		this.skill = skill;
		this.skillSet = skillSet;
		this.assignedTaskPriority = assignedTaskPriority;
		//this.assignedTaskPriority = TaskPriority.fromString(assignedTaskPriority);
		this.available = available;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the skill
	 */
	public String getSkill() {
		return skill;
	}

	/**
	 * @param skill the skill to set
	 */
	public void setSkill(String skill) {
		this.skill = skill;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/**
	 * @return the skillSet
	 */
	public Set<Skill> getSkillSet() {
		return skillSet;
	}

	/**
	 * @param skillSet the skillSet to set
	 */
	public void setSkillSet(Set<Skill> skillSet) {
		this.skillSet = skillSet;
	}

	/**
	 * @return the assignedTaskPriority
	 */
	public int getAssignedTaskPriority() {
		return assignedTaskPriority;
	}

	/**
	 * @param assignedTaskPriority the assignedTaskPriority to set
	 */
	public void setAssignedTaskPriority(int assignedTaskPriority) {
		this.assignedTaskPriority = assignedTaskPriority;
	}

	@Override
	public String toString() {
		return String.format("Agent [agentId=%s, fullName=%s, skill=%s, available=%s, skillSet=%s, assignedTask=%s]",
				agentId, fullName, skill, available, skillSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(agentId, available, fullName);
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
		Agent other = (Agent) obj;
		return Objects.equals(agentId, other.agentId) && available == other.available
				&& Objects.equals(fullName, other.fullName);
	}

}
