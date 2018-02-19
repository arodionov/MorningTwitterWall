package ua.kug.tw

interface TwitterWall {
    val size: Int
    fun tweets(): List<String>
}