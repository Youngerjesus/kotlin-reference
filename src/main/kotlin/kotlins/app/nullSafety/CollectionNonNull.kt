package kotlins.app.nullSafety

class CollectionNonNull {
}

fun main () {
    val nullableList: List<Int?> = listOf(1, 2, null, 4)
    val intList: List<Int> = nullableList.filterNotNull()
    println(intList)
}