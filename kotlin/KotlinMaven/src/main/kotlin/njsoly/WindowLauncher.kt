package njsoly

import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*


class WindowLauncher() : JFrame() {
    constructor(title: String) : this() {
        this.title = title

    }

    var flowLayout: FlowLayout = FlowLayout(FlowLayout.CENTER, 5, 5)

    var borderLayout = BorderLayout(5, 5)

    private var stuff: Map<JComponent, String> = mapOf<JComponent, String> (
        JLabel("I am a window launcher") to BorderLayout.NORTH,
        JButton("click this and live") to BorderLayout.CENTER
    )

    init {
        layout = flowLayout
        this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        SwingUtilities.invokeLater { this.addStuff() }
        SwingUtilities.invokeLater { this.pack() }
    }

    fun addStuff(){
        SwingUtilities.invokeLater {
            when (layout) {
                is BorderLayout -> {
                    stuff.forEach { this.contentPane.add(it.key, it.value) }
                }
                is FlowLayout -> {
                    stuff.forEach { this.contentPane.add(it.key) }
                }
                else -> {
                    stuff.forEach { contentPane.add(it.key) }
                }
            }
        }
        SwingUtilities.invokeLater {
            this.components.forEach {
                it.size = it.preferredSize
            }
        }
        SwingUtilities.invokeLater { this.pack() }
    }
}

fun main(args: Array<String>) {
    val w = WindowLauncher("WindowLauncher")
//    w.addStuff()

    SwingUtilities.invokeLater {
        w.isVisible = true
        w.setLocationRelativeTo(null)
    }

}
