package ua.kug.boot

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import twitter4j.util.function.Consumer
import ua.kug.tw.RetweetAction
import ua.kug.tw.TwitterWallWithAction
import java.util.concurrent.atomic.AtomicLong

@RestController
class TwitterController(val twitterService: TwitterService) {

    @GetMapping("/tw")
    fun tweets() =
            twitterService.tweets()

    @PostMapping("/subscribe")
    fun subscribe(@RequestParam hashTags: String): ResponseEntity<Any> {
        val tags = hashTags.split(" ").toList()
        return try {
            twitterService.subscribe(tags)
            ResponseEntity.accepted().build()
        } catch (ex: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

}