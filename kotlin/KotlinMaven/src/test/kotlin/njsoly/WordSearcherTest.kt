package njsoly

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
    }

    @Test
    fun `filterToLength() with exact length does it right` () {
        TODO()
    }

    @Test
    fun `filterToLength() with min and max`() {
        TODO()
    }

    @Test
    fun `getFilename() returns non-null` () {
        assertNotNull(wordSearcher.filename)
    }
}
