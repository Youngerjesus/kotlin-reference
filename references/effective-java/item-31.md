# 아이템 31: Unbounded Wildcard 를 사용해 API 유연성을 높여라

***

이전에 이야기 했듯이 제네릭은 Invariant (불공변) 방식이다.

하지만 때로는 유연함을 제공해줘야한다.

예시로 보자.

```java
public class Stack<E> {
    public Stack(); 
    public void push(E e); 
    public E pop(); 
    public boolean isEmpty();
}
```

여기에서 스택에 원소를 넣는 메소드인 pushAll 를 보자.

```java
public void pushAll (Iterable<E> src) {
    for (E e : src) {
        push(e)    
    }
}
```

이 메소드는 타입이 같다면 잘 작동한다.

하지만 Stack<Number> 로 선언하고 Iterable<Integer> 로 push 메소드에 전달한다고 하면 예외가 발생한다. 

논리적으로는 전혀 문제가 없음에도. (<- 이게 문제.)

다행히 해결책으로 자바에서는 Unbounded Wildcard 를 지원해주므로 이걸 사용하면 된다.

즉 Iterable<E> 대신에 Iterable<? extends E> 를 사용하면 E 의 하위타입을 모두 지원한다는 뜻이다. 

여기서 extends E 라는 말은 어색하다. 우리는 하위타입이라는 용어를 사용하는데 extends E 는 E 를 확장시켰다 라는 말이기 떄문에.

와일드카드를 사용하도록 pushAll 메소드를 변경하면 다음과 같다.

```java
public void pushAll(Iterable<? extends E> src) {
    for (E e : src) {
        push(e);    
    }
}
```

이렇게하면 Stack<Number> 로 선언하더라도 Iterable<Integer> 를 넣는게 가능하다.

이제 pushAll 과 쌍을 이루는 popAll 을 보자.

popAll 메소드는 Stack 안에 있는 요소를 매개변수로 받은 컬렉션안으로 모두 옮긴다.

```java
public void popAll(Collection<E> dst) {
    while(!isEmpty()) {
        dst.add(pop());    
    }    
}
```

이 메소드의 경우에도 타입이 같다면 잘 작동한다.

하지만 Stack<Number> 를 Collection<Object> 로 옮기는데에서 컴파일 에러가난다. 물론 이도 논리적으로 문제는 없다.

그러므로 와일드카드를 써서 E 의 상위타입을 매개변수로 받을 수 있도록 popAll() 메소드를 바꿔야한다.

상위타입인 이유는 서브 클래스는 슈퍼 클래스의 스펙을 만족하기 떄문이다. 

하위타입으로 받으면 스펙을 만족하지 않는 E 가 컬렉션을 전달될 수 있기 때문에 이는 논리적으로 맞지 않다.

그러므로 Collection<E> 를 Collection<? super E> 로 바꿔야한다.

여기서의 메시지는 __유연성을 주기 위해서 생산자 관련 메소드와, 소비자 관련 메소드는 와일드 카드를 사용하라__ 이다.

다음 공식을 이해하면 어떤 와일드 카드를 사용해야 하는지 기억하기 쉽다.

`producer-extends, consumer-super`

이 한정적 와일드카드를 사용할 때 리턴타입에는 사용하지 않는게 좋다. (클라이언트 입장에서 어떤 타입으로 받을지 결정해줘야하는 문제가 생기기 떄문에.)

