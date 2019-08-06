package njsoly


class SevenZ  (val n: Int = 7, val s: String = "Z") {
    operator fun component1(): Int = n
    operator fun component2(): String = s
}

fun main(args: Array<String>) {
    println("Hello, World")

    val sevenZ = SevenZ()
    val (number, letter) = sevenZ

    println("Destructured number: $number, destructured letter: $letter")
}

