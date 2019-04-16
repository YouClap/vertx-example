package tech.youclap.example.shared.extensions

import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.Router

fun Vertx.createRouter(): Router = Router.router(this)
