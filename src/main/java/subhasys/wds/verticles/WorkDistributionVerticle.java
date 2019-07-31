/**
 * 
 */
package subhasys.wds.verticles;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

/**
 * @author Subhasis
 *
 */
public class WorkDistributionVerticle extends Verticle {
	public void start() {
		RouteMatcher wdsApiRouteMatcher = new RouteMatcher();
		wdsApiRouteMatcher.get("/workdistribution/v1/health-check", new Handler<HttpServerRequest>() {

			public void handle(final HttpServerRequest request) {
				// sleep for 500 milli-secs
				vertx.setTimer(500, new Handler<Long>() {

					public void handle(Long timer) {
						request.response().end("Health-Check : Work Distribution API is Up Running...");
					}
					
				});
				
			}
			
		});
		
		wdsApiRouteMatcher.post("/workdistribution/v1/task", new Handler<HttpServerRequest>() {

			public void handle(final HttpServerRequest request) {
				// sleep for 1000 milli-secs
				vertx.setTimer(1000, new Handler<Long>() {

					public void handle(Long timer) {
						request.response().end("Congratulations..Your task has been created and Assigned to a skilled Agent.");
					}
					
				});
				
			}
			
		});
		
		wdsApiRouteMatcher.put("/workdistribution/v1/task/id", new Handler<HttpServerRequest>() {

			public void handle(final HttpServerRequest request) {
				// sleep for 500 milli-secs
				vertx.setTimer(500, new Handler<Long>() {

					public void handle(Long timer) {
						request.response().end("Congratulations..The Task# has been marked as Completed.");
					}
					
				});
				
			}
			
		});
		
		vertx.createHttpServer().requestHandler(wdsApiRouteMatcher).listen(8080);
	}

}
