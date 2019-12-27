package njsoly.wordsearcher

import GridBagFormCreation
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.JTextArea

/**
 * An extension of [GridBagFormCreation] that serves as an
 * interface for [WordSearcher].
 */
class WordSearcherForm : GridBagFormCreation() {

    val ws: WordSearcher = WordSearcher()

    override fun getInputTextArea() : JTextArea{
        return this.inputTextArea
    }

    class KA(val componentSource: JComponent) : KeyAdapter() {

        @Override
        override fun keyPressed(e: KeyEvent?) {
            super.keyPressed(e)
            if(e?.keyCode == KeyEvent.VK_UP){
                if(componentSource is JTextArea){
                    if(componentSource.caretPosition == 0){

                    }
                }
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

    init {
        this.infoTextArea.text = "WORD SEARCHER!"
        this.inputTextArea.addKeyListener(KA(this.infoTextArea))
    }

    fun processInput(input: String) : List<String> {
        val results = ws.processInput(input)



        return results!!
    }
}
