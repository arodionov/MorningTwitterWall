package ua.kug.tw

import kotlinx.coroutines.experimental.async
import twitter4j.Status
import twitter4j.TwitterStream
import twitter4j.util.function.Consumer
import ua.kug.buffer.BoundedBuffer

class TwitterWallWithAction(
        twitterStream: TwitterStream,
        hashTags: List<String>,
        override val size: Int = 10,
        private val actions: List<Consumer<Status>> = emptyList()) : TwitterWall {

    private val buffer = BoundedBuffer<String>(size)

    init {
        val filteredTags = hashTags.filter { it.isNotBlank() }
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

    internal fun action(status: Status) {
        async { actions.forEach { it.accept(status) } }
    }

    internal fun updateBuffer(msg: String) {
        buffer.put(msg)
    }

    override fun tweets() = buffer.values()
}