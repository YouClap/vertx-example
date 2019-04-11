package tech.youclap.template

import io.vertx.ext.web.Router

/**
 * Controller interface
 */
interface Controller {

    fun setupRoutes(router: Router)
}
