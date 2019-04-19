package tech.youclap.example.verticle

import io.reactivex.Completable
import io.vertx.kotlin.core.deploymentOptionsOf
import io.vertx.reactivex.config.ConfigRetriever
import io.vertx.reactivex.core.AbstractVerticle
import tech.youclap.example.controller.RestControllerImpl
import tech.youclap.example.model.Car
import tech.youclap.example.service.CarService
import tech.youclap.example.service.CarServiceImpl
import tech.youclap.example.shared.codec.LocalCodec
import tech.youclap.example.store.CarStoreEventBus
import tech.youclap.example.store.CarStoreImpl

class MainVerticle : AbstractVerticle() {

    private fun <T> registerLocalCodec(klass: Class<T>) {
        vertx.eventBus().delegate.registerDefaultCodec(klass, LocalCodec(klass))
    }

    private fun setupCodecs() {
        registerLocalCodec(Car::class.java)
        registerLocalCodec(ArrayList::class.java)
    }

    override fun rxStart(): Completable {

        setupCodecs()

        val configRetriever = ConfigRetriever.create(vertx)

        return configRetriever
            .rxGetConfig()
            .flatMapCompletable { jsonObject ->

                val httpJson = jsonObject.getJsonObject("http")
                val restConfig = deploymentOptionsOf(config = httpJson)

                val store = CarStoreImpl()

                val carStoreEventBus = CarStoreEventBus(vertx.eventBus())
                val service: CarService = CarServiceImpl(store)
//                val service: CarService = CarServiceImpl(carStoreEventBus)

                val restController = RestControllerImpl(service)

                val restVerticle = RestVerticle(restController)


                val storeVerticle = DataVerticle(store) // Not doing anything now


                val restDeploy = vertx.rxDeployVerticle(restVerticle, restConfig).ignoreElement()
                val storeDeploy = vertx.rxDeployVerticle(storeVerticle).ignoreElement()

                storeDeploy.andThen(restDeploy)
            }
    }
}
