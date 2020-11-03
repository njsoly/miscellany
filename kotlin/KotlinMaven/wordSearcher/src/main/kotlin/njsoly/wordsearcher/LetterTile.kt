@file:Suppress("KDocMissingDocumentation")

package njsoly.wordsearcher

/**
 *
 */
enum class LetterTile (val points: Int = 1, val vowel: Boolean = false) {
    A(vowel = true),
    B(4),
    C(4),
    D(2),
    E(vowel = true),
    F(4),
    G(3),
    H(3),
    I(vowel = true),
    J(10),
    K(5),
    L(2),
    M(4),
    N(2),
    O(vowel = true),
    P(4),
    Q(10),
    R,
    S,
    T,
    U(2, vowel = true),
    V(5),
    W(4),
    X(8),
    Y(3, vowel = true),
    Z(10);

    companion object {
        fun basicWordValue(s: String): Int {
            return s.toUpperCase().sumBy{ valueOf(it.toString()).points }
        }
    }
}
