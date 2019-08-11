/**
 * 
 */
package subhasys.wds.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import redis.embedded.RedisServer;

/**
 * @author subhasis
 *
 */
public class WdsApiSharedCache extends AbstractVerticle {
	
	private static final int REDIS_PORT = 6381;
	private RedisServer redisServer;

	@Override
	public void start(Future<Void> cacheServerFuture) {
		System.out.println("WdsApiSharedCache :: start() - Starting Embedded RedisServer at port " + REDIS_PORT);
		try {
			redisServer = new RedisServer();
			// redisServer.start();
			if (cacheServerFuture.succeeded()) {
				System.out.println("WdsApiSharedCache :: start() - Done Starting Embedded RedisServer at port " + REDIS_PORT);
			} else {
				System.out.println("WdsApiSharedCache :: start() - ERROR Starting Embedded RedisServer at port " + REDIS_PORT);
			}
			cacheServerFuture.complete();
		} catch (Exception redisIoExcp) {
			redisIoExcp.printStackTrace();
			cacheServerFuture.fail(redisIoExcp);
		}
		
	}

	@Override
	public void stop(Future<Void> cacheFutureRes) {
		if (redisServer != null) {
			redisServer.stop();
			redisServer = null;
		}
		cacheFutureRes.complete();
	}
	
	private static JsonObject getWdsCacheConfig() {
		JsonObject redisConfig = new JsonObject();
		redisConfig.put("host", "localhost");
		redisConfig.put("port", REDIS_PORT);
		return redisConfig;
	}

}
