/**
 * 
 */
package subhasys.wds.domain;

import java.io.Serializable;
import java.sql.Date;

import subhasys.wds.utils.TaskPriority;

/**
 * @author subhasis
 *
 */
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9169178187316484884L;

	private String taskId;
	private TaskPriority taskPriority;
	private TaskStatus taskStatus;
	private Date startTime;
	private Date endTime;

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
	 * @return the taskStatus
	 */
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return String.format("Task [taskId=%s, taskPriority=%s, taskStatus=%s]", taskId, taskPriority, taskStatus);
	}

}
