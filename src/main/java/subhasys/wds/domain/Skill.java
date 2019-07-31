/**
 * 
 */
package subhasys.wds.domain;

import java.io.Serializable;

/**
 * @author subhasis
 *
 */
public class Skill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2046528189848653924L;

	private String skillName;
	private String sillId;

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
	 * @return the sillId
	 */
	public String getSillId() {
		return sillId;
	}

	/**
	 * @param sillId the sillId to set
	 */
	public void setSillId(String sillId) {
		this.sillId = sillId;
	}

	@Override
	public String toString() {
		return String.format("Skill [skillName=%s, sillId=%s]", skillName, sillId);
	}

}
