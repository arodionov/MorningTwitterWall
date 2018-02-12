package ua.kug.boot

import org.springframework.stereotype.Service
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import twitter4j.util.function.Consumer
import ua.kug.tw.RetweetAction
import ua.kug.tw.TwitterWallWithAction
import javax.annotation.PostConstruct

@Service
class TwitterService {

    private var twitterWall: TwitterWallWithAction? = null

    @PostConstruct
    fun init() {
        twitterWall = twitterWall(listOf("#KUG", "#MorningAtLohika", "#Kotlin"))
    }

    private fun twitterWall(tags: List<String>) =
            TwitterWallWithAction(
                    twitterStream = TwitterStreamFactory().instance,
                    hashatgs = tags,
                    size = 5,
                    actions = listOf(
                            RetweetAction(
                                    twitter = TwitterFactory().instance,
                                    stopWords = listOf("ugly", "no soup", "bad", "yegor256")
                            ),
                            Consumer {
                                println(it.text)
                                println(Thread.currentThread().name)
                            }
                    )
            )

    fun tweets() = twitterWall!!.tweets()

    fun subscribe(tags: List<String>) {
        twitterWall = twitterWall(tags)
    }

}