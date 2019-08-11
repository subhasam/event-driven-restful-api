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
		System.out.println("AgentDao - DONE Creating AgentDao");
	}

	public void updateAgentInfo(Agent agentInfo, Handler<AsyncResult<JsonObject>> agentUpdateHandler) {
		agentInfo.setAvailable(false);
		JsonObject agentUpdateQuery = new JsonObject();
		agentUpdateQuery.put(COLUMN_AGENT_ID, agentInfo.getAgentId());
		System.out.println("updateAgentInfo() : Agent Update Query =>"+ Json.encodePrettily(agentUpdateQuery) +
				"\n Agent with updated Info =>"+ Json.encodePrettily(agentInfo));
		JsonObject updatedAgenInfo = new JsonObject(Json.encodePrettily(agentInfo));
		mongoDbClient.findOneAndReplace(WDS_AGENT_TABLE, agentUpdateQuery, updatedAgenInfo, updateHandler -> {
			if (updateHandler.failed()) {
				agentUpdateHandler.handle(updateHandler);
				return;
			}
		System.out.println("updateAgentInfo() :: Updated Agent Info with recent task activity.");
		});

	}

	public void getAllAgents(Handler<AsyncResult<List<JsonObject>>> agentSearchHandler) {
		JsonObject availableAgentsQuery = new JsonObject();
		availableAgentsQuery.put(COLUMN_AVILABLE_AGENT, true);
		System.out.println("AgentDao :: getAllAgents() - Querying Table <" + WDS_AGENT_TABLE + "> with Query => "
				+ availableAgentsQuery.encodePrettily());
		mongoDbClient.find(WDS_AGENT_TABLE, availableAgentsQuery, agentSearchHandler);
		/*mongoDbClient.find(WDS_AGENT_TABLE, availableAgentsQuery, resultHandler -> {
			if (resultHandler.succeeded()) {
				System.out.println("getAllAgents() : Total Agents from MongoDB = " + resultHandler.result().size());
				List<Agent> availableAgentList = new ArrayList<>(resultHandler.result().size());
				resultHandler.result().forEach(agent -> 
					availableAgentList.add(Json.decodeValue(agent.encode(), Agent.class))
				);
				return;
			} else {
				System.out.println("getAllAgents() : No Agents found or Query Failed");
				resultHandler.otherwiseEmpty();
				agentSearchHandler.handle(resultHandler);
			}
		});*/
	}

	public void findAgentBySkill(String agentSkill, Handler<AsyncResult<JsonObject>> agentSearchHandler) {
		JsonObject agentSearchQuery = new JsonObject();
		agentSearchQuery.put(COLUMN_SKILL, agentSkill);
		agentSearchQuery.put(COLUMN_AVILABLE_AGENT, true);
		System.out.println("AgentDao :: findAgentBySkill() - Querying Table " + WDS_AGENT_TABLE + " with Query => "
				+ agentSearchQuery.encodePrettily());
		mongoDbClient.findOne(WDS_AGENT_TABLE, agentSearchQuery, null, searchHandler -> {
			if (searchHandler.failed()) {
				agentSearchHandler.handle(searchHandler);
				return;
			}
		});
	}

}
