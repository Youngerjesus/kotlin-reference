# Pair

> ````kotlin
> data class Pair<out A, out B> : Serializable
>````
> 
> Represents a generic pair of two values.
>
> There is no meaning attached to values in this class, it can be used for any purpose. Pair exhibits value semantics, i.e. two pairs are equal if both components are equal.
>
> ````kotlin
> val (a, b) = Pair(1, "x")
> println(a) // 1
> println(b) // x
> ````
> 
> Properties
> - val first: A
> - val second: B 

 

