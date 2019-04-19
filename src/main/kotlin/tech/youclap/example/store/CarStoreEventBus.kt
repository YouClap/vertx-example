package tech.youclap.example.store

import io.reactivex.Single
import io.vertx.reactivex.core.eventbus.EventBus
import tech.youclap.example.model.Car

class CarStoreEventBus(private val eventBus: EventBus) : CarStore {

    override fun find(term: String): Single<List<Car>> {
        return eventBus.rxSend<List<Car>>("data.car.find", term)
            .map {message ->
               message.body()
            }
    }
}
