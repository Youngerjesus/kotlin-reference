# Null Safety

***

## Nullable types and non-null types

> Kotlin's type system is aimed at eliminating the danger of null references, also known as [The Billion Dollar Mistake.](https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions)
> 
> One of the most common pitfalls in many programming languages, including Java, is that accessing a member of a null reference will result in a null reference exception. In Java this would be the equivalent of a NullPointerException, or an NPE for short.
>
> The only possible causes of an NPE in Kotlin are:
> - An explicit call to throw NullPointerException().
> - Usage of the !! operator that is described below.
> - Data inconsistency with regard to initialization, such as when:
>   - An uninitialized this available in a constructor is passed and used somewhere (a "leaking this").
>   - A superclass constructor calls an open member whose implementation in the derived class uses an uninitialized state.
> - Java interoperation:
>   - Attempts to access a member of a null reference of a platform type;
>   - Nullability issues with generic types being used for Java interoperation. For example, a piece of Java code might add null into a Kotlin MutableList<String>, therefore requiring a MutableList<String?> for working with it.
>   - Other issues caused by external Java code.
> 
> In Kotlin, the type system distinguishes between references that can hold null (nullable references) and those that cannot (non-null references). For example, a regular variable of type String cannot hold null:
> 
> ```kotlin
> var a: String = "abc" // Regular initialization means non-null by default
> a = null // compilation error
> ```
> 
> To allow nulls, you can declare a variable as a nullable string by writing String?:
>
> ```kotlin
> var b: String? = "abc" // can be set to null
> b = null // ok
> print(b)
> ```
> 
> Now, if you call a method or access a property on a, it's guaranteed not to cause an NPE, so you can safely say:
>
> ````kotlin
> val l = a.length
> ````
> 
> But if you want to access the same property on b, that would not be safe, and the compiler reports an error:
>
> ````kotlin
> val l = b.length // error: variable 'b' can be null
> ````
> 
> But you still need to access that property, right? There are a few ways to do so.

- 코틀린 언어에서는 널 참조를 제거할려고 만들었다. 
  - 널을 만든 사람 자체이 자신이 만든 널 때문에 Billion 의 손실이 있다고 한다. 그 사람의 논리는 널 참조는 자동적으로 컴파일애서 checking 해줘야한다 라는 것.
- 코틀린에서는 Null 을 가질 수 있는 타입인지, 가질 수 없는 타입인지 명확하게 구별한다.
  - NULL 을 가질 수 없는 타입인 경우에는 애초에 널 체킹이 필요하지도 않다.   
  - 그라고 NUll 을 가질 수 있는 타입인 경우에는 강제로 컴파일 에러를 내준다. __(자바에서는 절대 해주지 않는 것)__


### Null Checking Example

````kotlin
fun main () {
    var str: String? = getNull(3)
    val a = str 
    println(a.length) // Null checking 을 하라는 compile error 발생
}

fun getNull(n: Int): String? {
    if (n % 2 == 1) return null
    return "abc"
}
````

***

## Checking for null in conditions

> First, you can explicitly check whether b is null, and handle the two options separately:
>
> ````kotlin
> val l = if (b != null) b.length else -1
> ````
> 
> The compiler tracks the information about the check you performed, and allows the call to length inside the if. More complex conditions are supported as well:
>
> ````kotlin
> val b: String? = "Kotlin"
> if (b != null && b.length > 0) {
>   print("String of length ${b.length}")
> } else {
>   print("Empty string")
> }
> ````
> 
> Note that this only works where b is immutable (meaning it is a local variable that is not modified between the check and its usage or it is a member val that has a backing field and is not overridable), because otherwise it could be the case that b changes to null after the check.

- 코틀린에서 Null 을 체킹하는 간단한 방법은 if 절을 이용해서 하는 것이다.
- 물론 이 방법도 immutable 한 변수인 경우에만 된다. (검사한 후에 null 로 변경될 여지가 있으니까.)

*** 

## Safe calls

> Your second option for accessing a property on a nullable variable is using the safe call operator ?.:
>
> ```kotlin
> val a = "Kotlin"
> val b: String? = null
> println(b?.length)
> println(a?.length) // Unnecessary safe call
> ```
> 
> This returns b.length if b is not null, and null otherwise. The type of this expression is Int?.
>
> Safe calls are useful in chains. For example, Bob is an employee who may be assigned to a department (or not). That department may in turn have another employee as a department head. To obtain the name of Bob's department head (if there is one), you write the following:
> 
> ```kotlin
> bob?.department?.head?.name
> ```
> 
> Such a chain returns null if any of the properties in it is null.
>
> To perform a certain operation only for non-null values, you can use the safe call operator together with let:
>
> ```kotlin
> val listWithNulls: List<String?> = listOf("Kotlin", null)
> for (item in listWithNulls) {
>   item?.let { println(it) } // prints Kotlin and ignores null
> }
> ```
> 
> A safe call can also be placed on the left side of an assignment. Then, if one of the receivers in the safe calls chain is null, the assignment is skipped and the expression on the right is not evaluated at all:
>
> ```kotlin
> // If either `person` or `person.department` is null, the function is not called:
> person?.department?.head = managersPool.getManager()
> ```

