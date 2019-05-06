/** 
 * https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMS4zLjMxIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsImpzQ29kZSI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoidmFsIGw6IExpc3Q8SW50PiA9ICgxLi45KS50b0xpc3QoKVxuXG5cblxuXG5cblxuZnVuIG1haW4oKSB7XG4gICAgcHJpbnRsbihcIkhlbGxvLCB3b3JsZCEhISBcIiArIGwpXG4gICAgZm9yKGkgaW4gbCl7XG4gICAgICAgIHByaW50bG4oXCJpOiAkaTsgbDogJHtsLnRvTGlzdCgpLnRvU3RyaW5nKCl9XCIpXG4gICAgfVxuICAgIFxuICAgIHZhbCBvZGRzID0gbC5maWx0ZXJ7eCAtPiB4ICUgMiAhPSAwfVxuICAgIHZhbCBldmVucyA9IGwuZmlsdGVye2l0ICUgMiA9PSAwfVxuICAgIHByaW50bG4oXCJvZGRzOiAkb2RkcywgZXZlbnM6ICRldmVuc1wiKVxufVxuXG5cbiJ9
 * https://pl.kotl.in/oTCV_bZrE
 * 2019-05-06
 */

val l: List<Int> = (1..9).toList()
var b: String = "snth"
// the long way to do a "use if not null", without elvis operator
val c: Int = if (b != null) b.length else -1
// a shorter way to do it, WITH elvis operator.
val d = b?.length ?: -1

// safe cast
val aInt: Int? = c as? Int

fun dealWithAnyX(x: Any){
    // example of WHEN-IS block
    when (x) {
    is Int -> print(x + 1)
    is String -> print(x.length + 1)
    is IntArray -> print(x.sum())
	}
}

fun funWithFilters (){
    val l: List<Int> = (1..9).toList()

    // some filter fun
    val odds = l.filter{x -> x % 2 != 0}
    val evens = l.filter{it % 2 == 0}

    for(i in l){
        println("i: $i; l: ${l.toList().toString()}")
    }
    
    println("odds: $odds, evens: $evens")
}
fun main() {
    println("Hello, world!!! " + l)
    
    funWithFilters()
    dealWithAnyX()

}
