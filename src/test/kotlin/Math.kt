import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import world.anhgelus.msmp.mazegenerator.utils.Mth

class Math {
    @Test
    fun `Matrix test`() {
        val matrix = Mth.Matrix<Int>(2,2)
        matrix.setContents(mutableListOf(1,4,8,2))

        Assertions.assertEquals(1, matrix[1,1])
        Assertions.assertEquals(4, matrix[1,2])
        Assertions.assertEquals(8,matrix[2,1])
        Assertions.assertEquals(2, matrix[2,2])

        Assertions.assertEquals(0,matrix.calcIndex(1,1))
        Assertions.assertEquals(1, matrix.calcIndex(1,2))
        Assertions.assertEquals(2, matrix.calcIndex(2,1))
        Assertions.assertEquals(3, matrix.calcIndex(2,2))

        Assertions.assertEquals(2,matrix.getIdFromValue(8))

        val ij = Mth.Matrix<Int>(2,1)
        ij.setContents(mutableListOf(2,1))
        val got = matrix.getIJFromValue(8)!!
        Assertions.assertEquals(ij[1,1], got[1,1])
        Assertions.assertEquals(ij[1,2], got[1,2])
    }
}