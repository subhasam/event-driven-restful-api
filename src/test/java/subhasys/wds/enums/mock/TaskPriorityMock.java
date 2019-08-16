package subhasys.wds.enums.mock;

import javax.annotation.Generated;

import org.junit.platform.engine.support.hierarchical.Node.Invocation;

import subhasys.wds.enums.TaskPriority;

public class TaskPriorityMock {//extends MockUp<TaskPriority> {

	private boolean getPriorityMocked = false;
	private int getPriorityExecutions = 0;
	private int getPriorityReturnValue = 0;
	private boolean fromStringMocked = false;
	private int fromStringExecutions = 0;
	private TaskPriority fromStringReturnValue = null;

	public static TaskPriorityMock create() {
		return new TaskPriorityMock();
	}

	@MethodRef(name = "getPriority", signature = "()I")
	@Mock
	int getPriority(Invocation inv) {
		getPriorityExecutions++;
		if (getPriorityMocked) {
			return getPriorityReturnValue;
		}
		return inv.proceed();
	}

	public void setUpMockGetPriority(int returnValue) {
		getPriorityReturnValue = returnValue;
		getPriorityMocked = true;
		getPriorityExecutions = 0;
	}

	public int getGetPriorityExecutions() {
		return getPriorityExecutions;
	}

	public boolean isGetPriorityExecuted() {
		return getPriorityExecutions > 0;
	}

	public void resetMockGetPriority() {
		getPriorityMocked = false;
		getPriorityExecutions = 0;
	}

	@MethodRef(name = "fromString", signature = "(I)QTaskPriority;")
	@Mock
	TaskPriority fromString(Invocation inv, int priority) {
		fromStringExecutions++;
		if (fromStringMocked) {
			return fromStringReturnValue;
		}
		return inv.proceed();
	}

	public void setUpMockFromString(TaskPriority returnValue) {
		fromStringReturnValue = returnValue;
		fromStringMocked = true;
		fromStringExecutions = 0;
	}

	public int getFromStringExecutions() {
		return fromStringExecutions;
	}

	public boolean isFromStringExecuted() {
		return fromStringExecutions > 0;
	}

	public void resetMockFromString() {
		fromStringMocked = false;
		fromStringExecutions = 0;
	}

	public void resetAllMocks() {
		resetMockGetPriority();
		resetMockFromString();
	}
}