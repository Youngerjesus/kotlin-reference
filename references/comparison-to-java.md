# Comparison to Java

***

## Some Java issues addressed in Kotlin

Kotlin fixes a series of issues that Java suffers from:

- Null references are controlled by the type system.

- No raw types

- Arrays in Kotlin are invariant
  
- Kotlin has proper function types, as opposed to Java's SAM-conversions
  
- Use-site variance without wildcards
  
- Kotlin does not have checked exceptions

***

## No raw types

### Java generics in Kotlin

> Kotlin's generics are a little different from Java's (see Generics). When importing Java types to Kotlin, the following conversions are done:
>
> Java's wildcards are converted into type projections:
> 
> - Foo<? extends Bar> becomes Foo<out Bar!>!
> - Foo<? super Bar> becomes Foo<in Bar!>!
>
> Java's raw types are converted into star projections:
>
> - List becomes List<*>! that is List<out Any?>!
>
> Like Java's, Kotlin's generics are not retained at runtime: objects do not carry information about actual type arguments passed to their constructors. For example, ArrayList<Integer>() is indistinguishable from ArrayList<Character>(). This makes it impossible to perform is -checks that take generics into account. Kotlin only allows is -checks for star-projected generic types:
>
> ````kotlin
> if (a is List<Int>) // Error: cannot check if it is really a List of Ints
>  // but> 
> if (a is List<*>) // OK: no guarantees about the contents of the list
> ````

- 자바의 제네릭은 코틀린과 좀 다르다.
- 자바의 제네릭은 선언부에 공변성을 넣을 수 없다. 코틀린은 Declaration-site Variation 을 통해 가능하다.
  - 한번의 선언만으로 가능하니까 유용하다 라는 점.
  - 부모 타입의 제네릭에 하위 타입의 제네릭을 대입하는게 가능하다 라는 점. (유연성을 제공해줬다.)  
- 자바의 Unbounded Wildcard 역할을 해줄 수 있는게 Type Projection 이고
- 코틀린에서는 Star Projection 을 지원한다.
  - 어떤 타입이 올 지 결정할 수 없는 문제에 대해서 * 을 놓을 수 있다라는 점. (Any 랑은 좀 다른 형태)  

***

## Arrays

> Arrays in Kotlin are represented by the Array class. It has get and set functions that turn into [] by operator overloading conventions, and the size property, along with other useful member functions:
>
> ```kotlin
> class Array<T> private constructor() {
>      val size: Int
>      operator fun get(index: Int): T
>      operator fun set(index: Int, value: T): Unit
>  
>      operator fun iterator(): Iterator<T>
>      // ...
>  }
>  Copied!
> ```
> 
> To create an array, use the function arrayOf() and pass the item values to it, so that arrayOf(1, 2, 3) creates an array [1, 2, 3]. Alternatively, the arrayOfNulls() function can be used to create an array of a given size filled with null elements.
>
> Another option is to use the Array constructor that takes the array size and the function that returns values of array elements given its index:
> 
> ````kotlin
> // Creates an Array<String> with values ["0", "1", "4", "9", "16"]
> val asc = Array(5) { i -> (i * i).toString() }
> asc.forEach { println(it) }
> ````
> 
> As we said above, the [] operation stands for calls to member functions get() and set().
> 
> Arrays in Kotlin are invariant. This means that Kotlin does not let us assign an Array<String> to an Array<Any>, which prevents a possible runtime failure (but you can use Array<out Any>, see Type Projections).

- Kotlin 에서는 Array 도 클래스로 이뤄져있다는 것. 
- Array 를 만들 떄는 arrayOf() 이라는 메소드를 통해서, arrayOfNulls() 라는 메소드르 통해서 만들 수 있다. (널 값을 넣을 수 있는 여부의 차이)
- Array 에서 요소에 접근할 때 [] operator 를 통해서 가능한데 이는 내부적으로 get, set 메소드를 사용한다.
- 그리고 코틀린의 Array 는 Invariant 이다. (자바는 그렇지 않다. 이펙티브 자바 참고.)

***

## Function types

