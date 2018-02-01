package ua.kug.tw

import twitter4j.TwitterStream
import ua.kug.buffer.BoundedBuffer
import ua.kug.buffer.Buffer

class TwitterWall(twitterStream: TwitterStream, hashatgs: List<String>, val size: Int = 10) {

    private val buffer = BoundedBuffer<String>(size)

    init {
        val filterdTags = hashatgs.filter { it.isNotBlank() }
        if(filterdTags.isEmpty()) throw IllegalArgumentException("Is empty or blank")
        twitterStream
                .onStatus{updateBuffer(it.text)}
                .filter(*filterdTags.toTypedArray())
    }

    internal fun updateBuffer(msg: String) {
        buffer.put(msg)
    }
    fun tweets() = buffer.values()

}