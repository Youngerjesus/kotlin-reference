# Iterators

***

> For traversing collection elements, the Kotlin standard library supports the commonly used mechanism of iterators – objects that provide access to the elements sequentially without exposing the underlying structure of the collection. Iterators are useful when you need to process all the elements of a collection one-by-one, for example, print values or make similar updates to them.
> 
> Iterators can be obtained for inheritors of the Iterable<T> interface, including Set and List, by calling the iterator() function.
>
> Once you obtain an iterator, it points to the first element of a collection; calling the next() function returns this element and moves the iterator position to the following element if it exists.
>
> Once the iterator passes through the last element, it can no longer be used for retrieving elements; neither can it be reset to any previous position. To iterate through the collection again, create a new iterator.
> 
> ```kotlin
> val numbers = listOf("one", "two", "three", "four")
> val numbersIterator = numbers.iterator()
> while (numbersIterator.hasNext()) {
>   println(numbersIterator.next())
> }
> ```
>
> Another way to go through an Iterable collection is the well-known for loop. When using for on a collection, you obtain the iterator implicitly. So, the following code is equivalent to the example above:
>
> ```kotlin
> val numbers = listOf("one", "two", "three", "four")
> for (item in numbers) {
>   println(item)
> }
> ```
> 
> Finally, there is a useful forEach() function that lets you automatically iterate a collection and execute the given code for each element. So, the same example would look like this:
>
> ```kotlin
> val numbers = listOf("one", "two", "three", "four")
> numbers.forEach {
>   println(it)
> }
> ```
- Collection 은 요소를 순회하는 방식을 제공해주기 위해서 Iterator 를 제공해준다.
- Iterator 는 클라이언트 쪽에서 내가 사용하는 타입을 숨길 수 있다. 어떤 자료구조를 쓰는지 몰라도된다.
  - 근데 Iterator 를 쓰는거랑 Kotlin 에서 for loop 쓰는거랑 크게 차이가 있진 않은듯 for loop 에서도 사용하는 타입을 숨길 수 있으니까. 그리고 for loop 이 더 가독성이 좋다.
  - 아니면 forEach 도 Iterator 보다 좋은듯.
- 일반적으로 Iterator 는 처음부터 끝까지 가면 더 이상 다음 요소에 접근할 수 없다. (reset 이 되는게 아니라면)

***

## List iterators

> For lists, there is a special iterator implementation: ListIterator. It supports iterating lists in both directions: forwards and backwards.
>
> Backward iteration is implemented by the functions hasPrevious() and previous(). Additionally, the ListIterator provides information about the element indices with the functions nextIndex() and previousIndex().
>
> ```kotlin
> val numbers = listOf("one", "two", "three", "four")
> val listIterator = numbers.listIterator()
> while (listIterator.hasNext()) listIterator.next()
> 
> println("Iterating backwards:")
> 
> while (listIterator.hasPrevious()) {
>   print("Index: ${listIterator.previousIndex()}")
>   println(", value: ${listIterator.previous()}")
> }
> ```
> 
> Having the ability to iterate in both directions, means the ListIterator can still be used after it reaches the last element.

- ListIterator 를 사용하면 앞 뒤 양방향으로 움직일 수 있다. (이러면 for-loop 보다 장점이 있는듯.)
- ListIterator 가 마자막 요소에 접근한 후 next() 를 호출하면 NoSuchElementException 이 발생한다.

## Mutable iterators

> For iterating mutable collections, there is MutableIterator that extends Iterator with the element removal function remove(). So, you can remove elements from a collection while iterating it.
>
> ```kotlin
> val numbers = mutableListOf("one", "two", "three", "four") 
> val mutableIterator = numbers.iterator()
> 
> mutableIterator.next()
> mutableIterator.remove()    
> println("After removal: $numbers")
> ```
>
> In addition to removing elements, the MutableListIterator can also insert and replace elements while iterating the list.
>
> ```kotlin
> val numbers = mutableListOf("one", "four", "four") 
> val mutableListIterator = numbers.listIterator()
> 
> mutableListIterator.next()
> mutableListIterator.add("two")
> mutableListIterator.next()
> mutableListIterator.set("three")   
> println(numbers)
> ```

- MutableList 에서는 MutableIterator 를 제공해주고 이는 remove() 라는 함수를 제공해준다. 