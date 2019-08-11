/**
 * 
 */
package subhasys.wds.domain;

import java.util.List;
import java.util.Map;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author subhasis
 *
 */
public class ApiResultData {

	JsonObject apiResult;

	public ApiResultData(Object object) {

		apiResult = new JsonObject();

		if (object instanceof List || object instanceof Map) {
			apiResult.put("result", new JsonArray(Json.encode(object)));

		} else {
			apiResult.put("result", new JsonObject(Json.encode(object)));
		}

	}

	public JsonObject getResult() {
		return apiResult;
	}

	public void setResult(JsonObject result) {
		this.apiResult = result;
	}

}
