package ua.kug.boot

import ua.kug.tw.TwitterWall

abstract class TwitterService(var twitterWall: TwitterWall) {

    fun tweets() = twitterWall.tweets()

    fun subscribe(tags: List<String>) {
        twitterWall = createTwitterWall(tags)
    }

    abstract fun createTwitterWall(tags: List<String>): TwitterWall

}