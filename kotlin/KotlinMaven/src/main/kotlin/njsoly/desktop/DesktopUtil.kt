package njsoly.desktop

import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment

class DesktopUtil {

    val gd: GraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice

    fun main() {

        val bounds = gd.defaultConfiguration.bounds
        println("bounds: $bounds")
    }
}
