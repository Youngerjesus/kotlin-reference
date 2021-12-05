# Filtering collections

> Filtering is one of the most popular tasks in collection processing. In Kotlin, filtering conditions are defined by predicates – lambda functions that take a collection element and return a boolean value: true means that the given element matches the predicate, false means the opposite.
>
> The standard library contains a group of extension functions that let you filter collections in a single call. These functions leave the original collection unchanged, so they are available for both mutable and read-only collections. To operate the filtering result, you should assign it to a variable or chain the functions after filtering.

- Collection 에서 한번의 call() 을 통해 Filtering 을 진행하는게 가능하다.
- Filtering 을 해도 Original Collection 에서는 영향을 주지 않는다.
- Filtering 결과를 가지고오려면 변수를 할당하거나, 체이닝을 할당해놓으면 된다. 

## Filter by predicate

> The basic filtering function is filter(). When called with a predicate, filter() returns the collection elements that match it. For both List and Set, the resulting collection is a List, for Map it's a Map as well.
>
> ```kotlin
> val numbers = listOf("one", "two", "three", "four")  
> val longerThan3 = numbers.filter { it.length > 3 }
> println(longerThan3)
> 
> val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
> val filteredMap = numbersMap.filter { (key, value) -> key.endsWith("1") && value > 10}
> println(filteredMap)
> ```
> 
> The predicates in filter() can only check the values of the elements. If you want to use element positions in the filter, use filterIndexed(). It takes a predicate with two arguments: the index and the value of an element.
>
> To filter collections by negative conditions, use filterNot(). It returns a list of elements for which the predicate yields false.
>
> There are also functions that narrow the element type by filtering elements of a given type:
>
> - filterIsInstance() returns collection elements of a given type. Being called on a List<Any>, filterIsInstance<T>() returns a List<T>, thus allowing you to call functions of the T type on its items.
>
> ```kotlin
> val numbers = listOf(null, 1, "two", 3.0, "four")
> println("All String elements in upper case:")
> numbers.filterIsInstance<String>().forEach {
>     println(it.uppercase())
> }
> // Output
> // All String elements in upper case:
> // TWO
> // FOUR
> ```
> 
> filterNotNull() returns all non-null elements. Being called on a List<T?>, filterNotNull() returns a List<T: Any>, thus allowing you to treat the elements as non-null objects.
>
> ```kotlin
> val numbers = listOf(null, "one", "two", null)
> numbers.filterNotNull().forEach {
>       println(it.length)   // length is unavailable for nullable Strings
>   }
> ```
 
- Predicate 라는 람다 표현식을 Filter 에서 적용하는 예제를 소개하는 것
- Filter 는 list, map, set 과 같은 컬렉션에 적용할 수 있음.
- 만약 Filter 를 하는데 인덱스의 요소가 필요하다면 filterIndexed() 메소드를 사용하면 됨.
- 필터를 하지 않기를 원한다면 filterNot() 메소드를 사용하자
- 리스트 요소의 타입을 제한시키기를 원한다면 filterIsInstance<T> 메소드를 통해서 제한하자.
- Null 요소를 제한시키고 싶다면 filterNotNull() 메소드를 사용하자.

***

## Partition

> Another filtering function – partition() – filters a collection by a predicate and keeps the elements that don't match it in a separate list. So, you have a Pair of List s as a return value: the first list containing elements that match the predicate and the second one containing everything else from the original collection.
>
> ```kotlin
> val numbers = listOf("one", "two", "three", "four")
> val (match, rest) = numbers.partition { it.length > 3 }
> 
> println(match)
> println(rest)
> 
> // Output
> // [three, four]
> // [one, two]
> ```

- partition() 이라는 메소드를 통해서 필터된 리스트, 필터가 안되는 리스트 를 Pair 형태로 받을 수 있는 메소드가 있다.

***

## Test predicates

> Finally, there are functions that simply test a predicate against collection elements:
>
> - any() returns true if at least one element matches the given predicate.
> - none() returns true if none of the elements match the given predicate.
> - all() returns true if all elements match the given predicate. Note that all() returns true when called with any valid predicate on an empty collection. Such behavior is known in logic as vacuous truth.
>
> ```kotlin
> val numbers = listOf("one", "two", "three", "four")
> 
> println(numbers.any { it.endsWith("e") })
> println(numbers.none { it.endsWith("a") })
> println(numbers.all { it.endsWith("e") })
> 
> println(emptyList<Int>().all { it > 5 }) 
> 
> // Output
> // true
> // true
> // false
> // true
> ```

- Collection 의 요소들중에 이런게 있는지 간단하게 Predicate Type 으로 확인할 수 있는 메소드가 있다. (any, none, all)
- all() 을 사용할 때 조심해야하는데 empty 인 리스트이면 true 를 리턴한다.  

