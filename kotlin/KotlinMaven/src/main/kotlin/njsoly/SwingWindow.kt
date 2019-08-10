package njsoly

import javax.swing.JFrame

class SwingWindow () : JFrame() {

    constructor(title: String) : this() {
        this.title = title
    }
    fun launch() {
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setBounds(200, 100, 1000, 500)
        this.isVisible = true
    }
}

fun main (){
    val sw = SwingWindow("SwingWindow")
    sw.launch()


}
