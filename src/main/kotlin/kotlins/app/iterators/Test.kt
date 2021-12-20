package kotlins.app.iterators

fun main() {
    val numbers = mutableListOf("one", "two", "three", "four")
    val mutableIterator = numbers.iterator()

    while (mutableIterator.hasNext()) {
        println(mutableIterator.next())
    }

    println("After removal: $numbers")
}