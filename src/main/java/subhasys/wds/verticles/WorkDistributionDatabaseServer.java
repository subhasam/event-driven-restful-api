/**
 * 
 */
package subhasys.wds.verticles;

import java.util.Optional;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author subhasis
 *
 */
public class WorkDistributionDatabaseServer {

	private static final String DAFAULT_MONGODB_URI = "mongodb://localhost:27017";
	private static final String DAFAULT_MONGODB = "wds_db";
	
	// private static MongoClient mongoClient;
	
	public static void main(String wdsDb[]) {
		System.out.println("MongoDB Config " + WorkDistributionDatabaseServer.getWdsMongoDbConfig().encodePrettily());

	}

	public static JsonObject getWdsMongoDbConfig() {
		// JsonObject wdsMongoDbConfig = Optional.ofNullable(Vertx.currentContext().config()).orElse(new JsonObject());
		JsonObject wdsMongoDbConfig = new JsonObject();
		String wdsDatabaeUri = Optional.ofNullable(wdsMongoDbConfig.getString("mongo_uri"))
				.orElse(DAFAULT_MONGODB_URI);
		String wdsDatabase = Optional.ofNullable(wdsMongoDbConfig.getString("mongo_db"))
				.orElse(DAFAULT_MONGODB);
		JsonObject mongoDbConfig = new JsonObject().put("connection_string", wdsDatabaeUri)
				.put("db_name", wdsDatabase);
		/*try {
			mongoClient = MongoClient.createShared(vertx, mongoDbConfig);
		} catch (Exception ioExcp) {
			future.fail(ioExcp);
			return;
		}*/
		return mongoDbConfig;
	}
	
}
