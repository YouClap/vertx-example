package tech.youclap.template

import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import tech.youclap.template.extensions.endAsJson
import tech.youclap.template.models.Brand
import tech.youclap.template.models.Car

class RestController : Controller {

    override fun setupRoutes(router: Router) {
        router.get("/example/:id").handler(this::handleGet)
    }

    private fun handleGet(routingContext: RoutingContext) {

        val request = routingContext.request()

        val id = request.getParam("id")

        val car = Car("Golf $id", 6, Brand.BMW)

        routingContext.endAsJson(car)
    }
}
