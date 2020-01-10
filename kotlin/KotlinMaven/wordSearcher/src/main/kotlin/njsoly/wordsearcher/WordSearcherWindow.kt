package njsoly.wordsearcher

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
