package njsoly.util.strings

import org.junit.Assert.*
import org.junit.Test

class ListUtilKtTest{
    
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
}
