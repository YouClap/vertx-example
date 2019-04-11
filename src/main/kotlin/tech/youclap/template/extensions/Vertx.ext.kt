package tech.youclap.template.extensions

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

fun Vertx.createRouter(): Router = Router.router(this)
