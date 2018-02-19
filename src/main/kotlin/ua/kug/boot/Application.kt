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
import ua.kug.tw.TwitterWallWithAction

@SpringBootApplication
class Application {

    @Bean
    fun twitterService(tw: TwitterWallWithAction): TwitterService {
        return TODO()
    }

    @Bean
    @Scope("prototype")
    fun twitterWall(@Value("#Java, #Kotlin") tags: List<String>, actions: List<Consumer<Status>>) =
            TwitterWallWithAction(
                    twitterStream = TwitterStreamFactory().instance,
                    hashatgs = tags,
                    size = 5,
                    actions = actions
            )

    //@Bean
    fun retweetAction() =
            RetweetAction(
                    twitter = TwitterFactory().instance,
                    stopWords = listOf("ugly", "no soup", "bad", "yegor256")
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