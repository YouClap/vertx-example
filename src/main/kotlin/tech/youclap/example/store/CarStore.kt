package tech.youclap.example.store

import io.reactivex.Single
import tech.youclap.example.model.Car

interface CarStore {

    fun find(term: String): Single<List<Car>>
}
