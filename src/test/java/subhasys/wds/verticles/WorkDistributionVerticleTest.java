/**
 * 
 */
package subhasys.wds.verticles;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author subhasis
 *
 */
@RunWith(VertxUnitRunner.class)
public class WorkDistributionVerticleTest {
	private Vertx vertx;

	@Before
	public void setUp(TestContext context) {
		vertx = Vertx.vertx();
		vertx.deployVerticle(WorkDistributionVerticle.class.getName(), context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}
	
	@Ignore("NotTiming Out for Server Start.")
	@Test
	  public void testWdsApiStartUp(TestContext context) {
	    final Async async = context.async();

	    vertx.createHttpClient().getNow(9090, "localhost", "/workdistribution/v1/health-check",
	     response -> {
	      response.handler(body -> {
	        context.assertTrue(body.toString().contains("Health-Check : Work Distribution API is Up Running..."));
	        async.complete();
	      });
	    });
	  }

}
