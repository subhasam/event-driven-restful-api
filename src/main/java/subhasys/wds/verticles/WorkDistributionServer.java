/**
 * 
 */
package subhasys.wds.verticles;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.mongo.MongoClient;
import subhasys.wds.dao.AgentDao;
import subhasys.wds.dao.TaskAssignmentDao;
import subhasys.wds.services.TaskAssignmentService;
import subhasys.wds.services.WdsAuthenicationService;

/**
 * @author subhasis
 *
 */
public class WorkDistributionServer extends AbstractVerticle {

	public static final String APPLICATION_JSON = "application/json";
	private static final String WDS_API_BASE_URI = "/workdistribution/v1";
	private static final int SERVER_PORT = 9090;
	private static final String SEVER_HOST = "localhost";
	
	private HttpServer wdsApiServer;
	private WdsAuthenicationService authService;
	private TaskAssignmentService wdsTaskAssignmentService;
	private MongoClient wdsMongoClient;

	@Override
	public void init(Vertx vertx, Context context) {
		super.init(vertx, context);
	}
	
	@Override
	public void start(Future<Void> futureResult) {
		JsonObject wdsMongoConfig = WorkDistributionDatabaseServer.getWdsMongoDbConfig();
		try {
			wdsMongoClient = MongoClient.createShared(vertx, wdsMongoConfig);
		} catch (Exception dbExcp) {
			futureResult.fail(dbExcp);
			return;
		}
		
		TaskAssignmentDao taskAssignmentDao = new TaskAssignmentDao(wdsMongoClient);
		AgentDao agentDao = new AgentDao(wdsMongoClient);
		wdsTaskAssignmentService = new TaskAssignmentService(taskAssignmentDao, agentDao);
		authService = new WdsAuthenicationService();
		wdsApiServer = vertx.createHttpServer(wdsServerOptions());
		wdsApiServer.requestHandler();
		wdsApiServer.requestHandler(workDistributionApiRouter());
		wdsApiServer.listen(serverStartUpResult -> {
			if (serverStartUpResult.succeeded()) {
				System.out.println("Congratulations !! Work Distribution API is ready to process your request at "
						+ String.format("%s%s%s%s", "http://", SEVER_HOST, ":", SERVER_PORT));
				futureResult.complete();
			} else {
				futureResult.fail(serverStartUpResult.cause());
			}
		});

	}
	
	private Router workDistributionApiRouter() {
		Router wdsApiRouter = Router.router(vertx);
		
		wdsApiRouter.route().consumes(APPLICATION_JSON).produces(APPLICATION_JSON)
				.handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET).allowedMethod(HttpMethod.POST)
						.allowedMethod(HttpMethod.PATCH)
						.allowedHeader("access_token").allowedHeader("Content-Type"));
		wdsApiRouter.route().handler(BodyHandler.create());
		wdsApiRouter.route().handler(context -> {
			context.response().headers().add(CONTENT_TYPE, APPLICATION_JSON);
			context.next();
		});
		wdsApiRouter.route().failureHandler(ErrorHandler.create());

		wdsApiRouter.get(String.format("%s%s", WDS_API_BASE_URI, "/health-check")).handler(this::checkApiHealth);
		wdsApiRouter.get(String.format("%s%s", WDS_API_BASE_URI, "/skills")).handler(this::getAvailableSkills);
		wdsApiRouter.post(String.format("%s%s", WDS_API_BASE_URI, "/tasks")).handler(this::createTask);
		wdsApiRouter.patch(String.format("%s%s", WDS_API_BASE_URI, "/tasks/:taskId")).handler(this::closeTask);

		return wdsApiRouter;

	}
	
	@Override
	public void stop(Future<Void> future) {
		if (wdsApiServer == null) {
			future.complete();
			return;
		}
		wdsApiServer.close(future.completer());
	}

	private void checkApiHealth(RoutingContext wdsApiContext) {
		wdsApiContext.request().response().setStatusCode(200).end("Health-Check : Work Distribution API is Up Running...");
	}

	private void createTask(RoutingContext wdsApiContext) {
		if (authService.isTrustedUser(wdsApiContext)) {
			if (wdsTaskAssignmentService.isValidatTaskRequest(wdsApiContext)) {
				wdsTaskAssignmentService.executeTaskAssignment(wdsApiContext);
			} else {
				handleRequestError(wdsApiContext);
				
			}
		}
	}

	private void closeTask(RoutingContext wdsApiContext) {
		if (authService.isTrustedUser(wdsApiContext)) {
			String taskId = wdsApiContext.pathParam("taskId");
			if (null != taskId && !taskId.isEmpty()) {
				wdsTaskAssignmentService.markTaskCompleted(wdsApiContext);
			} else {
				handleRequestError(wdsApiContext);
			}
		}

	}
	
	private void getAvailableSkills(RoutingContext wdsApiContext) {
		if (authService.isTrustedUser(wdsApiContext)) {
			wdsTaskAssignmentService.getAvailableSkillSet(wdsApiContext);
		}
	}

	private void handleRequestError(RoutingContext wdsApiContext) {
		wdsApiContext.request().response().setStatusCode(400).end("Required Task Details are not present in the request");
	}

	private HttpServerOptions wdsServerOptions() {
		HttpServerOptions httpServerOption = new HttpServerOptions();
		httpServerOption.setHost(SEVER_HOST);
		httpServerOption.setPort(SERVER_PORT);
		return httpServerOption;
	}
	

}
