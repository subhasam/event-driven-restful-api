/**
 * 
 */
package subhasys.wds.verticles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @author subhasis
 *
 */
public class WorkDistributionVerticle extends AbstractVerticle {

	private List<String> deploymentIds;

	@Override
	public void init(Vertx vertx, Context context) {
		super.init(vertx, context);
		deploymentIds = new ArrayList<>();
	}

	@Override
	public void start(Future<Void> futureResult) {
		Future<String> future = Future.future();
		DeploymentOptions webserverOptions = new DeploymentOptions();
		vertx.deployVerticle(WdsApiSharedCache.class.getName(), webserverOptions, future);
	}

	private Future<String> deployEmbeddedCache() {
		Future<String> future = Future.future();
		DeploymentOptions options = new DeploymentOptions();
		options.setWorker(true);
		vertx.deployVerticle(WdsApiSharedCache.class.getName(), options, future);
		return future;
	}

	@Override
	public void stop(Future<Void> future) {
		CompositeFuture.all(deploymentIds.stream().map(this::undeploy).collect(Collectors.toList()))
				.setHandler(future.<CompositeFuture>map(c -> null).completer());

	}

	private Future<Void> undeploy(String deploymentId) {
		Future<Void> future = Future.future();
		vertx.undeploy(deploymentId, res -> {
			if (res.succeeded()) {
				future.complete();
			} else {
				future.fail(res.cause());
			}
		});
		return future;
	}

	public static void main(String... wdsApiArgs) {
		final VertxOptions vertOptions = new VertxOptions();
		vertOptions.setWarningExceptionTime(3000000000L);
		Vertx.vertx(vertOptions).deployVerticle(WorkDistributionServer.class.getName());
	}

}
