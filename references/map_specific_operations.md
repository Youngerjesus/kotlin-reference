# Map-specific operations

> In maps, types of both keys and values are user-defined. Key-based access to map entries enables various map-specific processing capabilities from getting a value by key to separate filtering of keys and values. On this page, we provide descriptions of the map processing functions from the standard library.

***

## Retrieve keys and values

> For retrieving a value from a map, you must provide its key as an argument of the get() function. The shorthand [key] syntax is also supported. If the given key is not found, it returns null. There is also the function getValue() which has slightly different behavior: it throws an exception if the key is not found in the map. Additionally, you have two more options to handle the key absence:
>
> - getOrElse() works the same way as for lists: the values for non-existent keys are returned from the given lambda function.
> - getOrDefault() returns the specified default value if the key is not found.
> 
> ````kotlin
> val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
> println(numbersMap.get("one"))
> println(numbersMap["one"])
> println(numbersMap.getOrDefault("four", 10))
> println(numbersMap["five"])               // null
> //numbersMap.getValue("six")      // exception!
> ````
> 
> To perform operations on all keys or all values of a map, you can retrieve them from the properties keys and values accordingly. keys is a set of all map keys and values is a collection of all map values.
>
> ````kotlin
> val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
> println(numbersMap.keys)
> println(numbersMap.values)
> ````

- map 에서는 key 를 통해 value 를 찾을 수 있다. 이와 관련된 함수는 좀 많은데 하나씩 살펴보자.  
- get() 메소드를 통해서 value 를 찾을 때 해당 key 값에 따른 value 가 없다면 null 을 가지고온다.
- getValue() 메소드를 통해서 value 를 찾을 때 해당 key 값에 따른 value 가 없다면 예외를 낸다.
- getOrDefault() 메소드는 해당 key 값에 따른 value 가 없다면 인자로 준 기본 값을 가지고온다.
- getOrElse() 메소드는 해당 key 값에 따른 value 가 없다면 람다식을 실행한 값을 가지고온다.
- map.keys 를 통해 모든 키 리스트를 조회하는게 가능하다.
- map.values 를 통해 모든 value 리스트를 조회하는게 가능하다.

***

## Filter

> You can filter maps with the filter() function as well as other collections. When calling filter() on a map, pass to it a predicate with a Pair as an argument. This enables you to use both the key and the value in the filtering predicate.
> 
> ```kotlin
> val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
> val filteredMap = numbersMap.filter { (key, value) -> key.endsWith("1") && value > 10}
> println(filteredMap)
> ```
> 
> There are also two specific ways for filtering maps: by keys and by values. For each way, there is a function: filterKeys() and filterValues(). Both return a new map of entries which match the given predicate. The predicate for filterKeys() checks only the element keys, the one for filterValues() checks only values.
>
> ````kotlin
> val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
> val filteredKeysMap = numbersMap.filterKeys { it.endsWith("1") }
> val filteredValuesMap = numbersMap.filterValues { it < 10 }
> 
> println(filteredKeysMap)
> println(filteredValuesMap)
> ````

- map Collection 도 filter() 메소드를 제공해서 원하는 값만 남겨놓을 수 있다.
- filter() 메소드를 실행하면 실행할 람다식으로 key, value 가 Pair 로 담겨져 있는 값을 인자로 받을 수 있고 람다식에선 Boolean 값을 리턴하도록 하면 해당 값만 남겨지는 핕터링을 할 수 있다.
- key 만 또는 value 만 필터링해서 가져오고 싶다면 map.filterKeys() 메소드와 map.filterValues() 메소드를 실행하면 된다.

***

## Plus and minus operators

> Due to the key access to elements, plus (+) and minus (-) operators work for maps differently than for other collections. plus returns a Map that contains elements of its both operands: a Map on the left and a Pair or another Map on the right. When the right-hand side operand contains entries with keys present in the left-hand side Map, the result map contains the entries from the right side.
>
> ````kotlin
> val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
> println(numbersMap + Pair("four", 4))
> println(numbersMap + Pair("one", 10))
> println(numbersMap + mapOf("five" to 5, "one" to 11))
> // Output
> // {one=1, two=2, three=3, four=4}
> // {one=10, two=2, three=3}
> // {one=11, two=2, three=3, five=5}
> ````
> 
> minus creates a Map from entries of a Map on the left except those with keys from the right-hand side operand. So, the right-hand side operand can be either a single key or a collection of keys: list, set, and so on.
>
> ````kotlin
> val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
> println(numbersMap - "one")
> println(numbersMap - listOf("two", "four"))
> 
> // Output
> // {two=2, three=3}
> // {one=1, three=3}
> ````

