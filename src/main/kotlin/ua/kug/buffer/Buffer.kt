package ua.kug.buffer

interface Buffer<T> {
    val size: Int
    fun values(): List<T>
    fun put(value: T)
}