package tech.youclap.example.store

import io.reactivex.Single
import tech.youclap.example.model.Car

class CarStoreImpl : CarStore {

    private val list by lazy {
        (1..1000).map {
            Car(
                name = "Car $it",
                wheels = it
            )
        }
    }

    override fun find(term: String): Single<List<Car>> {

        return Single.just(list)
            .map { cars ->
                cars.filter { car ->
                    car.name.contains(term) || car.wheels.toString().contains(term)
                }
            }
    }
}
