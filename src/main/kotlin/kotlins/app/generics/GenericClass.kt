package kotlins.app.generics

class GenericClass<in T>  {
}

class GrandChild : Child() {

}

open class Child : Parent() {

}

open class Parent {

}
interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}

fun main() {
    val a = GenericClass<Parent>()
    val b: GenericClass<Child> = a
}