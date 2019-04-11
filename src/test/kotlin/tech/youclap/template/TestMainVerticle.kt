package tech.youclap.template

import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class TestMainVerticle {

    @BeforeEach
    fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(MainVerticle(), testContext.succeeding<String> { _ -> testContext.completeNow() })
    }

//    @Test
//    @DisplayName("Should start a Web MainVerticle on port 8888")
//    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
//    @Throws(Throwable::class)
//    fun start_http_server(vertx: Vertx, testContext: VertxTestContext) {
//        println("lalalal")
//        vertx.createHttpClient() .getNow(8888, "localhost", "/") { response ->
//            println("coisas")
//            testContext.verify {
//                assertTrue(response.statusCode() == 200)
//                response.handler { body ->
//                    assertTrue(body.toString().contains("Hello from Vert.x!"))
//                    testContext.completeNow()
//                }
//            }
//        }
//    }
}
