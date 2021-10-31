package kotlins.app.nullSafety

class NullChecking {

}

fun main () {
    var str: String? = getNull(3)
    var str2: String? = null
//    val length = str2!!.length // 컴파일 에러가 발생하지 않음.
    val a = str
    if (a != null) {
        println(a.length)
    }

    val b: String = "Kotlin"
    print("String of length ${b.length}")
}

fun getNull(n: Int): String? {
    if (n % 2 == 1) return null
    return "abc"
}