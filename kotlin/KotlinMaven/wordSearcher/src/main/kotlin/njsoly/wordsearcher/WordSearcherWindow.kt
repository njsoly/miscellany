package njsoly.wordsearcher

import javax.swing.JFrame
import javax.swing.WindowConstants

class WordSearcherWindow(title: String = "WordSearcherWindow") : JFrame(title) {

    private val wordSearcherForm: WordSearcherForm = WordSearcherForm()

    init {
        this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.contentPane = wordSearcherForm.mainPanel
    }

    fun packAndShow() {
        this.pack()
        this.show()
    }

}

/**
 *
 */
fun main(args: Array<String>) {
    WordSearcherWindow().packAndShow()
}
