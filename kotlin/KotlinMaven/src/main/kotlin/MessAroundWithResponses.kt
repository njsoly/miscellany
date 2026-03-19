class MessAroundWithResponses (val n: Int = 11) {

    fun sayHello () {
        println("sweet")
    }
}



fun main(args: Array<String>){
    println("Messing around with Resources...")
    MessAroundWithResponses().sayHello()


    println("Now exiting ${MessAroundWithResponses().javaClass.name}")

}
