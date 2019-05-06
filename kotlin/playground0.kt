/** 
 * https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMS4zLjMxIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsImpzQ29kZSI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoidmFsIGw6IExpc3Q8SW50PiA9ICgxLi45KS50b0xpc3QoKVxuXG5cblxuXG5cblxuZnVuIG1haW4oKSB7XG4gICAgcHJpbnRsbihcIkhlbGxvLCB3b3JsZCEhISBcIiArIGwpXG4gICAgZm9yKGkgaW4gbCl7XG4gICAgICAgIHByaW50bG4oXCJpOiAkaTsgbDogJHtsLnRvTGlzdCgpLnRvU3RyaW5nKCl9XCIpXG4gICAgfVxuICAgIFxuICAgIHZhbCBvZGRzID0gbC5maWx0ZXJ7eCAtPiB4ICUgMiAhPSAwfVxuICAgIHZhbCBldmVucyA9IGwuZmlsdGVye2l0ICUgMiA9PSAwfVxuICAgIHByaW50bG4oXCJvZGRzOiAkb2RkcywgZXZlbnM6ICRldmVuc1wiKVxufVxuXG5cbiJ9
 */

val l: List<Int> = (1..9).toList()

fun main() {
    println("Hello, world!!! " + l)
    for(i in l){
        println("i: $i; l: ${l.toList().toString()}")
    }
    
    val odds = l.filter{x -> x % 2 != 0}
    val evens = l.filter{it % 2 == 0}
    println("odds: $odds, evens: $evens")
}


