package tech.youclap.template.extensions

import io.vertx.ext.web.Router
import tech.youclap.template.Controller

fun Router.addController(controller: Controller) {
    controller.setupRoutes(this)
}

operator fun Router.plusAssign(controller: Controller) {
    this.addController(controller)
}
