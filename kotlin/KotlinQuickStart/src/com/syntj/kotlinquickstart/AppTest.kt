package com.syntj.kotlinquickstart
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AppTest {

	var x: Int = 0

    @Mock
    var f: Fish

	@Test
	fun yourTest() {
		assertTrue(true)

	}
}
