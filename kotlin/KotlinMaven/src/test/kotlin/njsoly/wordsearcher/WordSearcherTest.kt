package njsoly.wordsearcher

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import kotlin.text.Regex.Companion.escape

@RunWith(MockitoJUnitRunner::class)

class WordSearcherTest : WordSearcher() {

    val wordSearcher: WordSearcher = WordSearcher()

    @Before
    fun setup () {

    }

    @Test
    fun testGetWords() {
    }

    @Test
    fun testInputLoop() {
    }

    @Test
    fun `isSimple() given simple word returns true`() {
       assertEquals(true, "snth".isSimple())
    }
    @Test
    fun `isSimple() given word with dot returns false`() {
       assertEquals(false, "A.A".isSimple())
    }

    @Test
    fun `isSimple() given two words with dot returns false`() {
       assertEquals(false, "A.A SNTH".isSimple())
    }

    @Test
    fun `isSimple() given two words returns false`() {
       assertEquals(false, "AAA SNTH".isSimple())
    }

    @Test
    fun `isSimple() given word with numbers returns false`() {
       assertEquals(false, "A03U3SNTH".isSimple())
    }

    @Test
    fun `how to use regex` () {
        val s = "[AZ]LZ"
        val escd = escape(s)
        println("\"$s\"escaped: $escd")
        assertEquals(true, "ALZ".matches(Regex(s)))
        println("..A..".trim('.')+".*?")
        val pattern = "..A.."
        println("adjusted regex: \".*${pattern.trim('.')}.*\"")
        assertEquals(true, "BLACK".matches(Regex(".*"+"..A..".trim('.')+".*?")))
    }

    @Test
    fun `filterToPattern works` () {
        val pattern = "..A.."

        assertEquals(listOf("BLACK"), listOf("BLACK").filterToPattern(pattern))
    }

    @Test
    fun `filterToLength() with exact length does it right` () {
        assertEquals(listOf("WHAT"), listOf("WHAT").filterToLength(4))
    }

    @Test
    fun `filterToLength() with min and max`() {
        assertEquals(listOf("WHAT"), listOf("WHAT").filterToLength(4,4))
    }

    @Test
    fun `getFilename() returns non-null` () {
        assertNotNull(wordSearcher.filename)
    }

    @Test
    fun `matchLettersToWord with letters BLACK and word BLACK and pattern BL-CK returns true` () {
        val letters = "BLAKC"
        val word = "BLACK"
        val pattern = "BL.CK"
        val result = wordSearcher.matchLettersToWord(letters, word, pattern, 0)
        assertEquals(true, result)
    }
    @Test
    fun `matchLettersToWord with letters BLACK and word BLACK and pattern --A-- returns true` () {
        val letters = "BLAKC"
        val word = "BLACK"
        val pattern = "..A.."
        val result = wordSearcher.matchLettersToWord(letters, word, pattern, 0)
        assertEquals(true, result)
    }

    /**
     * @see WordSearcher.isQuitMessage
     * @param input to check if user requested to quit
     * @param expected whether or not that should be seen as a quit message
     */
    fun `isQuitMessage() given (an input) returns (a boolean)` (input: String, expected: Boolean) {
        assertEquals("", true, wordSearcher.isQuitMessage("quit"))
    }
}