- 코틀린에서 두 번째로 Null Checking 을 하는 방법은 `?.` 를 사용하면 된다.
  - 예를 들어서 `b?.length` 같은 경우에 b 가 널이면 null 을 리턴하게 되고 널이 아니라면 b.length 를 리턴하게 해준다.
  - 이 방법은 객체에서 프로퍼티에 접근을 chain 식으로 할 때 유용하다. `bob?.department?.head?.name` 즉 bob, department, head 모두가 널이 아니면 name 을 리턴하도록 하는 것이다. (Null Checking 로직이 적어진다.)
  - 이와 비슷하게 반복문에서도 null 이 아니면 실행되는 코드 블록을 let 을 통해서 만들 수 있다.
  - `?.` 을 통해서 값을 가져오는게 아니라 대입을 할 때도 사용할 수 있다. `person?.department?.head = managersPool.getManager()` 라는 메소드에서 person 과 department 가 모두 널이 아닌 경우에 managersPool.getManager() 라는 메소드가 실행된다.

***

## Elvis operator

> When you have a nullable reference, b, you can say "if b is not null, use it, otherwise use some non-null value":
>
> ````kotlin
> val l: Int = if (b != null) b.length else -1
> ````
> 
> Instead of writing the complete if expression, you can also express this with the Elvis operator ?::
>
> ````kotlin
> val l = b?.length ?: -1
> ````
> 
> If the expression to the left of ?: is not null, the Elvis operator returns it, otherwise it returns the expression to the right. Note that the expression on the right-hand side is evaluated only if the left-hand side is null.
>
> Since throw and return are expressions in Kotlin, they can also be used on the right-hand side of the Elvis operator. This can be handy, for example, when checking function arguments:
>
> ````kotlin
> fun foo(node: Node): String? {
>     val parent = node.getParent() ?: return null
>     val name = node.getName() ?: throw IllegalArgumentException("name expected")
>     // ...
> }
> ````

- null 을 다룰 때 주로 사용하는 방식은 이거다. 널 이면 이렇게 하고 널이 아니면 이렇게 해라.
  - 이 부분을 Elvis Operator 를 사용하면 좀 더 간단하게 이용하는게 가능하다. Elvis Operator 는 '?:' 을 말한다. (엘비스라는 가수의 모양을 땃다고 한다.)
  - Elvis Operator 는 `?:` 의 왼쪽 값이 널이 아니면 그걸 사용하고 널이면 오른쪽 값을 사용한다라는 뜻.

***

## The !! operator

> The third option is for NPE-lovers: the not-null assertion operator (!!) converts any value to a non-null type and throws an exception if the value is null. You can write b!!, and this will return a non-null value of b (for example, a String in our example) or throw an NPE if b is null:

> ```kotlin
> val l = b!!.length
> ```
> 
> Thus, if you want an NPE, you can have it, but you have to ask for it explicitly and it won’t appear out of the blue.

- !! operator 를 이용하면 Not Null 임을 나타낼 수 있다. 
- Not Null 임을 명시했지만 null 인 경우에는 NPE 가 난다. 그러므로 NPE 를 만들고 싶다면 이걸 사용하면 된다.

***

## Safe casts

> Regular casts may result in a ClassCastException if the object is not of the target type. Another option is to use safe casts that return null if the attempt was not successful:
>
> ````kotlin
> val aInt: Int? = a as? Int
> ````

- 타입을 변환할려고 하는데 Target Type 으로 변환할 수 없다면 ClassCastException 이 발생한다. 이를 막고 싶다면 타입을 변환했을 때 실패하더라도 예외를 던지고 싶지 않다면 Safe Cast 를 이용하면 된다. 대신에 Null 값을 리턴할 수 있다

***

## Collections of a nullable type

> If you have a collection of elements of a nullable type and want to filter non-null elements, you can do so by using filterNotNull:
>
> ````kotlin
> val nullableList: List<Int?> = listOf(1, 2, null, 4)
> val intList: List<Int> = nullableList.filterNotNull()
> ````

- 컬렉션에 Nullable 요소가 있고 이것들을 처리하고 싶다면 filterNotNull 을 이용하면 된다.

