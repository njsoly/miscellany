fun main(args: Array<String>) {
	printHello()
	printHowManyArgs(args)
	val s = "snth"
	println("reverse of $s is ${rev(s)}")
	var x = '7'
	
}

fun rev(s : String) : String {
	var r = s.toCharArray()
	for(i in 0..(s.length - 1)){
		r[i] = s.get(s.length - 1 - i)
	}
	return String(r)
}
fun printHowManyArgs(args: Array<String>){
	println("there are ${args.size} args.")
}

fun printHello(){
	println("Hello, world!")
}

fun getOnlyPositives(ints : MutableSet<Int?>): MutableSet<Int>{
	return ints.filter{it != null && it > 0}
}