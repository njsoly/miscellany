package njsoly.util.strings

/**
 * Filters a list of [String]s to only those whose lengths
 * fall in the range from [min] to [max], inclusive.
 */
fun List<String>.filterToLength(min: Int, max: Int) : List<String> {
    return this.filter{ it.length in min .. max }
}

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
        it.matches(Regex(pattern.replace(".",".?")))
    }
}