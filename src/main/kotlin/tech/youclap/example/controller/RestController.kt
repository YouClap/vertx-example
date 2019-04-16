package tech.youclap.example.controller

import io.vertx.reactivex.ext.web.Router

interface RestController : Controller {

    fun setupRoutes(router: Router)
}
