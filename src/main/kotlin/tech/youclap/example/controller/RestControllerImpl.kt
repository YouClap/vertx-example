package tech.youclap.example.controller

import io.reactivex.rxkotlin.subscribeBy
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import tech.youclap.example.model.ExampleResult
import tech.youclap.example.service.CarService
import tech.youclap.example.shared.extensions.endAsJson

class RestControllerImpl(
    private val service: CarService
) : RestController {

    override fun setupRoutes(router: Router) {
//        router.post("/example/:id").handler(BodyHandler.create()).handler(::create)
        router.get("/example/:term").handler(::find)
    }

    private fun find(routingContext: RoutingContext) {

        val request = routingContext.request()

        val term = request.getParam("term")

        service.find(term)
            .subscribeBy(onSuccess = {

                val result = ExampleResult(it)

                routingContext.endAsJson(result)
            })
    }
}
