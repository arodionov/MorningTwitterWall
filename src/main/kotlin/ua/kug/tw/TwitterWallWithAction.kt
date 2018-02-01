package ua.kug.tw

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
        val filterdTags = hashatgs.filter { it.isNotBlank() }
        if(filterdTags.isEmpty()) throw IllegalArgumentException("Is empty or blank")
        twitterStream
                .onStatus{action(it)}
                .filter(*filterdTags.toTypedArray())
    }

    fun action(status: Status) {
        actions.forEach {it.apply { status }}
    }

    internal fun updateBuffer(msg: String) {
        buffer.put(msg)
    }
    fun tweets() = buffer.values()
}