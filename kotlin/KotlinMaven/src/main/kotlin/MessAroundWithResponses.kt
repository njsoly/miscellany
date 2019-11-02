class MessAroundWithResponses (val n: Int = 11) {

    fun `say hello` () {
        println("sweet")
    }
}



fun main(args: Array<String>){
    kotlin.io.println("Messing around with Resources...")
    MessAroundWithResponses().`say hello`()


    println("Now exiting ${MessAroundWithResponses().javaClass.name}")

}
