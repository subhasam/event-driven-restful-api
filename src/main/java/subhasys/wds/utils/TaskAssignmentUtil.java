/**
 * 
 */
package subhasys.wds.utils;


import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import subhasys.wds.domain.Task;

/**
 * @author subhasis
 *
 */
public class TaskAssignmentUtil {

	private TaskAssignmentUtil() {
		// private constructor
	}

	public static Task getDomainObjectFromRequest(RoutingContext wdsApiContext) {
		Task requestedTask = null;
		try {
			requestedTask = Json.decodeValue(wdsApiContext.getBodyAsString("UTF-8"), Task.class);
		} catch (DecodeException decodExcp) {
			wdsApiContext.fail(500, decodExcp);
		}
		
		return requestedTask;

	}

}
