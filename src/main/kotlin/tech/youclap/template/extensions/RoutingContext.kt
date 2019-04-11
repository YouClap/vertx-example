package tech.youclap.template.extensions

import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

fun RoutingContext.endAsJson(obj: Any) {
    response()
        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
        .end(Json.encodePrettily(obj))
}
