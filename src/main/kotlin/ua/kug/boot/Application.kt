package ua.kug.boot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import twitter4j.Status
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import twitter4j.util.function.Consumer
import ua.kug.tw.RetweetAction
import ua.kug.tw.TwitterWall
import ua.kug.tw.TwitterWallWithAction

@SpringBootApplication
class Application {

    @Bean
    fun twitterService(tw: TwitterWall, actions: List<Consumer<Status>>): TwitterService =
            object : TwitterService(tw) {
                override fun createTwitterWall(tags: List<String>): TwitterWall {
                    return twitterWall(tags, actions)
                }
            }

    @Bean
    @Scope("prototype")
    fun twitterWall(@Value("\${tw.hashTags}") tags: List<String>, actions: List<Consumer<Status>>) =
            TwitterWallWithAction(
                    twitterStream = TwitterStreamFactory().instance,
                    hashTags = tags,
                    actions = actions
            )

    @Bean
    fun retweetAction(@Value("\${tw.stopWords}") stopWords: List<String>) =
            RetweetAction(
                    twitter = TwitterFactory().instance,
                    stopWords = stopWords
            )

    @Bean
    fun printAction() =
            Consumer<Status> {
                println(it.text)
                println(Thread.currentThread().name)
            }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}