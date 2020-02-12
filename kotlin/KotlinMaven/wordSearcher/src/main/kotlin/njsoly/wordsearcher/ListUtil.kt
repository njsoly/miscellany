package njsoly.wordsearcher

class ListUtil

/**
 * Filters a list of [String]s to only those whose lengths
 * fall in the range from [min] to [max], inclusive.
 */
fun List<String>.filterToLength(min: Int, max: Int) : List<String> {
    return this.filter{ it.length in min .. max }
}


// TODO MISC-16 move these List<String> extension functions into util class
/** Filters a list of [String]s to only those whose lengths
 * equal [n]. */
fun List<String>.filterToLength(n: Int) : List<String> {
    return this.filter{ it.length == n }
}

/**
 * Filters a list of [String]s to the contents which match
 * the specified [pattern], with endless wildcards on each end.
 *
 * So, the list is matched against `.*[pattern].*`  .
 */
fun List<String>.filterToPattern(pattern: String) : List<String> {
    return this.filter {
        if(WordSearcher.DEBUG) println("filtering $it to match $pattern")
        it.matches(Regex(pattern.replace(".",".?")))
    }
}
