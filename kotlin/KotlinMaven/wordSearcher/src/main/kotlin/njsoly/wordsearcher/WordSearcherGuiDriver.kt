package njsoly.wordsearcher

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class WordSearcherGuiDriver {

    val window: WordSearcherWindow = WordSearcherWindow()
    val form: WordSearcherForm = window.wordSearcherForm

    init {
        form.inputArea
    }

}
