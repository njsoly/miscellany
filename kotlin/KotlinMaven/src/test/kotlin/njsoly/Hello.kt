package njsoly

import java.time.LocalDate


fun main(args: Array<String>) {
    println("Hello, World")

    val sevenZ = SevenZ()
    val (number, letter) = sevenZ

    println("Destructured number: $number, destructured letter: $letter")
}

class Hello {
    val now: LocalDate = LocalDate.now()
}
