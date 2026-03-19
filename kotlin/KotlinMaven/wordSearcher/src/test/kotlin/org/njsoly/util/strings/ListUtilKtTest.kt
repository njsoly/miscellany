package njsoly.util.strings

import org.junit.Assert.*
import org.junit.Test

class ListUtilKtTest{
    
    @Test
    fun `filterToPattern works` () {
        val pattern = "..A.."

        assertEquals(listOf("BLACK"),
                listOf("BLACK").filterToPattern(pattern)
        )
    }

    @Test
    fun `filterToLength() with exact length does it right` () {
        assertEquals(
                listOf("WHAT", "WANT"),
                listOf("WHAT", "DO", "YOU", "WANT").filterToLength(4)
        )
    }

    @Test
    fun `filterToLength() with min and max on one fitting word returns that word`() {
        assertEquals(
                listOf("WHAT"),
                listOf("WHAT").filterToLength(4, 4)
        )
    }

    @Test
    fun `filterToLength() with min and max and one fit and one unfit words returns fit`() {
        assertEquals(
                listOf("ANIMAL"),
                listOf("ANIMAL","MAN").filterToLength(4, 7)
        )
    }
}
