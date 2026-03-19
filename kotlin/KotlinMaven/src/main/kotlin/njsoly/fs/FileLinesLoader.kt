package njsoly.fs


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException

/**
 * Loads the words file
 */
class FileLinesLoader (val filename: String){

    val file: File = File(filename)

    val lines: List<String>

    val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        lines = try {
            LOGGER.info("reading ${file.name} (size ${file.length()}).")
            file.readLines()
        } catch (e: FileNotFoundException){
            LOGGER.error("couldn't read $file at ${file.absoluteFile}")
            emptyList()
        }

    }
}
