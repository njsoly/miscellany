import njsoly.util.ColorUtil.*
import java.awt.Color
import java.awt.Font
import java.awt.Insets
import java.awt.event.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Timer
import javax.swing.*
import javax.swing.border.BevelBorder
import javax.swing.border.TitledBorder
import javax.swing.text.JTextComponent
import javax.swing.text.html.HTMLDocument
import javax.swing.text.html.StyleSheet

open class GridBagFormCreation {
    var mainPanel: JPanel? = null
    open var inputTextArea: JTextArea? = null
    var resultsTextArea: JTextArea? = null
    private val innerPanel: JPanel? = null
    private val resultsPane: JScrollPane? = null
    val infoTextArea: JTextArea? = null
    private var htmlPane: JEditorPane? = null
    protected var htmlDocument: HTMLDocument

    companion object {
        protected val TIMINGS =
            HashMap<String, Pair<LocalDateTime, LocalDateTime>>()
        const val DEBUG = true
        fun theTime(): String {
            val timeWithNanos =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.n"))
            return timeWithNanos.substring(0, timeWithNanos.length - 6)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val form = GridBagFormCreation()
            val window = form.gridBagFormCreationJframe
            SwingUtilities.invokeLater {
                window.pack()
                window.isVisible = true
            }
        }

        init {
            if (DEBUG) println("GridBagFormCreation class loaded: " + theTime())
        }
    }

