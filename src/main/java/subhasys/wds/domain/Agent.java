/**
 * 
 */
package subhasys.wds.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * @author subhasis
 *
 */
public class Agent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8830454874828357275L;

	private String agentId;
	private String fullName;
	private boolean available;
	private Set<Skill> skillSet;

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

	@Override
	public String toString() {
		return String.format("Agent [agentId=%s, fullName=%s]", agentId, fullName);
	}

}
