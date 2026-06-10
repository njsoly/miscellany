package org.njsoly.tinker.leet


/**
 *242. Valid Anagram
 * Solved
 * Easy
 * Topics
 * premium lock icon
 * Companies
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 *
 * https://leetcode.com/problems/valid-anagram/
 *
 *
 * Example 1:
 *
 * Input: s = "anagram", t = "nagaram"
 *
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "rat", t = "car"
 *
 * Output: false
 *
 *
 *
 * Constraints:
 *
 * 1 <= s.length, t.length <= 5 * 10^4
 * s and t consist of lowercase English letters.
 *
 */
class ValidAnagram {
    class Solution {
        fun isAnagram(s: String, t: String): Boolean {
            if (s.length != t.length) return false

            val a = IntArray('z'.code + 1)
            for(i in 0 ..< s.length){
                a[s[i].code]++
                a[t[i].code]--
            }
            for(n in a){
                if (n != 0) {
                    return false
                }
            }
            return true
        }
    }
}

fun main() {
    val testCases = listOf(
        Pair("anagram", "nagaram") to true,
        Pair("rat", "car") to false,
        Pair("a", "a") to true,
        Pair("ab", "ba") to true,
        Pair("abc", "def") to false
    )

    val solution = ValidAnagram.Solution()
    testCases.forEach { (input, expected) ->
        val (s, t) = input
        val result = solution.isAnagram(s, t)
        val status = if (result == expected) "✓" else "✗"
        println("$status Input: s = \"$s\", t = \"$t\"")
        println("  Output: $result, Expected: $expected")
        println()
    }
}
