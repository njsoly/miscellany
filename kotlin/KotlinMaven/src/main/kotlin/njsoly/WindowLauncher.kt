package njsoly

import java.awt.*
import javax.swing.*

class Cv : Canvas() {

    override fun paint(g: Graphics?) {
        super.paint(g)
        val g2: Graphics2D = g as Graphics2D
        g2.color = Color.BLUE
        g2.fillRect(10,10,100,100)
        g2.background = Color.BLACK
    }
}

class WindowLauncher() : JFrame() {
    constructor(title: String) : this() {
        this.title = title

        canvas.preferredSize = Dimension(400, 400)
        canvasPanel.add(canvas)
    }

    var flowLayout: FlowLayout = FlowLayout(FlowLayout.CENTER, 5, 5)

    var borderLayout = BorderLayout(5, 5)

    val canvasPanel = JPanel(true)
    private val canvas = Cv()

    private var stuff: Map<JComponent, String> = mapOf<JComponent, String> (
        JLabel("I am a window launcher") to BorderLayout.NORTH,
        JButton("click this and live") to BorderLayout.SOUTH,
        canvasPanel to BorderLayout.CENTER
    )

    init {
//        layout = borderLayout
        layout = flowLayout
        this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        SwingUtilities.invokeLater { setComponentsToPreferred() }
        SwingUtilities.invokeLater {
            canvas.setSize(1280, 720)
            canvasPanel.doLayout()
        }
        SwingUtilities.invokeLater { addStuff() }
        SwingUtilities.invokeLater { pack() }
    }

    fun addStuff(){
        SwingUtilities.invokeLater {
            when (layout) {
                is BorderLayout -> {
                    this.contentPane.layout = this.borderLayout
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
    }

    fun setComponentsToPreferred() {
        this.components.forEach {
            it.size = it.preferredSize
        }
    }
}

fun main(args: Array<String>) {
    val w = WindowLauncher("WindowLauncher")

    SwingUtilities.invokeLater {
        w.isVisible = true

        w.components.plus(w.contentPane.components)
            .plus(w.rootPane.components)
            .plus(w.contentPane)
            .forEachIndexed { i, component ->
            println("component $i: ${component.name} (${component.javaClass.simpleName})")
        }
    }

    SwingUtilities.invokeLater {
        w.pack()
        w.setLocationRelativeTo(null)
    }

}
