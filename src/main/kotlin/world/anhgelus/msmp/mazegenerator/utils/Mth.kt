package world.anhgelus.msmp.mazegenerator.utils

object Mth {
    fun inIntervalWithoutSens(n: Int, a: Int, b: Int): Boolean {
        return n in a..b || n in b..a
    }
}