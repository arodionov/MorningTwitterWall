package ua.kug.buffer

class BoundedBuffer<T>(override val size: Int = 10) : Buffer<T> {

    private val list = mutableListOf<T>()

    init {
        if (size < 1) throw IllegalArgumentException("Negative size")
        //val sign = if(size < 1) "negative" else "positive"
    }

    override fun values() = list.toList().reversed()

    override fun put(value: T) {
        if (list.size == size) list.removeAt(0)
        list.add(value)
    }

}