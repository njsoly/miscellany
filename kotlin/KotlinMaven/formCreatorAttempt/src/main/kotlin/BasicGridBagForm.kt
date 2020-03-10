package njsoly.gui.form_creator

import njsoly.log.SetupLogger
import java.time.LocalDateTime
import java.util.*
import javax.swing.JEditorPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.text.html.HTMLDocument

/**
 * This class will serve as a re-write of GridBagFormCreation.java and .form as one Kotlin class
 * that isn't constructed from an XML file.
 */
class BasicGridBagForm : JPanel() {

    protected var inputTextArea: JTextArea? = null
    protected var resultsTextArea: JTextArea? = null
    private val innerPanel: JPanel? = null
    private val resultsPane: JScrollPane? = null
    private val textAreaInfo: JTextArea? = null
    private val htmlPane: JEditorPane? = null
    protected var htmlDocument: HTMLDocument? = null

    private val LOGGER = SetupLogger.setupLogger(this.javaClass)

    protected val TIMINGS = HashMap<String, Pair<LocalDateTime, LocalDateTime>>()

    val DEBUG = true

    // TODO lots more stuff.

}