package world.anhgelus.msmp.mazegenerator.utils

object Mth {
    fun inIntervalWithoutSens(n: Int, a: Int, b: Int): Boolean {
        return n in a..b || n in b..a
    }

    class Matrix<T>(val n: Int, val m: Int) {
        private val contents = mutableListOf<T>()
        operator fun set(i: Int, j: Int, v: T) {
            contents[calcIndex(i,j)] = v
        }
        operator fun get(i: Int, j: Int): T {
            return contents[calcIndex(i,j)]
        }

        fun calcIndex(i: Int, j: Int): Int {
            return n*j+i
        }
    }
}