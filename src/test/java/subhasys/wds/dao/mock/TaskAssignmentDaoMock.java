package subhasys.wds.dao.mock;

import java.util.List;

import javax.annotation.Generated;

import org.junit.platform.engine.support.hierarchical.Node.Invocation;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import subhasys.wds.dao.TaskAssignmentDao;
import subhasys.wds.domain.Task;

public class TaskAssignmentDaoMock {//extends MockUp<TaskAssignmentDao> {

	private boolean findAgentBySkillMocked = false;
	private int findAgentBySkillExecutions = 0;
	private boolean findTaskByIdMocked = false;
	private int findTaskByIdExecutions = 0;
	private boolean getAgentWithMostRecentTaskMocked = false;
	private int getAgentWithMostRecentTaskExecutions = 0;
	private Task getAgentWithMostRecentTaskReturnValue = null;
	private boolean getAllTasksInProgressMocked = false;
	private int getAllTasksInProgressExecutions = 0;
	private List<JsonObject> getAllTasksInProgressReturnValue = null;
	private boolean createTaskMocked = false;
	private int createTaskExecutions = 0;
	private boolean updateTaskMocked = false;
	private int updateTaskExecutions = 0;

	public static TaskAssignmentDaoMock create() {
		return new TaskAssignmentDaoMock();
	}

	@MethodRef(name = "findAgentBySkill", signature = "(QString;QHandler<QAsyncResult<QJsonObject;>;>;)V")
	@Mock
	void findAgentBySkill(Invocation inv, String agentSkill, Handler<AsyncResult<JsonObject>> handler) {
		findAgentBySkillExecutions++;
		if (findAgentBySkillMocked) {
			return;
		}
		inv.proceed();
	}

	public void setUpMockFindAgentBySkill() {
		findAgentBySkillMocked = true;
		findAgentBySkillExecutions = 0;
	}

	public int getFindAgentBySkillExecutions() {
		return findAgentBySkillExecutions;
	}

	public boolean isFindAgentBySkillExecuted() {
		return findAgentBySkillExecutions > 0;
	}

	public void resetMockFindAgentBySkill() {
		findAgentBySkillMocked = false;
		findAgentBySkillExecutions = 0;
	}

	@MethodRef(name = "findTaskById", signature = "(QString;QHandler<QAsyncResult<QJsonObject;>;>;)V")
	@Mock
	void findTaskById(Invocation inv, String taskId, Handler<AsyncResult<JsonObject>> handler) {
		findTaskByIdExecutions++;
		if (findTaskByIdMocked) {
			return;
		}
		inv.proceed();
	}

	public void setUpMockFindTaskById() {
		findTaskByIdMocked = true;
		findTaskByIdExecutions = 0;
	}

	public int getFindTaskByIdExecutions() {
		return findTaskByIdExecutions;
	}

	public boolean isFindTaskByIdExecuted() {
		return findTaskByIdExecutions > 0;
	}

	public void resetMockFindTaskById() {
		findTaskByIdMocked = false;
		findTaskByIdExecutions = 0;
	}

	@MethodRef(name = "getAgentWithMostRecentTask", signature = "()QTask;")
	@Mock
	Task getAgentWithMostRecentTask(Invocation inv) {
		getAgentWithMostRecentTaskExecutions++;
		if (getAgentWithMostRecentTaskMocked) {
			return getAgentWithMostRecentTaskReturnValue;
		}
		return inv.proceed();
	}

	public void setUpMockGetAgentWithMostRecentTask(Task returnValue) {
		getAgentWithMostRecentTaskReturnValue = returnValue;
		getAgentWithMostRecentTaskMocked = true;
		getAgentWithMostRecentTaskExecutions = 0;
	}

	public int getGetAgentWithMostRecentTaskExecutions() {
		return getAgentWithMostRecentTaskExecutions;
	}

	public boolean isGetAgentWithMostRecentTaskExecuted() {
		return getAgentWithMostRecentTaskExecutions > 0;
	}

	public void resetMockGetAgentWithMostRecentTask() {
		getAgentWithMostRecentTaskMocked = false;
		getAgentWithMostRecentTaskExecutions = 0;
	}

	@MethodRef(name = "getAllTasksInProgress", signature = "()QList<QJsonObject;>;")
	@Mock
	List<JsonObject> getAllTasksInProgress(Invocation inv) {
		getAllTasksInProgressExecutions++;
		if (getAllTasksInProgressMocked) {
			return getAllTasksInProgressReturnValue;
		}
		return inv.proceed();
	}

	public void setUpMockGetAllTasksInProgress(List<JsonObject> returnValue) {
		getAllTasksInProgressReturnValue = returnValue;
		getAllTasksInProgressMocked = true;
		getAllTasksInProgressExecutions = 0;
	}

	public int getGetAllTasksInProgressExecutions() {
		return getAllTasksInProgressExecutions;
	}

	public boolean isGetAllTasksInProgressExecuted() {
		return getAllTasksInProgressExecutions > 0;
	}

	public void resetMockGetAllTasksInProgress() {
		getAllTasksInProgressMocked = false;
		getAllTasksInProgressExecutions = 0;
	}

	@MethodRef(name = "createTask", signature = "(QTask;QHandler<QAsyncResult<QString;>;>;)V")
	@Mock
	void createTask(Invocation inv, Task requestedTask, Handler<AsyncResult<String>> addTaskHandler) {
		createTaskExecutions++;
		if (createTaskMocked) {
			return;
		}
		inv.proceed();
	}

	public void setUpMockCreateTask() {
		createTaskMocked = true;
		createTaskExecutions = 0;
	}

	public int getCreateTaskExecutions() {
		return createTaskExecutions;
	}

	public boolean isCreateTaskExecuted() {
		return createTaskExecutions > 0;
	}

	public void resetMockCreateTask() {
		createTaskMocked = false;
		createTaskExecutions = 0;
	}

	@MethodRef(name = "updateTask", signature = "(QString;QHandler<QAsyncResult<QJsonObject;>;>;)V")
	@Mock
	void updateTask(Invocation inv, String taskId, Handler<AsyncResult<JsonObject>> taskHandler) {
		updateTaskExecutions++;
		if (updateTaskMocked) {
			return;
		}
		inv.proceed();
	}

	public void setUpMockUpdateTask() {
		updateTaskMocked = true;
		updateTaskExecutions = 0;
	}

	public int getUpdateTaskExecutions() {
		return updateTaskExecutions;
	}

	public boolean isUpdateTaskExecuted() {
		return updateTaskExecutions > 0;
	}

	public void resetMockUpdateTask() {
		updateTaskMocked = false;
		updateTaskExecutions = 0;
	}

	public void resetAllMocks() {
		resetMockFindAgentBySkill();
		resetMockFindTaskById();
		resetMockGetAgentWithMostRecentTask();
		resetMockGetAllTasksInProgress();
		resetMockCreateTask();
		resetMockUpdateTask();
	}
}