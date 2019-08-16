package subhasys.wds.dao.mock;

import java.util.List;

import javax.annotation.Generated;

import org.junit.platform.engine.support.hierarchical.Node.Invocation;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import subhasys.wds.dao.AgentDao;
import subhasys.wds.domain.Agent;

public class AgentDaoMock {//extends MockUp<AgentDao> {

	private boolean updateAgentInfoMocked = false;
	private int updateAgentInfoExecutions = 0;
	private boolean getAllAgentsMocked = false;
	private int getAllAgentsExecutions = 0;
	private boolean findAgentBySkillMocked = false;
	private int findAgentBySkillExecutions = 0;

	public static AgentDaoMock create() {
		return new AgentDaoMock();
	}

	@MethodRef(name = "updateAgentInfo", signature = "(QAgent;QHandler<QAsyncResult<QJsonObject;>;>;)V")
	@Mock
	void updateAgentInfo(Invocation inv, Agent agentInfo, Handler<AsyncResult<JsonObject>> agentUpdateHandler) {
		updateAgentInfoExecutions++;
		if (updateAgentInfoMocked) {
			return;
		}
		inv.proceed();
	}

	public void setUpMockUpdateAgentInfo() {
		updateAgentInfoMocked = true;
		updateAgentInfoExecutions = 0;
	}

	public int getUpdateAgentInfoExecutions() {
		return updateAgentInfoExecutions;
	}

	public boolean isUpdateAgentInfoExecuted() {
		return updateAgentInfoExecutions > 0;
	}

	public void resetMockUpdateAgentInfo() {
		updateAgentInfoMocked = false;
		updateAgentInfoExecutions = 0;
	}

	@MethodRef(name = "getAllAgents", signature = "(QHandler<QAsyncResult<QList<QJsonObject;>;>;>;)V")
	@Mock
	void getAllAgents(Invocation inv, Handler<AsyncResult<List<JsonObject>>> agentSearchHandler) {
		getAllAgentsExecutions++;
		if (getAllAgentsMocked) {
			return;
		}
		inv.proceed();
	}

	public void setUpMockGetAllAgents() {
		getAllAgentsMocked = true;
		getAllAgentsExecutions = 0;
	}

	public int getGetAllAgentsExecutions() {
		return getAllAgentsExecutions;
	}

	public boolean isGetAllAgentsExecuted() {
		return getAllAgentsExecutions > 0;
	}

	public void resetMockGetAllAgents() {
		getAllAgentsMocked = false;
		getAllAgentsExecutions = 0;
	}

	@MethodRef(name = "findAgentBySkill", signature = "(QString;QHandler<QAsyncResult<QJsonObject;>;>;)V")
	@Mock
	void findAgentBySkill(Invocation inv, String agentSkill, Handler<AsyncResult<JsonObject>> agentSearchHandler) {
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

	public void resetAllMocks() {
		resetMockUpdateAgentInfo();
		resetMockGetAllAgents();
		resetMockFindAgentBySkill();
	}
}