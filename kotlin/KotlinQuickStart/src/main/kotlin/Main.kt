fun main(args: Array<String>) {
	printHello()
	printHowManyArgs(args)
}

fun printHowManyArgs(args: Array<String>){
	println("there are ${args.size} args.")
}

fun printHello(){
	println("Hello, world!")
}