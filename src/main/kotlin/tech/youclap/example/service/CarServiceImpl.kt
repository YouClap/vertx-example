package tech.youclap.example.service

import io.reactivex.Single
import tech.youclap.example.model.Car
import tech.youclap.example.store.CarStore

class CarServiceImpl(private val store: CarStore) : CarService {

    override fun find(term: String): Single<List<Car>> {
        return store.find(term)
    }
}
