package tech.youclap.template

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServer
import tech.youclap.template.extensions.addController
import tech.youclap.template.extensions.createRouter

private const val PORT = 8080 // TODO change this to config

class MainVerticle : AbstractVerticle() {

    private val server: HttpServer by lazy {
        vertx.createHttpServer()
    }

    override fun start(future: Future<Void>) {

        val router = vertx.createRouter()

        val controller = RestController()
        router.addController(controller)
        // router += controller
        // controller.setupRoutes(router)

        server.requestHandler(router)
        server.listen(PORT) { result ->
            if (result.succeeded()) {
                println("HTTP server started on port ${server.actualPort()}")
                future.complete()
            } else {
                future.fail(result.cause())
            }
        }
    }

    override fun stop(future: Future<Void>) {
        super.stop(future)
        server.close(future)
    }
}
