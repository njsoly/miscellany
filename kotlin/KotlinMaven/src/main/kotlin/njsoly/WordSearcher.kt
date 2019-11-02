package njsoly

import java.io.File
import java.time.LocalDate


fun main(args: Array<String>) {
    println("Hello, WordSearcher.")
    
    println("File ${WordSearcher.file.name} exists: ${WordSearcher.file.exists()}")
    println("File ${WordSearcher.file2.name} exists: ${WordSearcher.file2.exists()}")

}

class WordSearcher (val filename: String = file.name){


    companion object {
        val now = LocalDate.now()

        val file: File = File("twl.txt")
        val file2: File = File("twl06.txt")

    }


}

