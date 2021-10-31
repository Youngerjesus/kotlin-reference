# Generics: in, out, where

***

> Classes in Kotlin can have type parameters, just like in Java:
>
> ````kotlin
> class Box<T>(t: T) {
>    var value = t
> }
> ````
> 
> To create an instance of such a class, simply provide the type arguments:
>
> ````kotlin
> val box: Box<Int> = Box<Int>(1)
> ````
> 
> But if the parameters can be inferred, for example, from the constructor arguments, you can omit the type arguments:
>
> ````kotlin
> val box = Box(1) // 1 has type Int, so the compiler figures out that it is Box<Int>
> ````

- 코틀린도 자바처럼 클래스에서 타입 파라미터 선언을 할 수 있다.
- 인스턴스를 만들려면 명시적으로 어떠한 타입인지 말해줘야한다. 하지만 타입 추론이 가능하다면 이를 생략해도 괜찮다.

***

## Variance

> One of the trickiest aspects of Java's type system is the wildcard types (see Java Generics FAQ). Kotlin doesn't have these. Instead, Kotlin has declaration-site variance and type projections.
>
> Let's think about why Java needs these mysterious wildcards. The problem is explained well in Effective Java, 3rd Edition, Item 31: Use bounded wildcards to increase API flexibility.
> First, generic types in Java are invariant, meaning that List<String> is not a subtype of List<Object>. If List were not invariant, it would have been no better than Java's arrays, as the following code would have compiled but caused an exception at runtime:
> 
> ````java
> // Java
> List<String> strs = new ArrayList<String>();
> List<Object> objs = strs; // !!! A compile-time error here saves us from a runtime exception later.
> objs.add(1); // Put an Integer into a list of Strings
> String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String
> ````
> 
> Java prohibits such things in order to guarantee run-time safety. But this has implications. For example, consider the addAll() method from the Collection interface. What's the signature of this method? Intuitively, you'd write it this way:
>
> ````java
> interface Collection<E> ... {
>     void addAll(Collection<E> items);
> }
> ````
> 
> (In Java, you probably learned this the hard way, see Effective Java, 3rd Edition, Item 28: Prefer lists to arrays)
>
> That's why the actual signature of addAll() is the following:
> 
> ```java
> // Java
> interface Collection<E> ... {
>   void addAll(Collection<? extends E> items);
> }
> ```
> 
> The wildcard type argument ? extends E indicates that this method accepts a collection of objects of E or a subtype of E, not just E itself. This means that you can safely read E 's from items (elements of this collection are instances of a subclass of E), but cannot write to it as you don't know what objects comply with that unknown subtype of E. In return for this limitation, you get the desired behavior: Collection<String> is a subtype of Collection<? extends Object>. In other words, the wildcard with an extends -bound (upper bound) makes the type covariant.
>
> The key to understanding why this works is rather simple: if you can only take items from a collection, then using a collection of String s and reading Object s from it is fine. Conversely, if you can only put items into the collection, it's okay to take a collection of Object s and put String s into it: in Java there is List<? super String>, a supertype of List<Object>.
>
> The latter is called contravariance, and you can only call methods that take String as an argument on List<? super String> (for example, you can call add(String) or set(int, String)). If you call something that returns T in List<T>, you don't get a String, but rather an Object.
>
> Joshua Bloch gives the name Producers to objects you only read from and Consumers to those you only write to. He recommends:




- 자바의 타입 시스템에서 가장 다루기 힘든 것이 wildcard 이다. 코틀린에서는 이게 없고 declaration-site variance 와 type projection 이 있다.
  - Wildcard 는 3 종류가 있었다.
    - Unbound Wildcard (?)
    - Upper Bounded Wildcard (? extends Foo)
    - Lower Bounded Wildcard (? super Foo) 
- 자바는 왜 Wildcard 가 필요했을까? (이는 Effective Java Item 31 에 보면 나와있다.)
  - List<String> 은 List<Object> 의 하위 타입이 아니기 때문에. 그냥 서로 다른 타입이라서. 
  - 이 경우에는 유용하지 않다. 하위 타입이나 상한 타입을 받아도 되는 경우가 필요하기 때문에. 그래서 와일드카드가 등장했다.
  - 와일드카드를 사용할 때 법칙 `producer-extends, consumer-super`

***

## Declaration-site variance

> Let's suppose that there is a generic interface Source<T> that does not have any methods that take T as a parameter, only methods that return T:
>
> ````java
> // Java
> interface Source<T> {
>   T nextT();
> }
> ````
>
> Then, it would be perfectly safe to store a reference to an instance of Source<String> in a variable of type Source<Object>- there are no consumer-methods to call. But Java does not know this, and still prohibits it:
>
> ````java
> // Java
> void demo(Source<String> strs) {
> Source<Object> objects = strs; // !!! Not allowed in Java
> // ...
> }
> ````
> To fix this, you should declare objects of type Source<? extends Object>. Doing so is meaningless, because you can call all the same methods on such a variable as before, so there's no value added by the more complex type. But the compiler does not know that.
>
> Doing so is meaningless, because you can call all the same methods on such a variable as before, so there's no value added by the more complex type. But the compiler does not know that.
> 
> In Kotlin, there is a way to explain this sort of thing to the compiler. This is called declaration-site variance: you can annotate the type parameter T of Source to make sure that it is only returned (produced) from members of Source<T>, and never consumed. To do this, use the out modifier:
>
> ````kotlin
> interface Source<out T> {
>    fun nextT(): T
> }
>
> fun demo(strs: Source<String>) {
>   val objects: Source<Any> = strs // This is OK, since T is an out-parameter
>   // ...
> }
> ````
> 
> The general rule is this: when a type parameter T of a class C is declared out, it may occur only in the out -position in the members of C, but in return C<Base> can safely be a supertype of C<Derived>.
>
> In other words, you can say that the class C is covariant in the parameter T, or that T is a covariant type parameter. You can think of C as being a producer of T 's, and NOT a consumer of T 's.
> 
> The out modifier is called a variance annotation, and since it is provided at the type parameter declaration site, it provides declaration-site variance. This is in contrast with Java's use-site variance where wildcards in the type usages make the types covariant.
>
> In addition to out, Kotlin provides a complementary variance annotation: in. It makes a type parameter contravariant, meaning it can only be consumed and never produced. A good example of a contravariant type is Comparable:
>
> ````kotlin
> interface Comparable<in T> {
>     operator fun compareTo(other: T): Int
> }
> 
> fun demo(x: Comparable<Number>) {
>   x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
>   // Thus, you can assign x to a variable of type Comparable<Double>
>   val y: Comparable<Double> = x // OK!
> }
> ````
> The words in and out seem to be self-explanatory (as they’ve already been used successfully in C# for quite some time), and so the mnemonic mentioned above is not really needed. It can in fact be rephrased at a higher level of abstraction:

- 자바에서 Generic 은 일반적으로 Invariant 이다. 그래서 타입이 완전 똑같지 않다면 대입하는게 불가능하고 유연함을 제공하기 위해서는 Unbounded Wildcard 를 이용해야한다.
- 하지만 코틀린에서는 클래스나 인터페이스를 선언할 때 out, in 을 통해서 제네릭에 variant 를 제공해주는게 가능하다.



