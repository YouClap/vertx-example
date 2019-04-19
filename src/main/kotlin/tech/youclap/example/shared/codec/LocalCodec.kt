package tech.youclap.example.shared.codec

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec

class LocalCodec<T>(private var klass: Class<T>) : MessageCodec<T, T> {

    override fun decodeFromWire(pos: Int, buffer: Buffer?): T {
        throw IllegalStateException("only implemented for remote purposes")
    }

    override fun systemCodecID(): Byte {
        return (-1).toByte()
    }

    override fun encodeToWire(buffer: Buffer?, s: T) {
        throw IllegalStateException("only implemented for remote purposes")
    }

    override fun transform(s: T): T {
        return s
    }

    override fun name(): String {
        return klass.simpleName
    }
}
