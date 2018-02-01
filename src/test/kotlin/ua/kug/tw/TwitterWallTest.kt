package ua.kug.tw

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow

import io.kotlintest.specs.FunSpec
import twitter4j.TwitterStream

class TwitterWallTest : FunSpec() {

    init {

        val twitterStream: TwitterStream = mock{
            on {onStatus(any())} doReturn it
        }

        test("create TW"){
            TwitterWall(twitterStream, listOf("#KUG"))
        }

        test("TW with hasgtags") {
            TwitterWall(twitterStream, listOf("#KUG"))
        }

        test("hashtags should not be empty"){
            shouldThrow<IllegalArgumentException> {
                TwitterWall(twitterStream, listOf())
            }.message shouldBe "Is empty or blank"
        }

        test("hashtags should not be blank"){
            shouldThrow<IllegalArgumentException> {
                TwitterWall(twitterStream, listOf("", " ", "  "))
            }.message shouldBe "Is empty or blank"
        }

        test("TW size should be 10 by default"){
            val twitterWall = TwitterWall(twitterStream, listOf("#KUG"))
            twitterWall.size shouldBe 10
        }

        test("TW size"){
            val twitterWall = TwitterWall(twitterStream, listOf("#KUG"), size = 3)
            twitterWall.size shouldBe 3
        }

        test("Passing TwitterSteream"){
            TwitterWall(twitterStream, listOf("#KUG"))
        }

        test("Call filter with hashtags on TwitterSteream"){
            val tags = listOf("#KUG", "#JUG")
            TwitterWall(twitterStream, tags)
            verify(twitterStream).filter("#KUG", "#JUG")
        }

        test("Call onStatus on TwitterSteream"){
            val tags = listOf("#KUG", "#JUG")
            TwitterWall(twitterStream, tags)
            verify(twitterStream).onStatus(any())
        }

        test("Call onStatus should be with updateBuff on TwitterSteream"){
            val tags = listOf("#KUG", "#JUG")
            val twitterWall = TwitterWall(twitterStream, tags)
            //verify(twitterStream).onStatus{twitterWall.updateBuffer(any())}
        }

        test("update buffer") {

            val tags = listOf("#KUG", "#JUG")
            val twitterWall = TwitterWall(twitterStream, tags, 2)
            with(twitterWall) {
                updateBuffer("a")
                updateBuffer("b")
                updateBuffer("c")
            }

            twitterWall.tweets() shouldBe listOf("c", "b")

        }


    }

}