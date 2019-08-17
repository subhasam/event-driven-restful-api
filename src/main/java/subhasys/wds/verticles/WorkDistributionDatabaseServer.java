/**
 * 
 */
package subhasys.wds.verticles;

import java.util.Optional;

import io.vertx.core.json.JsonObject;

/**
 * @author subhasis
 *
 */
public class WorkDistributionDatabaseServer {

	private static final String DAFAULT_MONGODB_URI = "mongodb://localhost:27017";
	private static final String DAFAULT_MONGODB = "wds_db";
	
	private WorkDistributionDatabaseServer() {
		// private constructor
	}
	public static JsonObject getWdsMongoDbConfig() {
		JsonObject wdsMongoDbConfig = new JsonObject();
		String wdsDatabaeUri = Optional.ofNullable(wdsMongoDbConfig.getString("mongo_uri"))
				.orElse(DAFAULT_MONGODB_URI);
		String wdsDatabase = Optional.ofNullable(wdsMongoDbConfig.getString("mongo_db"))
				.orElse(DAFAULT_MONGODB);
		return new JsonObject().put("connection_string", wdsDatabaeUri)
				.put("db_name", wdsDatabase);
		
	}
	
}