    protected fun addStandardKeyListeners() {
        if (DEBUG) println("GridBagFormCreation(): adding key listener " + theTime())
        inputTextArea!!.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent) {
                super.keyTyped(e)
            }
        })
        inputTextArea!!.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                super.keyPressed(e)
                if (e.isActionKey) {
                    println("e (" + e.keyCode + ") is an action key.")
                } else {
                    println("e (" + e.keyCode + ") is NOT an action key.")
                }
                if (e.keyCode == KeyEvent.VK_ENTER) {
                    println("pressed ENTER.")
                }
            }
        })
        if (DEBUG) println("gridBagFormCreation(): added key listener")
    }

    fun setBgFg(jc: JComponent?, bg: Color?, fg: Color?) {
        SwingUtilities.invokeLater {
            println("setting BG & FG (" + jc!!.name + "): END " + theTime())
            jc.foreground = fg
            jc.background = bg
        }
    }

    protected val htmlPaneStylesheet: StyleSheet
        protected get() = htmlDocument.styleSheet

    fun addCssRuleToHtmlPane(cssRule: String?) {
        htmlDocument.styleSheet.addRule(cssRule)
    }

    fun setSelectionBackground(c: Color?, vararg jtcz: JTextComponent) {
        SwingUtilities.invokeLater {
            for (jtc in jtcz) {
                jtc.selectionColor = c
            }
        }
    }

    /**
     * Given a list of [JTextComponent]s, change each of their margins to `n`.
     * @param n
     * @param jtcz
     */
    protected fun setMargins(n: Int, vararg jtcz: JTextComponent) {
        SwingUtilities.invokeLater {
            for (jtc in jtcz) {
                SwingUtilities.invokeLater { jtc.margin = Insets(n, n, n, n) }
            }
        }
    }

    protected fun doStandardComponentBorder(vararg jcz: JComponent) {
        SwingUtilities.invokeLater {
            if (DEBUG) println("doStandardComponentBorder(" + jcz.javaClass.simpleName + "): " + theTime())
            for (jc in jcz) {
                jc.border = BorderFactory.createLineBorder(BETTER_MAGENTA_PINK, 2, true)
            }
        }
    }

    private fun isVisibleLoop(window: JFrame, maxTimes: Int) {
        val t: Timer = object : Timer() {
            var times = 0
            var max = maxTimes
        }
        t.scheduleAtFixedRate(object : TimerTask() {
            var times = 0
            val max = maxTimes
            override fun run() {
                println("Window is visible [" + times + "]: " + window.isVisible)
                times++
                if (times > maxTimes) {
                    cancel()
                    if (!window.isVisible) {
                        System.exit(1)
                    }
                }
                if (window.isVisible) {
                    cancel()
                }
            }
        }, 0, 2000)
    }

    val gridBagFormCreationJframe: JFrame
        get() {
            val frame: JFrame = object : JFrame("GridBagFormCreation") {
                override fun setVisible(visible: Boolean) {
                    if (DEBUG) println("setting window visible: " + theTime())
                    super.setVisible(visible)
                    if (DEBUG) println("window is visible " + visible + ": " + theTime())
                }

                override fun pack() {
                    if (DEBUG) println("packing window: " + theTime())
                    super.pack()
                    if (DEBUG) println("packed window: " + theTime())
                }

                init {
                    addContainerListener(object : ContainerListener {
                        override fun componentAdded(e: ContainerEvent) {
                            if (DEBUG) println("componentAdded: $e")
                        }

                        override fun componentRemoved(e: ContainerEvent) {
                            if (DEBUG) println("componentRemoved: $e")
                        }
                    })
                    addComponentListener(object : ComponentListener {
                        override fun componentResized(e: ComponentEvent) {
                            println("component resized: " + e.component.name + " " + e.paramString())
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
            }
            isVisibleLoop(frame, 20)
            frame.contentPane = GridBagFormCreation().mainPanel
            frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            frame.pack()
            return frame
        }

    fun createUIComponents() {
        if (DEBUG) println("createUIComponents() " + theTime())
        htmlPane = JEditorPane("text/html", "<div><b>aoeu</b> snth</div> <hr /> <h3>aoeusntahoeusnth</h3>")
    }

    init {
        if (DEBUG) println("GridBagFormCreation() " + theTime())
        addStandardKeyListeners()
        SwingUtilities.invokeLater {
            mainPanel!!.border = BorderFactory.createLineBorder(DEEP_GREEN_BLUE, 2, true)
            resultsPane!!.background = DEEP_GREEN_BLUE // med cobalt blue
            resultsPane.foreground = SOFTER_CYAN // bright-ish cyan
            resultsPane.border = BorderFactory.createTitledBorder(
                BorderFactory.createBevelBorder(
                    BevelBorder.RAISED,
                    Color.CYAN.brighter(),
                    Color.CYAN.darker()
                ),
                "Results",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                Font("Consolas", Font.BOLD, 16),
                Color(65, 255, 133).brighter() // bright green blue
            )
            resultsTextArea!!.disabledTextColor = SOFTER_SEAFOAM
            resultsTextArea!!.border = BorderFactory.createLineBorder(SKY_BLUE_3, 1)
            inputTextArea!!.border = BorderFactory.createLineBorder(BRIGHT_SEAFOAM) // bright seafoam
            infoTextArea!!.background = Color.BLACK
        }
        setBgFg(resultsTextArea, Color.BLACK, SOFTER_SEAFOAM)
        setBgFg(inputTextArea, DEEP_BLUE, Color.WHITE)
        setBgFg(htmlPane, DEEP_GREEN_BLUE, SOFTER_CYAN)
        htmlPane!!.disabledTextColor = Color.GRAY
        htmlPane!!.selectedTextColor = Color.WHITE
        htmlDocument = htmlPane!!.document as HTMLDocument
        htmlDocument.styleSheet.addRule("html body { color: #FFFFFF }")
        doStandardComponentBorder(innerPanel!!, infoTextArea!!, htmlPane!!)
        setMargins(3, infoTextArea, inputTextArea!!, resultsTextArea!!)
        setSelectionBackground(BETTER_PURPLE, resultsTextArea!!, inputTextArea!!, htmlPane!!, infoTextArea)
        for (jc in arrayOf<JComponent?>(resultsTextArea, inputTextArea, infoTextArea, htmlPane)) {
            jc!!.font = Font("Consolas", Font.PLAIN, 14)
        }
        println("GridBagFormCreation(): END " + theTime())
    }
}
