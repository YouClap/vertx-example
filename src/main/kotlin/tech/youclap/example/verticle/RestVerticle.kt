package tech.youclap.example.verticle

import io.reactivex.Completable
import io.vertx.reactivex.config.ConfigRetriever
import io.vertx.reactivex.core.AbstractVerticle
import tech.youclap.example.controller.RestController
import tech.youclap.example.model.config.HttpConfig
import tech.youclap.example.shared.extensions.createRouter
import tech.youclap.example.shared.extensions.mapTo

// TODO maybe this should moved to an abstract class with a setupRoutes? 🤔
class RestVerticle(private val controller: RestController) : AbstractVerticle() {

    override fun rxStart(): Completable {

        val configRetriever = ConfigRetriever.create(vertx)

        return configRetriever.rxGetConfig()
            .flatMapCompletable { json ->

                val config = json.mapTo<HttpConfig>()

                val router = vertx.createRouter()

                controller.setupRoutes(router)

                vertx
                    .createHttpServer()
                    .requestHandler(router)
                    .rxListen(config.port, config.host)
                    .ignoreElement()
            }
    }
}
