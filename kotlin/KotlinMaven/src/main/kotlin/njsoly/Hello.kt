package njsoly

import java.io.File
import java.time.LocalDate


fun main(args: Array<String>) {
    println("Hello, World")

    val (number, letter) = SevenZ()

    val file: File = File("twl.txt")
    println("File ${file.name} exists: ${file.exists()}")
    val sevenZ = SevenZ()
    val (number, letter) = sevenZ

    println("Destructured number: $number, destructured letter: $letter")
}

class Hello {
    val now = LocalDate.now()
}

