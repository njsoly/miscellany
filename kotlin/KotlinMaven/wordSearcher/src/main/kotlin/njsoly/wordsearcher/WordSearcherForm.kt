package njsoly.wordsearcher

import GridBagFormCreation
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.JTextArea
import javax.swing.SwingUtilities

/**
 * An extension of [GridBagFormCreation] that serves as an
 * interface for [WordSearcher].
 */
class WordSearcherForm : GridBagFormCreation {

    constructor() : super() {
        SwingUtilities.invokeLater {
            this.infoTextArea.text = "WORD SEARCHER!"
            this.resultsTextArea.text = ""
            this.inputTextArea.addKeyListener(KA(this.inputTextArea, this, this.ws))
        }
    }

    val ws: WordSearcher = WordSearcher()

    override fun getInputTextArea() : JTextArea{
        return this.inputTextArea
    }

    fun getWordSearcher(): WordSearcher {
        return ws
    }

    class KA(private val componentSource: JComponent,
             private val wsf: WordSearcherForm,
             private val ws: WordSearcher) : KeyAdapter() {

        @Override
        override fun keyPressed(e: KeyEvent?) {
            super.keyPressed(e)
            if(e?.keyCode == KeyEvent.VK_UP){
                if(componentSource is JTextArea){
                    if(componentSource.caretPosition == 0){
                        componentSource.text = ws.getLastInput()
                    }
                }
            }
            else if (e?.keyCode == KeyEvent.VK_ENTER){
                wsf.resultsTextArea.text += '\n' + wsf.processInput(wsf.inputTextArea.text).toString()
                wsf.inputTextArea.text = ""
            }
        }

        @Override
        override fun keyTyped(e: KeyEvent?) {
            super.keyTyped(e)
        }

        @Override
        override fun keyReleased(e: KeyEvent?) {
            super.keyReleased(e)
        }
    }

    fun processInput(input: String) : List<String> {
        val results = ws.processInput(input)

        resultsTextArea.text += '\n' + results.toString()

        return results!!
    }
}
