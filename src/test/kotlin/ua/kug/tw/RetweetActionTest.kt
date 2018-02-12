package ua.kug.tw

import com.nhaarman.mockito_kotlin.*
import io.kotlintest.specs.FunSpec
import twitter4j.Status
import twitter4j.Twitter


class RetweetActionTest : FunSpec() {

    init {
        val twitter: Twitter = mock()

        test("do not retweet reetweets") {
            val status: Status = mock {
                on { isRetweet } doReturn true
            }

            val action = RetweetAction(twitter)
            action.accept(status)

            verify(twitter, never()).retweetStatus(any())
        }

        test("do retweet original status") {
            val status: Status = mock {
                on { isRetweet } doReturn false
            }

            val action = RetweetAction(twitter)
            action.accept(status)

            verify(twitter).retweetStatus(any())
        }

        test("do retweet original status with tweet id") {
            val tweetId = 42L
            val status: Status = mock {
                on { isRetweet } doReturn false
                on { id } doReturn tweetId
            }

            val action = RetweetAction(twitter)
            action.accept(status)

            verify(twitter).retweetStatus(tweetId)
        }

        test("do not retweet status already retweeted by me") {
            val tweetId = 42L
            val status: Status = mock {
                on { isRetweet } doReturn false
                on { id } doReturn tweetId
                on { isRetweetedByMe } doReturn true
            }

            val action = RetweetAction(twitter)
            action.accept(status)

            verify(twitter, never()).retweetStatus(tweetId)
        }

        test("do not retweet if tweet contains stop words")
        {
            val stopWords = listOf("bad", "ugly")
            val status: Status = mock {
                on { isRetweet } doReturn false
                on { isRetweetedByMe } doReturn false
                on { text } doReturn "very ugly talk"
            }

            val action = RetweetAction(twitter, stopWords)
            action.accept(status)

            verify(twitter, never()).retweetStatus(any())
        }
    }
}