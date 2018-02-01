package ua.kug

import io.javalin.Javalin
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import ua.kug.tw.RetweetAction
import ua.kug.tw.TwitterWallWithAction

fun main(args: Array<String>) {
    val app = Javalin.create().port(7000).start()
    app.get("/") { ctx -> ctx.result("Hello World") }

    val twitterWall2 = TwitterWallWithAction(
            TwitterStreamFactory().instance,
            listOf("#KUG"),
            5,
            mutableListOf(
                    RetweetAction(TwitterFactory().instance, listOf("ugly", "no soup", "bad", "yegor256"))
            )
    )

    app.get("/tw") { ctx -> ctx.json(twitterWall2.tweets()) }
}
