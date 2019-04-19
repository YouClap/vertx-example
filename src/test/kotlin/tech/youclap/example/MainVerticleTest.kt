package tech.youclap.example

import io.vertx.core.DeploymentOptions
import io.vertx.junit5.Timeout
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.core.buffer.Buffer
import io.vertx.reactivex.ext.web.client.HttpResponse
import io.vertx.reactivex.ext.web.client.WebClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import tech.youclap.example.model.Car
import tech.youclap.example.verticle.MainVerticle
import tech.youclap.example.verticle.RestVerticle
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
class MainVerticleTest {

    companion object {
        const val PORT = 8888
        const val HOST = "localhost"
    }

    @BeforeEach
    fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(
            MainVerticle(), DeploymentOptions().setConfig(
                jsonObjectOf(
                    "verticles" to jsonObjectOf(
                        "dao" to DataVerticleTest::class.java.canonicalName,
                        "rest" to RestVerticle::class.java.canonicalName
                    ),
                    "confs" to jsonObjectOf(
                        "rest" to jsonObjectOf(
                            "port" to 8080,
                            "host" to "localhost"
                        )
                    )
                )
            )
        ) { ar ->
            if (ar.succeeded()) {
                testContext.completeNow()
            } else {
                testContext.failNow(ar.cause())
            }
        }
    }

//    @Test
    @DisplayName("Should create a car")
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    @Throws(Throwable::class)
    fun createCar(vertx: Vertx, testContext: VertxTestContext) {

        val carExpected = Car(
            "Golf 5",
            4
        )

        val entry = jsonObjectOf(
            "name" to carExpected.name,
            "wheels" to carExpected.wheels
        )


        val client = WebClient.create(vertx)
        val eventBus = vertx.eventBus()
        val ebConsumer = eventBus
            .consumer<Car>("test.dao.car.create")
            .handler { message ->
                val body = message.body()
                Assertions.assertEquals(carExpected, body)
                message.reply(null) // ignored message
            }

        ebConsumer.rxCompletionHandler().subscribe {
            client.post(PORT, HOST, "/api/car")
                .rxSendJsonObject(entry)
                .subscribe { response: HttpResponse<Buffer> ->
                    testContext.verify {
                        Assertions.assertEquals(jsonObjectOf("result" to "ok"), response.bodyAsJsonObject())
                        testContext.completeNow()
                    }
                }
        }
    }

//    @Test
    @DisplayName("Should retrieve a car")
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    @Throws(Throwable::class)
    fun retrieveCar(vertx: Vertx, testContext: VertxTestContext) {

        val car = Car(
            "Golf 5",
            4
        )


        val client = WebClient.create(vertx)
        val eventBus = vertx.eventBus()
        val ebConsumer = eventBus
            .consumer<String>("test.dao.car.retrieve")
            .handler { message ->
                val body = message.body()
                Assertions.assertEquals("golf5", body)
                message.reply(car)
            }

        val outputExpected = jsonObjectOf(
            "name" to car.name,
            "wheels" to car.wheels
        )
        ebConsumer.rxCompletionHandler().subscribe {
            client.get(PORT, HOST, "/api/car/golf5")
                .rxSend()
                .subscribe { response: HttpResponse<Buffer> ->
                    testContext.verify {
                        Assertions.assertEquals(outputExpected, response.bodyAsJsonObject())
                        testContext.completeNow()
                    }
                }
        }
    }


}
