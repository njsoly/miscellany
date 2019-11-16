package njsoly.wordsearcher

import java.io.File
import java.io.FileNotFoundException

class Loader (val filename: String){

    val file: File = File(filename)

    val words: List<String>

    init {
        words = try {
            WordSearcher.info("reading ${WordSearcher.file.name} (size ${WordSearcher.file.length()}).")
            file.readLines()
        } catch (e: FileNotFoundException){
            WordSearcher.error("couldn't read $file at ${file.absoluteFile}")
            emptyList()
        }

    }
}
