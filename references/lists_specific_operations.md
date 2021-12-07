# List-specific operations

***

## Retrieve elements by index

> Lists support all common operations for element retrieval: elementAt(), first(), last(), and others listed in Retrieve single elements. What is specific for lists is index access to the elements, so the simplest way to read an element is retrieving it by index. That is done with the get() function with the index passed in the argument or the shorthand [index] syntax.
>
> If the list size is less than the specified index, an exception is thrown. There are two other functions that help you avoid such exceptions:
>
> - getOrElse() lets you provide the function for calculating the default value to return if the index isn't present in the collection.
> - getOrNull() returns null as the default value.
> 
> ```kotlin
> val numbers = listOf(1, 2, 3, 4)
> println(numbers.get(0))
> println(numbers[0])
> //numbers.get(5)                         // exception!
> println(numbers.getOrNull(5))             // null
> println(numbers.getOrElse(5, {it}))        // 5
> ```

- Kotlin Collection 의 list 는 index 를 통해서 접근할 수 있다. 
- 그리고 이런 index 접근을 추상화 해놓은 오퍼레이션들도 있다. (e.g first(), last(), elementAt())
- 인덱스로 접근할 때 인덱스보다 리스트의 사이즈가 더 적은 경우 예외를 낼 수 있는데 예외가 안나도록 하는 방법이 두 가지가 있다.
  - getOrElse() : 예외 대신에 기본값을 전달해주는 메소드  
  - getOrNull() : 예외 대신에 널값을 전달해주는 메소드 

## Retrieve list parts

> In addition to common operations for Retrieving Collection Parts, lists provide the subList() function that returns a view of the specified elements range as a list. Thus, if an element of the original collection changes, it also changes in the previously created sublists and vice versa.
>
> ```kotlin
> val numbers = (0..13).toList()
> println(numbers.subList(3, 6)) // 3, 4, 5
> ```

- list 의 부분값을 가져올 수 있는 subList 메소드도 있다. 

## Find element positions

### Linear search

> In any lists, you can find the position of an element using the functions indexOf() and lastIndexOf(). They return the first and the last position of an element equal to the given argument in the list. If there are no such elements, both functions return -1.
>
> ```kotlin
> val numbers = listOf(1, 2, 3, 4, 2, 5)
> println(numbers.indexOf(2)) // 1
> println(numbers.lastIndexOf(2)) // 4
> ```
>
> There is also a pair of functions that take a predicate and search for elements matching it:
>
> - indexOfFirst() returns the index of the first element matching the predicate or -1 if there are no such elements.
> - indexOfLast() returns the index of the last element matching the predicate or -1 if there are no such elements.
>
> ````kotlin
> val numbers = mutableListOf(1, 2, 3, 4)
> println(numbers.indexOfFirst { it > 2}) // 2
> println(numbers.indexOfLast { it % 2 == 1}) // 2
> ````

- list 에서 특정 요소의 인덱스를 찾는 메소드를 제공해준다.
- indexOf() 메소드는 인자로 전달한 값이 리스트에 처음 등장하는 인덱스를 반환한다.
- lastIndexOf() 메소드는 인자로 전달한 값이 리스트에 마지막으로 등장하는 인덱스를 반환한다.
- indexOfFirst() 메소드는 조건에 맞는 값이 처음에 등장하는 인덱스를 반환한다.
- indexOfLast() 메소드는 조건에 맞는 값이 마지막으로 등장하는 인덱스를 반환한다.

### Binary search in sorted lists

> There is one more way to search elements in lists – binary search. It works significantly faster than other built-in search functions but requires the list to be sorted in ascending order according to a certain ordering: natural or another one provided in the function parameter. Otherwise, the result is undefined.
>
> To search an element in a sorted list, call the binarySearch() function passing the value as an argument. If such an element exists, the function returns its index; otherwise, it returns (-insertionPoint - 1) where insertionPoint is the index where this element should be inserted so that the list remains sorted. If there is more than one element with the given value, the search can return any of their indices.
>
> You can also specify an index range to search in: in this case, the function searches only between two provided indices.
>
> ````kotlin
> val numbers = mutableListOf("one", "two", "three", "four")
> numbers.sort()
> println(numbers)
> println(numbers.binarySearch("two"))  // 3
> println(numbers.binarySearch("z")) // -5
> println(numbers.binarySearch("two", 0, 2))  // -3
> ````

- list 가 정렬되어 있다면 binarySearch() 를 써서 특정 요소를 찾는게 더 빠르다. 
- binarySearch() 는 정렬이 되어있는걸 요구하고 정렬되어 있지 않다면 (-insertionPoint - 1) 값을 리턴한다. (여기서 insertionPoint 는 만약 해당 값이 리스트에 있다면 위치할 인덱스를 말한다.)
- 리스트의 특정 범위에서만 binarySearch() 를 할 수도 있다. 이 경우 인자로 범위를 주면 된다.

#### Comparator binary search

