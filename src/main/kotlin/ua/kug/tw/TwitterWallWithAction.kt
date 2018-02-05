package ua.kug.tw

import kotlinx.coroutines.experimental.async
import twitter4j.Status
import twitter4j.TwitterStream
import twitter4j.util.function.Consumer
import ua.kug.buffer.BoundedBuffer

class TwitterWallWithAction(
        twitterStream: TwitterStream,
        hashatgs: List<String>,
        val size: Int = 10,
        val actions: List<Consumer<Status>> = emptyList()) {

    private val buffer = BoundedBuffer<String>(size)

    init {
        val filteredTags = hashatgs.filter { it.isNotBlank() }
        if (filteredTags.isEmpty()) throw IllegalArgumentException("Is empty or blank")
        twitterStream
                .onStatus {
                    it.run {
                        action(it)
                        updateBuffer(it.text)
                    }
                }
                .filter(*filteredTags.toTypedArray())
    }

    fun action(status: Status) {
         async { actions.forEach { it.accept(status) } }
    }

    internal fun updateBuffer(msg: String) {
        buffer.put(msg)
    }

    fun tweets() = buffer.values()
}