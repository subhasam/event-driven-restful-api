/**
 * 
 */
package subhasys.wds.dao;

import java.util.List;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import subhasys.wds.domain.Agent;

/**
 * @author subhasis
 *
 */
public class AgentDao {

	public static final String WDS_AGENT_TABLE = "agents";
	public static final String COLUMN_AGENT_ID = "agentId";
	public static final String COLUMN_AVILABLE_AGENT = "available";
	public static final String COLUMN_SKILL = "skill";

	private MongoClient mongoDbClient;

	public AgentDao(MongoClient mongoDbClient) {
		this.mongoDbClient = mongoDbClient;
	}

	public void updateAgentInfo(Agent agentInfo, Handler<AsyncResult<JsonObject>> agentUpdateHandler) {
		JsonObject agentUpdateQuery = new JsonObject();
		agentUpdateQuery.put(COLUMN_AGENT_ID, agentInfo.getAgentId());
		JsonObject updatedAgenInfo = new JsonObject(Json.encode(agentInfo));
		mongoDbClient.findOneAndReplace(WDS_AGENT_TABLE, agentUpdateQuery, updatedAgenInfo, agentUpdateHandler);

	}

	public void getAgentWithMatchingSkill(final String requiredSkill, Handler<AsyncResult<List<JsonObject>>> agentSearchHandler) {
		JsonObject availableAgentsQuery = new JsonObject();
		availableAgentsQuery.put(COLUMN_SKILL, requiredSkill);
		mongoDbClient.find(WDS_AGENT_TABLE, availableAgentsQuery, agentSearchHandler);
	}

	public void findAgentBySkill(String agentSkill, Handler<AsyncResult<JsonObject>> agentSearchHandler) {
		JsonObject agentSearchQuery = new JsonObject();
		agentSearchQuery.put(COLUMN_SKILL, agentSkill);
		agentSearchQuery.put(COLUMN_AVILABLE_AGENT, true);
		mongoDbClient.findOne(WDS_AGENT_TABLE, agentSearchQuery, null, searchHandler -> {
			if (searchHandler.failed()) {
				agentSearchHandler.handle(searchHandler);
				return;
			}
		});
	}

}
