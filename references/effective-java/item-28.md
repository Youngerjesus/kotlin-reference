# 아이템 28: 배열 보다는 리스트를 사용하라

***

배열과 제네릭 타입에는 중요한 차이가 두 가지 있다.

배열은 Covariant 라는 점. 제네릭은 Invariant 라는 점이다.

먼저 Invariant 부터 설명하겠다. Covariant 에서 말할 내용이 많으므로. 

Invariant 는 Type1, Type2 가 상위 하위 타입이라도 List<Type1>, List<Type2> 는 서로 다른 타입이라는 것.
- 그러므로 Invariant 는 같은 타입일 때만 사용이 가능하고 컴파일 에러를 내준다. 
- 즉 List<Object> 에서 구현체로 List<String> 을 넣는게 말이 안된다 라는 것. 

Covariant 라는 뜻은 Sub 가 Super 의 하위타입이라면 Sub[] 는 Super[] 의 하위 타입이라는 것.
- 이게 맞는 말 처럼 보이지만 문제가 많다.
- 이 말이 가져다주는 건 상위타입의 배열에 하위타입의 배열을 넣는게 가능하다라는 것.
  - 문제는 컴파일 에러를 내주지도 않고 잘못해서 하위타입의 객체를 넣을려고 할 때 에러가 날 수 있다라는 점.
  - 하위타입을 지원해준다는 것 자체에서 많은 에러가 발생할 여지가 있다.

````java
@Test
void success() {
    Object[] objects = new Long[1];
    objects[0] = 5L; // Success
}

@Test
void fail() {
    Object[] objects = new Long[1];
    objects[0] = "Test"; // ArrayStoreException 발생 
}
````

배열과 제네릭의 두 번째 차이점은 런타임에서 타입 정보가 남아있는지의 여부다.

배열 같은 경우는 런타임에도 자신에게 대입할려는 요소의 타입을 확인하고 넣는다.

하지만 제네릭은 컴파일 타임에서만 타입을 계산하지 런타임에서는 타입에 대한 정보가 없다. 즉 타입 정보가 소거된다. 

소거를 지원한 이유는 자바 5 이후에 레거시 코드와 제네릭 타입을 함께 사용하기 위해서 지원했다고 알려져 있다. 이로인해 자바 5가 순조롭게 제네릭을 사용할 수 있게되었다고 한다.

이러한 이유로 자바에서 제네릭 배열을 생성하지 못하도록 컴파일에서 막는다.

만약에 막지 않는다면 어떠한 일이 생길지 알아보자.

```java
List<String>[] stringList = new List<String>[1];    // (1)
List<Integer> intList = List.of(42);                // (2)
Object[] objects = stringList;                      // (3)
objects[0] = intList;                               // (4)
String s = stringList[0].get(0); 
```

원래 (1) 에서 컴파일 에러가 나지만 안난다고 가정해보고 어떤 일이 일어나느지 보자.

(2) 에서는 Integer 타입의 List 를 만들었다.

(3) 에서 Object 타입의 배열에서 제네릭 배열을 넣어줬다. 하위타입으로 변환해줄 수 있기 때문에 이것이 가능하다.

(4) 에서 Object 배열의 첫 원소로 intList 라는 제네릭을 넣는다. 제네릭은 타입이 소거되었기 때문에 가능하다.

(5) 에서 값을 하나 가지고 오는데 이 값은 String 타입의 값이 아니라 Integer 타입의 값이다. Integer 타입인데 String 타입으로 자동 형변환을 할려고 하므로 ClassCastException 이 발생한다.

