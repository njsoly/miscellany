package njsoly.wordsearcher

import njsoly.log.SetupLogger
import org.apache.log4j.Logger
import java.io.File
import java.io.FileNotFoundException

/**
 * Loads the words file
 */
class Loader (val filename: String){

    val file: File = File(filename)

    val words: List<String>

    val LOGGER: Logger = SetupLogger.setupLogger(this::class.java)

    init {
        words = try {
            LOGGER.info("reading ${file.name} (size ${WordSearcher.file.length()}).")
            file.readLines()
        } catch (e: FileNotFoundException){
            LOGGER.error("couldn't read $file at ${file.absoluteFile}")
            emptyList()
        }

    }
}
