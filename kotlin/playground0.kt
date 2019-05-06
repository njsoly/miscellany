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


