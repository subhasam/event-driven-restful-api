/**
 * 
 */
package subhasys.wds.domain;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author subhasis
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2046528189848653924L;

	private String skillName;
	private String skillId;
	
	/**
	 * 
	 */
	public Skill() {
		super();
	}

	/**
	 * @param skillName
	 * @param skillId
	 */
	public Skill(String skillName, String skillId) {
		this.skillName = skillName;
		this.skillId = skillId;
	}

	/**
	 * @return the skillName
	 */
	public String getSkillName() {
		return skillName;
	}

	/**
	 * @param skillName the skillName to set
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	/**
	 * @return the skillId
	 */
	public String getSkillId() {
		return skillId;
	}

	/**
	 * @param skillId the skillId to set
	 */
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	@Override
	public String toString() {
		return String.format("Skill [skillName=%s, skillId=%s]", skillName, skillId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(skillId, skillName);
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
		Skill other = (Skill) obj;
		return Objects.equals(skillId, other.skillId) && Objects.equals(skillName, other.skillName);
	}

}
