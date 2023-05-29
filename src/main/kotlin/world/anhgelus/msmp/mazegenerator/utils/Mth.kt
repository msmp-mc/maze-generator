package world.anhgelus.msmp.mazegenerator.utils

object Mth {
    fun inIntervalWithoutSens(n: Int, a: Int, b: Int): Boolean {
        return n in a..b || n in b..a
    }

    class Matrix<T>(val n: Int, val m: Int) {
        private var contents = mutableListOf<T>()

        operator fun set(i: Int, j: Int, v: T) {
            contents.add(calcIndex(i,j), v)
        }
        operator fun get(i: Int, j: Int): T {
            return contents[calcIndex(i,j)]
        }

        fun calcIndex(i: Int, j: Int): Int {
            return n*(i-1)+j-1
        }
        fun getContents(): MutableList<T> {
            return contents
        }
        fun setContents(l: MutableList<T>) {
            if (l.size > m*n) {
                throw IllegalArgumentException("The list is too big for the matrix")
            }
            contents = l
        }

        /**
         * Calc I and J from the id
         */
        fun calcIJ(id: Int): Matrix<Int> {
            val matrix = Matrix<Int>(2,1)
            val i = ((id-id%m)/m)+1
            val j = id-(i-1)*m+1
            matrix.setContents(mutableListOf(i,j))
            return matrix
        }

        /**
         * Get the id from a value
         * @return the id or -1 if the value is not in the matrix
         */
        fun getIdFromValue(v: T): Int {
            if (!contents.contains(v)) return -1
            for (i in 0 until contents.size) {
                if (v==contents[i]) return i
            }
            return -1
        }

        /**
         * Get I and J from a value
         * @return the IJ or null if the value is not in the matrix
         */
        fun getIJFromValue(v: T): Matrix<Int>? {
            val id = getIdFromValue(v)
            if (id == -1) return null
            return calcIJ(id)
        }
    }
}