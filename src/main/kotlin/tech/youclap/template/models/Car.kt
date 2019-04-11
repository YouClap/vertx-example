package tech.youclap.template.models

data class Car(val name: String, val wheels: Int, val brand: Brand)

enum class Brand {

    VW,
    Seat,
    BMW
}
