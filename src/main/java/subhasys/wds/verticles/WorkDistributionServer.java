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
import subhasys.wds.exception.WdsApiException;
import subhasys.wds.services.TaskAssignmentService;
import subhasys.wds.services.WdsAuthenicationService;

/**
 * @author subhasis
 *
 */
public class WorkDistributionServer extends AbstractVerticle {

	public static final String APPLICATION_JSON = "application/json";
	private static final String WDS_API_BASE_URI = "/workdistribution/v1";
	
	private HttpServer wdsApiServer;
	private WdsAuthenicationService authService;
	private TaskAssignmentService wdsTaskAssignmentService;
	private static MongoClient wdsMongoClient;

	@Override
	public void init(Vertx vertx, Context context) {
		super.init(vertx, context);
	}
	
	@Override
	public void start(Future<Void> futureResult) {
		JsonObject wdsMongoConfig = WorkDistributionDatabaseServer.getWdsMongoDbConfig();
		System.out.println("WorkDistributionServer :: start() - Creating Mongo DB instance with configuration " + wdsMongoConfig.encodePrettily());
		try {
			wdsMongoClient = MongoClient.createShared(vertx, wdsMongoConfig);
		} catch (Exception dbExcp) {
			dbExcp.printStackTrace();
			futureResult.fail(dbExcp);
			return;
		}
		System.out.println("WorkDistributionServer :: start() - DONE Creating Mongo DB instance");
		
		TaskAssignmentDao taskAssignmentDao = new TaskAssignmentDao(wdsMongoClient);
		AgentDao agentDao = new AgentDao(wdsMongoClient);
		wdsTaskAssignmentService = new TaskAssignmentService(taskAssignmentDao, agentDao);
		authService = new WdsAuthenicationService();
		System.out.println("WorkDistributionServer :: start() - Creating HttpServer");
		wdsApiServer = vertx.createHttpServer(wdsServerOptions());
		System.out.println("WorkDistributionServer :: start() - DONE Creating HttpServer");
		wdsApiServer.requestHandler();
		wdsApiServer.requestHandler(workDistributionApiRouter());
		wdsApiServer.listen(serverStartUpResult -> {
			if (serverStartUpResult.succeeded()) {
				System.out.println("WorkDistributionServer :: workDistributionApiRouter() - Server Start Up SUCCESSFUL..");
				futureResult.complete();
			} else {
				System.out.println("WorkDistributionServer :: workDistributionApiRouter() - Server Start Up FAILED..");
				futureResult.fail(serverStartUpResult.cause());
			}
		});

	}
	
	private Router workDistributionApiRouter() {
		System.out.println("WorkDistributionServer :: workDistributionApiRouter() - Creating WDS-API Routers");
		Router wdsApiRouter = Router.router(vertx);
		
		wdsApiRouter.route().consumes(APPLICATION_JSON).produces(APPLICATION_JSON)
				.handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET).allowedMethod(HttpMethod.POST)
						.allowedHeader("access_token").allowedHeader("Content-Type"));
		wdsApiRouter.route().handler(BodyHandler.create());
		wdsApiRouter.route().handler(context -> {
			context.response().headers().add(CONTENT_TYPE, APPLICATION_JSON);
			context.next();
		});
		wdsApiRouter.route().failureHandler(ErrorHandler.create());

		wdsApiRouter.get(String.format("%s%s", WDS_API_BASE_URI, "/health-check")).handler(this::checkApiHealth);
		wdsApiRouter.post(String.format("%s%s", WDS_API_BASE_URI, "/tasks")).handler(this::createTask);
		wdsApiRouter.post(String.format("%s%s", WDS_API_BASE_URI, "/tasks/:taskId")).handler(this::closeTask);

		System.out.println("WorkDistributionServer :: workDistributionApiRouter() - DONE Creating WDS-API Routers");
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
		wdsApiContext.request().response().end("Health-Check : Work Distribution API is Up Running...");
		return;
	}

	private void createTask(RoutingContext wdsApiContext) {
		if (authService.isTrustedUser(wdsApiContext)) {
			if (wdsTaskAssignmentService.isValidateTaskRequest(wdsApiContext)) {
				System.out.println("createTask :: Calling TaskAssignmentService-assignTaskToAgent() for Task Assignment.");
				wdsTaskAssignmentService.assignTaskToAgent(wdsApiContext);
				System.out.println("createTask :: DONE Calling TaskAssignmentService-assignTaskToAgent(), Task Assigned.");
				wdsApiContext.request().response().setStatusCode(201).end(wdsApiContext.getBodyAsString("UTF-8"));
			} else {
				System.out.println("Either Task/ TaskId/ Task-SkillSet are null");
				handleRequestError(wdsApiContext);
			}
		}
		/*
		if (authService.isTrustedUser(wdsApiContext)) {
			System.out.println("Unauthorized Acccess. Please send valid Auth-Token in request Header access_token");
			wdsApiContext.fail(401);
			wdsApiContext.fail(401, new WdsApiException("401",
					"Unauthorized Access. Please send valid Auth-Token in request Header access_token", null));
			wdsApiContext.request().response().end("Unauthorized Access. Please send valid Auth-Token in request Header access_token");
			return;
		}
		*/

	}

	private void closeTask(RoutingContext wdsApiContext) {
		if (authService.isTrustedUser(wdsApiContext)) {
			/*
			 * wdsApiContext.fail(401, new WdsApiException(
			 * "Unauthorized Access. Please send valid Auth-Token in request Header access_token"
			 * , null)); return;
			 */

			String taskId = wdsApiContext.pathParam("taskId");
			System.out.println("closeTask() - Task ID to Close taskId[" + taskId + "], Req Param =>"
					+ wdsApiContext.request().getParam("taskId"));
			if (null != taskId) {
				System.out.println("closeTask() - Calling TaskService's markTaskCompleted()");
				wdsTaskAssignmentService.markTaskCompleted(wdsApiContext);
				// wdsApiContext.response().end(new JsonObject().put("result",
				// wdsApiContext.get("task").toString()).encodePrettily());
				System.out.println("closeTask :: Closed Task ID =>" +taskId);
			} else {
				handleRequestError(wdsApiContext);
			}
		}

	}

	private void handleRequestError(RoutingContext wdsApiContext) {
		//wdsApiContext.fail(statusCode);
		//wdsApiContext.fail(405, new WdsApiException("405", "Required Task Details are not present", null));
		wdsApiContext.response().setStatusCode(405).end("Required Task Details are not present");
		return;
	}

	private HttpServerOptions wdsServerOptions() {
		HttpServerOptions httpServerOption = new HttpServerOptions();
		httpServerOption.setHost("localhost");//\"localhost\"
		httpServerOption.setPort(9090);
		return httpServerOption;
	}
	

}