> When list elements aren't Comparable, you should provide a Comparator to use in the binary search. The list must be sorted in ascending order according to this Comparator. Let's have a look at an example:
>
> ````kotlin
> val productList = listOf(
> Product("WebStorm", 49.0),
> Product("AppCode", 99.0),
> Product("DotTrace", 129.0),
> Product("ReSharper", 149.0))
>
> println(productList.binarySearch(Product("AppCode", 99.0), compareBy<Product> { it.price }.thenBy { it.name })) // 1
> ````
>
> Here's a list of Product instances that aren't Comparable and a Comparator that defines the order: product p1 precedes product p2 if p1 's price is less than p2 's price. So, having a list sorted ascending according to this order, we use binarySearch() to find the index of the specified Product.
>
> Custom comparators are also handy when a list uses an order different from natural one, for example, a case-insensitive order for String elements.
>
> ````kotlin
> val colors = listOf("Blue", "green", "ORANGE", "Red", "yellow")
> println(colors.binarySearch("RED", String.CASE_INSENSITIVE_ORDER)) // 3
> ````

- 특정 요소를 정렬하는 비교 기준을 세울 수 없다면 즉 Comparable 인터페이스를 상속한 값이 리스트에 속해있지 않다면 이것을 만들어줘야한다.
- 만들어 줄 때 Comparator 인터페이스를 통해 만들어 줄 수 있고 compareBy<>() 를 통해 만들어 줄 수 있다.
- 그리고 BinarySearch 할 때 이 기준을 인자로 넘겨주면 된다.
- String 의 경우 비교하는 Comparator 로 String.CASE_INSENSITIVE_ORDER 를 제공해주는데 이는 대소문자를 구별하지 않는 Comparator 이다. 

***

## List write operations

> In addition to the collection modification operations described in Collection write operations, mutable lists support specific write operations. Such operations use the index to access elements to broaden the list modification capabilities.

### Add

> To add elements to a specific position in a list, use add() and addAll() providing the position for element insertion as an additional argument. All elements that come after the position shift to the right.
>
> ```kotlin
> val numbers = mutableListOf("one", "five", "six")
> numbers.add(1, "two")
> numbers.addAll(2, listOf("three", "four"))
> println(numbers)
> ```

### Update

> Lists also offer a function to replace an element at a given position - set() and its operator form []. set() doesn't change the indexes of other elements.
>
> ````kotlin
> val numbers = mutableListOf("one", "five", "three")
> numbers[1] =  "two"
> println(numbers)
> ````
> 
> fill() simply replaces all the collection elements with the specified value.
>
> ````kotlin
> val numbers = mutableListOf(1, 2, 3, 4)
> numbers.fill(3)
> println(numbers) // [3,3,3,3]
> ````

- fill() 메소드를 통해 리스트 내의 모든 요소를 replace 하는게 가능하다.

### Remove

> To remove an element at a specific position from a list, use the removeAt() function providing the position as an argument. All indices of elements that come after the element being removed will decrease by one.
>
> ```kotlin
> val numbers = mutableListOf(1, 2, 3, 4, 3)    
> numbers.removeAt(1)
> println(numbers)
> ```

### Sort

> In Collection Ordering, we describe operations that retrieve collection elements in specific orders. For mutable lists, the standard library offers similar extension functions that perform the same ordering operations in place. When you apply such an operation to a list instance, it changes the order of elements in that exact instance.
>
> The in-place sorting functions have similar names to the functions that apply to read-only lists, but without the ed/d suffix:
>
> - sort* instead of sorted* in the names of all sorting functions: sort(), sortDescending(), sortBy(), and so on.
> - shuffle() instead of shuffled().
> - reverse() instead of reversed().
> 
> asReversed() called on a mutable list returns another mutable list which is a reversed view of the original list. Changes in that view are reflected in the original list. The following example shows sorting functions for mutable lists:
>
> ````kotlin
> val numbers = mutableListOf("one", "two", "three", "four")
> 
> numbers.sort()
> println("Sort into ascending: $numbers")
> numbers.sortDescending()
> println("Sort into descending: $numbers")
> 
> numbers.sortBy { it.length }
> println("Sort into ascending by length: $numbers")
> numbers.sortByDescending { it.last() }
> println("Sort into descending by the last letter: $numbers")
> 
> numbers.sortWith(compareBy<String> { it.length }.thenBy { it })
> println("Sort by Comparator: $numbers")
> 
> numbers.shuffle()
> println("Shuffle: $numbers")
> 
> numbers.reverse()
> println("Reverse: $numbers")
> 
> // Output 
> // Sort into ascending: [four, one, three, two]
> // Sort into descending: [two, three, one, four]
> // Sort into ascending by length: [two, one, four, three]
> // Sort into descending by the last letter: [four, two, one, three]
> // Sort by Comparator: [one, two, four, three]
> // Shuffle: [one, three, four, two]
> // Reverse: [two, four, three, one]
> ````

- shuffle() 을 통해 리스트의 요소들을 섞을 수 있다.  
- sort() 를 통해 정렬할 수 있고 sortDescending() 을 통해 내림차순으로 정렬할 수 있다.
- reverse() 를 통해 거꾸로 정렬하는게 가능하다. 
- asReversed() 를 통해 오리지날 리스트는 변경하지 않고 거꾸로 정렬한 결과를 가지고 올 수 있다.

