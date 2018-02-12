package ua.kug.tw

import com.nhaarman.mockito_kotlin.*
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.FunSpec
import twitter4j.Status
import twitter4j.TwitterStream
import twitter4j.util.function.Consumer

class TwitterWallWithActionTest : FunSpec() {

    init {

        val twitterStream: TwitterStream = mock {
            on { onStatus(any()) } doReturn it
        }

        test("create TW") {
            TwitterWallWithAction(twitterStream, listOf("#KUG"))
        }

        test("TW with hasgtags") {
            TwitterWallWithAction(twitterStream, listOf("#KUG"))
        }

        test("hashtags should not be empty") {
            shouldThrow<IllegalArgumentException> {
                TwitterWallWithAction(twitterStream, listOf())
            }.message shouldBe "Is empty or blank"
        }

        test("hashtags should not be blank") {
            shouldThrow<IllegalArgumentException> {
                TwitterWallWithAction(twitterStream, listOf("", " ", "  "))
            }.message shouldBe "Is empty or blank"
        }

        test("TW size should be 10 by default") {
            val twitterWall = TwitterWallWithAction(twitterStream, listOf("#KUG"))

            twitterWall.size shouldBe 10
        }

        test("TW size") {
            val twitterWall = TwitterWallWithAction(twitterStream, listOf("#KUG"), size = 3)

            twitterWall.size shouldBe 3
        }

        test("Passing TwitterSteream") {
            TwitterWallWithAction(twitterStream, listOf("#KUG"))
        }

        test("Call filter with hashtags on TwitterSteream") {
            val tags = listOf("#KUG", "#JUG")
            TwitterWallWithAction(twitterStream, tags)

            verify(twitterStream).filter("#KUG", "#JUG")
        }

        test("Call onStatus on TwitterSteream") {
            val tags = listOf("#KUG", "#JUG")
            TwitterWallWithAction(twitterStream, tags)

            verify(twitterStream).onStatus(any())
        }

        test("update buffer") {
            val tags = listOf("#KUG", "#JUG")
            val twitterWall = TwitterWallWithAction(twitterStream, tags, 2)
            with(twitterWall) {
                updateBuffer("a")
                updateBuffer("b")
                updateBuffer("c")
            }

            twitterWall.tweets() shouldBe listOf("c", "b")
        }


        test("action will be called") {
            val tags = listOf("#KUG", "#JUG")
            val mockedAction: Consumer<Status> = mock()
            val mockedStatus: Status = mock()
            val twitterWall = TwitterWallWithAction(twitterStream, tags, 3, listOf(mockedAction))

            twitterWall.action(mockedStatus)

            verify(mockedAction).accept(any())
        }.config(enabled = false)
    }
}