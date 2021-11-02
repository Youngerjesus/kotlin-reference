# Exceptions

***

## Exception classes

> All exception classes in Kotlin inherit the Throwable class. Every exception has a message, a stack trace, and an optional cause.
> 
> To throw an exception object, use the throw expression:
>
> ````kotlin
> throw Exception("Hi There!")
> ````
> 
> To catch an exception, use the try... catch expression:
>
> ````kotlin
>  try {
>      // some code
>  } catch (e: SomeException) {
>      // handler
>  } finally {
>      // optional finally block
>  }
> ````
> 
> There may be zero or more catch blocks, and the finally block may be omitted. However, at least one catch or finally block is required.

- 코틀린에서 모든 Exception 클래스들은 Throwable 클래스를 상속받는다. 그리고 모든 Exception 은 메시지와 Stack trace 를 가지고 선택적으로 cause 를 가진다.
- catch 블락과 finally 블락은 생략 될 수 있지만 둘 다 생략되는건 불가능하다.

***

## Try is an expression

> try is an expression, which means it can have a return value:
>
> ```kotlin
> val a: Int? = try { input.toInt() } catch (e: NumberFormatException) { null }
> ```
> 
> The returned value of a try expression is either the last expression in the try block or the last expression in the catch block (or blocks). The contents of the finally block don't affect the result of the expression.

- try 블락은 표현식으로 사용이 가능하다. 즉 위의 예제처럼 대입으로 사용이 가능하다. 위 예제의 경우에는 예외가 안난다면 try 블락의 결과값이 전달되고 예외가 나면 catch 블락의 결과값이 전달된다. finally 블락은 값 전달이 되지는 않는다.

***

## Checked exceptions

> Kotlin does not have checked exceptions. There are many reasons for this, but we will provide a simple example that illustrates why it is the case.
>
> The following is an example interface from the JDK implemented by the StringBuilder class:
> 
> ```kotlin
> Appendable append(CharSequence csq) throws IOException;
> ```
> 
> This signature says that every time I append a string to something (a StringBuilder, some kind of a log, a console, etc.), I have to catch the IOExceptions. Why? Because the implementation might be performing IO operations (Writer also implements Appendable). The result is code like this all over the place.:
>
> ```kotlin
>  try {
>      log.append(message)
>  } catch (IOException e) {
>      // Must be safe
>  }
> ```
> 
> And that’s not good. Just take a look at Effective Java, 3rd Edition, Item 77: Don't ignore exceptions.
>
> Bruce Eckel says this about checked exceptions:
>>
>> Examination of small programs leads to the conclusion that requiring exception specifications could both enhance developer productivity and enhance code quality, but experience with large software projects suggests a different result – decreased productivity and little or no increase in code quality.
>
>
> And here are some additional thoughts on the matter:
>
> - [Java's checked exceptions were a mistake (Rod Waldhoff)](https://radio-weblogs.com/0122027/stories/2003/04/01/JavasCheckedExceptionsWereAMistake.html)
> - [The Trouble with Checked Exceptions (Anders Hejlsberg)](https://www.artima.com/articles/the-trouble-with-checked-exceptions)
>
> If you want to alert callers about possible exceptions when calling Kotlin code from Java, Swift, or Objective-C, you can use the @Throws annotation. Read more about using this annotation for Java and for Swift and Objective-C.
  
- Kotlin 에서는 Checked Exception 을 지원하지 않는다. 여기에는 많은 이유가 있다.
  - 예로 StringBuilder 에 append() 라는 메소드를 이용한다고 하면 I/O Exception 이 발생할 수 있다고 한다. (발생할 가능성이 없는데 단지 Appendable 인터페이스를 상속받았다는 이유로.)
  - 발생하지 않는 예외가 있고 이를 처리하는 catch 문이 있다는 점은 불편하다. 
  - Effective Java Item 77 를 보면 예외는 절대 무시하지 말라는 얘기가 나온다. __(이를 통해 혼돈을 줄 수 있는듯. 그래서 Effective Java 에서는 발생하지 않을 Exception 이름을 ignored 로 바꾸라고 하는듯.)__
- Bruce Eckel 은 대규모 프로그램을 만드는 상황이라면 예외처리를 신경쓰라는 요구 사항은 코드 퀄리티를 많이 떨어트린다고도 했다.
- Rod Waldhoff 는 Checked Exception 은 실험이었고 실패라고 얘기했다.
  - 자바 뒤에 나온 언어인 C# 과 Ruby 는 Checked Exception 이 없었다.  
  - Checked Exception 이 발생한다고 해서 딱히 할 수 있는 건 없다. skip, retry, report 등이 있겠지만 이 문제를 전파하는게 가장 합리적인 방법이다. 
  - 오히려 Checked Exception 을 사용하면서 try-catch 를 늘고려하는게 더 불편하다.
  - 애초에 실패하는 케이스가 lower level 인데 이 것들을 처리하는 곳이 high level 이니까 관심사가 다르다라는 뜻.
 
***

## The Nothing type

> throw is an expression in Kotlin, so you can use it, for example, as part of an Elvis expression:
> 
> ```kotlin
> val s = person.name ?: throw IllegalArgumentException("Name required")
> ```
> 
> The throw expression has the type Nothing. This type has no values and is used to mark code locations that can never be reached. In your own code, you can use Nothing to mark a function that never returns:
>
> ```kotlin
>  fun fail(message: String): Nothing {
>      throw IllegalArgumentException(message)
>  }
> ```
> 
> When you call this function, the compiler will know that the execution doesn't continue beyond the call:
>
> ```kotlin
>  val s = person.name ?: fail("Name required")
>  println(s)     // 's' is known to be initialized at this point
> ```
> 
> You may also encounter this type when dealing with type inference. The nullable variant of this type, Nothing?, has exactly one possible value, which is null. If you use null to initialize a value of an inferred type and there's no other information that can be used to determine a more specific type, the compiler will infer the Nothing? type:
>
> ```kotlin
> val x = null           // 'x' has type `Nothing?`
> val l = listOf(null)   // 'l' has type `List<Nothing?>
> ```

- throw 는 Elvis expression 에서 나오기도 한다.
- throw 는 Nothing 의 타입을 가지는데 이는 코드의 위치만 표시할 뿐 값이 없다라는 뜻이다. 그래서 function 에 Nothing 이라는 타입이 있다면 이는 리턴이 없다라는 뜻이다.
- Nothing? 의 타입은 null 로 초기화 하는 경우에 나오는 타입이고 컴파일러가 추론할 정보가 없기 떄문에 사용한다. 

