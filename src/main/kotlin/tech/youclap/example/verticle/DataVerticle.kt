package tech.youclap.example.verticle

import io.reactivex.rxkotlin.subscribeBy
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.eventbus.Message
import tech.youclap.example.store.CarStoreImpl

// TODO maybe change the store to the interface?
class DataVerticle(private val store: CarStoreImpl) : AbstractVerticle() {

    companion object{

        const val SEARCH_ADDRESS = "data.car.find" // TODO maybe move this to another file?

    }

    override fun start() {
        vertx.eventBus()
            .consumer<String>(SEARCH_ADDRESS, ::handleFind)
    }

    private fun handleFind(message: Message<String>) {

        val term = message.body()

        store.find(term)
            .subscribeBy(onSuccess = message::reply)
    }
}
