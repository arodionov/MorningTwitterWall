package ua.kug.buffer

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import org.junit.Assert.*
import org.junit.Test

class BoundedBufferTest{

    @Test
    fun createBuffer() {
        BoundedBuffer<Any>()
    }

    @Test
    fun createTypedBufferWith(){
        val boundedBuffer: BoundedBuffer<Any> = BoundedBuffer()
    }

    @Test
    fun sizeOfDefaultBuffer(){
        val boundedBuffer: BoundedBuffer<Any> = BoundedBuffer()
        //assertEquals(10, boundedBuffer.size)
        boundedBuffer.size shouldBe 10
    }

    @Test
    fun sizeOfBuffer(){
        val boundedBuffer: BoundedBuffer<Any> = BoundedBuffer(3)
        boundedBuffer.size shouldBe 3
    }

    @Test
    fun sizeShouldNotBeNegative(){
        shouldThrow<IllegalArgumentException> {
            BoundedBuffer<Any>(-2)
        }.message shouldBe "Negative size"
    }

    @Test
    fun valuesShouldBeEmpty(){
        val boundedBuffer: BoundedBuffer<Any> = BoundedBuffer()
        boundedBuffer.values() shouldBe emptyList<Any>()
        val values: List<Any> = boundedBuffer.values()
    }

    @Test
    fun putNull(){
        val boundedBuffer: BoundedBuffer<String> = BoundedBuffer()
        //boundedBuffer.put(null)
    }

    @Test
    fun putValue(){
        val boundedBuffer: BoundedBuffer<String> = BoundedBuffer()
        boundedBuffer.put("abc")

        boundedBuffer.values() shouldBe listOf("abc")
    }


    @Test
    fun putValues(){
        val boundedBuffer: BoundedBuffer<String> = BoundedBuffer()
        with(boundedBuffer) {
            put("a")
            put("b")
            put("c")
        }

        boundedBuffer.values() shouldBe listOf("c", "b", "a")
    }

    @Test
    fun putValuesBounded(){
        val boundedBuffer: BoundedBuffer<String> = BoundedBuffer(2)
        with(boundedBuffer) {
            put("a")
            put("b")
            put("c")
        }

        boundedBuffer.values() shouldBe listOf("c", "b")
    }
}