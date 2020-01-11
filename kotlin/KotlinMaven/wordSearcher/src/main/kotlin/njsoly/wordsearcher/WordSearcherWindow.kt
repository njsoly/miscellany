package njsoly.wordsearcher

import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

/**
 * A simple container for [WordSearcherForm].
 */
class WordSearcherWindow(title: String = "WordSearcherWindow") : JFrame(title) {

    val wordSearcherForm: WordSearcherForm = WordSearcherForm()

    init {
        this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.contentPane = wordSearcherForm.mainPanel


        addComponentListener(object : ComponentListener {
            override fun componentResized(e: ComponentEvent) {
                println("component resized: " + e.component.name + " " + e.paramString())
                pack()
            }

            override fun componentMoved(e: ComponentEvent) {
                println("component moved: " + e.component.name + " " + e.paramString())
            }

            override fun componentShown(e: ComponentEvent) {
                println("component shown: " + e.component.name + " " + e.paramString())
            }

            override fun componentHidden(e: ComponentEvent) {
                println("component hidden: " + e.component.name + " " + e.paramString())
            }
        })

    }

    fun packAndShow() {
        this.pack()
        this.isVisible = true
    }

}

fun main(args: Array<String>) {
    val w = WordSearcherWindow()
    w.packAndShow()

    SwingUtilities.invokeLater{
        w.packAndShow()
    }
}
