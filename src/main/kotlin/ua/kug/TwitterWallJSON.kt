package ua.kug

import io.javalin.Javalin
import twitter4j.Status
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import twitter4j.util.function.Consumer
import ua.kug.tw.RetweetAction
import ua.kug.tw.TwitterWallWithAction

fun main(args: Array<String>) {
    val app = Javalin.create().port(7000).start()
    app.get("/") { ctx -> ctx.result("Hello World") }

    val twitterWall = TwitterWallWithAction(
            twitterStream = TwitterStreamFactory().instance,
            hashatgs = listOf("#KUG", "#MorningAtLohika", "#Kotlin"),
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

    app.get("/tw") { it.json(twitterWall.tweets()) }
}
