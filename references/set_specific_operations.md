# Set-specific operations

> The Kotlin collections package contains extension functions for popular operations on sets: finding intersections, merging, or subtracting collections from each other.
> 
> To merge two collections into one, use the union() function. It can be used in the infix form a union b. Note that for ordered collections the order of the operands is important: in the resulting collection, the elements of the first operand go before the elements of the second.
>
> To find an intersection between two collections (elements present in both of them), use intersect(). To find collection elements not present in another collection, use subtract(). Both these functions can be called in the infix form as well, for example, a intersect b.
>
> ````kotlin
> val numbers = setOf("one", "two", "three")
> 
> println(numbers union setOf("four", "five"))
> println(setOf("four", "five") union numbers)
> 
> println(numbers intersect setOf("two", "one"))
> println(numbers subtract setOf("three", "four"))
> println(numbers subtract setOf("four", "three")) 
> // same output
> // [one, two, three, four, five]
> // [four, five, one, two, three]
> // [one, two]
> // [one, two]
> // [one, two]
> ````
> 
> Note that set operations are supported by List as well. However, the result of set operations on lists is still a Set, so all the duplicate elements are remove

- Kotlin Collection 에서는 교집합, 합집합, 차집합 기능을 지원하기 위해서 set 컬렉션을 지원한다.
- a union b 는 a 와 b 의 합집합
- a intersect b 는 a 와 b 의 교집합
- a subtract b 는 a 와 b 의 차집합을 제공한다.
- 이런 기능들은 list 컬렉션에서도 제공하는데 이 기능을 쓰고 반환하는 리턴 타입은 set 이므로 주의하자. 즉 중복이 모두 제거된다.






