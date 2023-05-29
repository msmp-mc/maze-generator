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
            return n*(j-1)+i
        }
        fun getContents(): MutableList<T> {
            return contents
        }

        /**
         * Calc I and J from the id
         */
        fun calcIJ(id: Int): Matrix<Int> {
            val matrix = Matrix<Int>(2,1)
            /*
             * id = (j-1)n+i <=> 0 = (j-1)n+i-id <=> -i = (j-1)n-id = <=> i = id-(j-1)n
             */
            val j = ((id-id%m)/m)+1
            val i = id-(j-1)*m
            matrix[1,1] = i
            matrix[2,1] = j
            return matrix
        }

        /**
         * Get the id from a value
         * @return the id or -1 if the value is not in the matrix
         */
        fun getIdFromValue(v: T): Int {
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