package org.njsoly.old.form_creator

import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


fun main() {
    val window : JFrame = JFrame(GridBagFormLaunch::class.java.simpleName)
    window.defaultCloseOperation = EXIT_ON_CLOSE
    window.isVisible = true
    window.add(GridBagFormLaunch().form.mainPanel)
}

/**
 *
 */
class GridBagFormLaunch {

    val form: GridBagFormCreation = GridBagFormCreation()

    init {

    }

}

