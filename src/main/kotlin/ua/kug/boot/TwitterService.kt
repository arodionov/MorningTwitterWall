package ua.kug.boot

import ua.kug.tw.TwitterWall

abstract class TwitterService() {

    private var twitterWall = createTwitterWall()

    fun tweets() = twitterWall.tweets()

    fun subscribe(tags: List<String>) {
        twitterWall = createTwitterWall()
    }

    abstract fun createTwitterWall(): TwitterWall

}