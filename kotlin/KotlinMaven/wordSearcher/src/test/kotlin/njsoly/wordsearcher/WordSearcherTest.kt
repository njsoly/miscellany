package njsoly.wordsearcher

import njsoly.wordsearcher.WordSearcher.Companion.DEBUG
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import kotlin.text.Regex.Companion.escape
import njsoly.wordsearcher.WordSearcher.Companion.isSimple

@RunWith(MockitoJUnitRunner::class)
class WordSearcherTest {

    val wordSearcher: WordSearcher = WordSearcher()

    private var DEBUG_was: Boolean? = null

    @Before
    fun setup () {
        DEBUG_was = DEBUG
        DEBUG = true
    }

    @After
    fun after () {
        DEBUG = DEBUG_was ?: false
    }

    // TASK MISC-14 create test that ensures that a word doesn't match to itself with wilcards added.  Example: ".....bocks" should not match "bocks".

    @Test
    fun `test getWords`() {
        assertNotNull(wordSearcher.words)
    }

    // TODO MISC-13 test inputloop.
    @Test
    fun `test inputLoop`() { }

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
        assertEquals(
            listOf("WHAT", "WANT"),
            listOf("WHAT", "DO", "YOU", "WANT").filterToLength(4)
        )
    }

    @Test
    fun `filterToLength() with min and max`() {
        assertEquals(listOf("WHAT"), listOf("WHAT").filterToLength(4,4))
    }

    @Test
    fun `getFilename() returns non-null` () {
        assertNotNull(wordSearcher.filename)
    }

    /**
     * @see WordSearcher.isQuitMessage
     * @param input to check if user requested to quit
     * @param expected whether or not that should be seen as a quit message
     */
    fun `isQuitMessage() given (an input) returns (a boolean)` (input: String, expected: Boolean) {
        assertEquals(
                "$input " + if(expected) {"should"} else {"should not"},
                expected,
                wordSearcher.isQuitMessage(input)
        )
    }
}
