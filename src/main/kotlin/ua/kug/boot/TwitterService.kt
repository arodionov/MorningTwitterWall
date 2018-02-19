package ua.kug.boot

import org.springframework.stereotype.Service
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import twitter4j.util.function.Consumer
import ua.kug.tw.RetweetAction
import ua.kug.tw.TwitterWallWithAction
import javax.annotation.PostConstruct

abstract class TwitterService(var twitterWall: TwitterWallWithAction) {

    fun tweets() = twitterWall.tweets()

    fun subscribe(tags: List<String>) {
        twitterWall = createTwitterWall()
    }

    abstract fun createTwitterWall(): TwitterWallWithAction

}