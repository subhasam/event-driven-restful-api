/**
 * 
 */
package subhasys.wds.verticles;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

/**
 * @author subhasis
 *
 */
public class ApiHealthMonitorVerticle extends Verticle {
	
	public void start() {
		RouteMatcher wdsApiHealthMonitor = new RouteMatcher();
		//TODO
		wdsApiHealthMonitor.get("/api/v1/health-check", new Handler<HttpServerRequest>() {

			public void handle(final HttpServerRequest request) {
				// sleep for 500 milli-secs
				vertx.setTimer(500, new Handler<Long>() {

					public void handle(Long timer) {
						request.response().end("Health-Check : Work Distribution API is Up Running...");
					}
					
				});
				
			}
			
		});
		
	}

}