- Map Collection 에서는 Plus (+) 연산과 Minus (-) 연산을 지원한다.
- Plus 연산을 할 땐 왼쪽 피연산자는 Map 이어야하고, 오른쪽 피연산자는 key-value 가 포함된 Pair 인 형태의 값이어야 한다. 이 경우 값 두 개를 더할 수 있고 갱신이 될 수 있다. 갱신은 Pair 값으로 된다.
- Minus 연산을 할 때 왼쪽 피연산자는 Map 이어야하고 오른쪽 피연산자는 Key 값이면 된다. 해당 Key 가 Map 에 있다면 지워진다.

***

## Map write operations

> Mutable maps offer map-specific write operations. These operations let you change the map content using the key-based access to the values.
>
> There are certain rules that define write operations on maps:
>
> - Values can be updated. In turn, keys never change: once you add an entry, its key is constant.
> - For each key, there is always a single value associated with it. You can add and remove whole entries.
> 
> Below are descriptions of the standard library functions for write operations available on mutable maps.

### Add and update entries

> To add a new key-value pair to a mutable map, use put(). When a new entry is put into a LinkedHashMap (the default map implementation), it is added so that it comes last when iterating the map. In sorted maps, the positions of new elements are defined by the order of their keys.
>
> ```kotlin
> val numbersMap = mutableMapOf("one" to 1, "two" to 2)
> numbersMap.put("three", 3)
> println(numbersMap)
> ```
> 
> To add multiple entries at a time, use putAll(). Its argument can be a Map or a group of Pair s: Iterable, Sequence, or Array.
>
> ```kotlin
> val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
> numbersMap.putAll(setOf("four" to 4, "five" to 5))
> println(numbersMap)
> ```
> 
> Both put() and putAll() overwrite the values if the given keys already exist in the map. Thus, you can use them to update values of map entries.
>
> ```kotlin
> val numbersMap = mutableMapOf("one" to 1, "two" to 2)
> val previousValue = numbersMap.put("one", 11)
> println("value associated with 'one', before: $previousValue, after: ${numbersMap["one"]}")
> println(numbersMap)
> ```
> 
> You can also add new entries to maps using the shorthand operator form. There are two ways:
>
> - plusAssign (+=) operator.
> - the [] operator alias for set().
> 
> ```kotlin
> val numbersMap = mutableMapOf("one" to 1, "two" to 2)
> numbersMap["three"] = 3     // calls numbersMap.put("three", 3)
> numbersMap += mapOf("four" to 4, "five" to 5)
> println(numbersMap)
> ```
> 
> When called with the key present in the map, operators overwrite the values of the corresponding entries.

- put 메소드를 통해서 mutableMap() 에 값을 넣을 수 있다. 
- mutableMap() 의 기본 구현체는 LinkedHashMap 이므로 값을 넣으면 맨 마지막 요소에 값이 들어간다. 
- 정렬된 Map 에서는 key 를 기준으로 정렬해서 값이 들어간다.
- putAll() 메소드를 통해서 한번에 값을 넣는 것도 가능한데 Pair 형태라면 어떠한 값이든지 상관없이 넣을 수 있다.
- '+=' 연산을 통해서도 값을 넣는게 가능하다. 물론 key 값이 중복되는게 있다면 업데이트되서 들어간다.

### Remove entries

> To remove an entry from a mutable map, use the remove() function. When calling remove(), you can pass either a key or a whole key-value-pair. If you specify both the key and value, the element with this key will be removed only if its value matches the second argument.
>
> ```kotlin
> val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
> numbersMap.remove("one")
> println(numbersMap)
> numbersMap.remove("three", 4)            //doesn't remove anything
> println(numbersMap)
> ```
>
> You can also remove entries from a mutable map by their keys or values. To do this, call remove() on the map's keys or values providing the key or the value of an entry. When called on values, remove() removes only the first entry with the given value.
>
> ```kotlin
> val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3, "threeAgain" to 3)
> numbersMap.keys.remove("one")
> println(numbersMap)
> numbersMap.values.remove(3)
> println(numbersMap)
> ```
> 
> The minusAssign (-=) operator is also available for mutable maps.
>
> ```kotlin
> val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
> numbersMap -= "two"
> println(numbersMap)
> numbersMap -= "five"             //doesn't remove anything
> println(numbersMap)
> ```

- map 에 있는 요소를 지울 땐 remove() 함수를 호출해서 지울 수 있다. key 값을 전달해도 좋고, key-value 를 전달해도 지울 수 있다.
- map.keys.remove(), map.values.remove() 를 통해서 구체적인 key, value 를 인자로 전달해서 지울 수도 있다.
- '-=' 연산자를 통해서 지울 수도 있다. 이때는 key 값을 전달하면 된다.



