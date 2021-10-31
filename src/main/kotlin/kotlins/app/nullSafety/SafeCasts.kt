package kotlins.app.nullSafety

class SafeCasts {
}

fun main() {
    val safeCasts = SafeCasts()
    val int: Int? = safeCasts as? Int
    println(int)
}