package tech.youclap.example

import io.reactivex.Completable
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.eventbus.MessageConsumer
import tech.youclap.example.model.Car

class DataVerticleTest : AbstractVerticle() {
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(DataVerticleTest::class.java)
    }

    override fun rxStart(): Completable {
        return Completable.mergeArray(
            messageConsumer<Car, String>("dao.car.create").rxCompletionHandler(),
            messageConsumer<String, Car>("dao.car.retrieve").rxCompletionHandler()
        )
    }


    /**
     * generic message consumer to forward messages to another consumer
     * within the test class
     * the new address in test class will be the same with a "test." prefix
     */
    private fun <E, O> messageConsumer(address: String): MessageConsumer<E> {
        val eventBus = vertx.eventBus()
        val consumerUserCreate = eventBus
            .consumer<E>(address)

        consumerUserCreate.handler { message ->
            eventBus.rxSend<O>("test.$address", message.body())
                .subscribe({ fw ->
                    message.reply(fw.body())
                }, { error ->
                    LOGGER.error("Error on message:$address", error)
                })
        }
        return consumerUserCreate
    }
}
