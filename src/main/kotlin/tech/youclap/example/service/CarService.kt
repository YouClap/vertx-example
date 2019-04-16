package tech.youclap.example.service

import io.reactivex.Single
import tech.youclap.example.model.Car

interface CarService {

    fun find(term: String): Single<List<Car>>
}
