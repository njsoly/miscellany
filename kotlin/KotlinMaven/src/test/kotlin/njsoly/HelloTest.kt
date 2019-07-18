package njsoly

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

import org.mockito.runners.MockitoJUnitRunner
import kotlin.test.assertEquals

/**
 * I had to do some weird shit with the Maven tab in IntelliJ to make this work correctly.
 */
@RunWith(MockitoJUnitRunner::class)
class HelloTest {

    @MockitoAnnotations.Mock
    lateinit var a: MutableList<Int>

    @Before
    fun setup () {
        a = ArrayList<Int>()
        a.add(-1)

    }

    @Test
    fun `what do you want` () {
        assertEquals(a[0], (-1))
    }
}
