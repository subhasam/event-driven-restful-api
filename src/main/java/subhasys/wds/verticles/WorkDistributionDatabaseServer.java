/**
 * 
 */
package subhasys.wds.verticles;

import java.util.Optional;

//import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author subhasis
 *
 */
public class WorkDistributionDatabaseServer {

	private static final String DAFAULT_MONGODB_URI = "mongodb://localhost:27017";
	private static final String DAFAULT_MONGODB = "wds_db";
	private static MongoClient wdsMongoClient;

	private WorkDistributionDatabaseServer() {
		// private constructor
	}

	private static class DataBaseConnectionHolder {
		public static final WorkDistributionDatabaseServer WDS_MONGO_DB_CONN = new WorkDistributionDatabaseServer();
	}

	public static JsonObject getWdsMongoDbConfig() {
		JsonObject wdsMongoDbConfig = new JsonObject();
		String wdsDatabaeUri = Optional.ofNullable(wdsMongoDbConfig.getString("mongo_uri")).orElse(DAFAULT_MONGODB_URI);
		String wdsDatabase = Optional.ofNullable(wdsMongoDbConfig.getString("mongo_db")).orElse(DAFAULT_MONGODB);
		return new JsonObject().put("connection_string", wdsDatabaeUri).put("db_name", wdsDatabase);

	}

	public static WorkDistributionDatabaseServer getInstance() {
		return DataBaseConnectionHolder.WDS_MONGO_DB_CONN;
	}
	
	private MongoClient getDataBaseConnection() {
		// wdsMongoClient = MongoClient.createNonShared(vetx, getWdsMongoDbConfig());
		return wdsMongoClient;
	}

}
