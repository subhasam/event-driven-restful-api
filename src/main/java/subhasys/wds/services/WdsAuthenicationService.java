/**
 * 
 */
package subhasys.wds.services;

import java.util.Objects;

import io.vertx.ext.web.RoutingContext;

/**
 * @author subhasis
 *
 */
public class WdsAuthenicationService {

	private static final String API_ACCESS_TOKEN = "access_token";

	public boolean isTrustedUser(RoutingContext context) {
		if (Objects.isNull(context.request().getHeader(API_ACCESS_TOKEN))) {
			System.out.println("WdsAuthenicationService: isTrustedUser() - Unauthorized Access. Please add valid Auth-Token 'access_token' in the Request Header");
			context.request().response().setStatusCode(401).setStatusMessage("Unauthorized Access. Please add valid Auth-Token 'access_token' in the Request Header");
			context.fail(401);
		}

		return true;

	}

}
