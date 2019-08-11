/**
 * 
 */
package subhasys.wds.services;

import java.util.Objects;

import io.vertx.ext.web.RoutingContext;
import subhasys.wds.exception.WdsApiException;

/**
 * @author subhasis
 *
 */
public class WdsAuthenicationService {

	private static final String API_ACCESS_TOKEN = "access_token";

	public boolean isTrustedUser(RoutingContext context) {
		System.out.println("WdsAuthenicationService : isTrustedUser() - RequestHeader [" + API_ACCESS_TOKEN +"] Value ==> [" + context.request().getHeader(API_ACCESS_TOKEN) +"]");
		if (Objects.isNull(context.request().getHeader(API_ACCESS_TOKEN))) {
			context.request().response().setStatusCode(401).setStatusMessage("Unauthorized Access. Please add valid Auth-Token 'access_token' in the Request Header ");
			context.fail(401);
		}

		return true;

	}

}
