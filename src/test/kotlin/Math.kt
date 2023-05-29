import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import world.anhgelus.msmp.mazegenerator.utils.Mth

class Math {
    @Test
    fun `Matrix test`() {
        val matrix = Mth.Matrix<Int>(2,2)
        matrix[1,1] = 1
        matrix[1,2] = 3
        matrix[2,1] = 8
        matrix[2,2] = 2

        Assertions.assertEquals(matrix[1,1], 1)
        Assertions.assertEquals(matrix[1,2], 3)
        Assertions.assertEquals(matrix[2,1], 8)
        Assertions.assertEquals(matrix[2,2], 2)

        Assertions.assertEquals(matrix.calcIndex(1,1), 1)
        Assertions.assertEquals(matrix.calcIndex(1,2), 2)
        Assertions.assertEquals(matrix.calcIndex(2,1), 3)
        Assertions.assertEquals(matrix.calcIndex(2,2), 4)

        Assertions.assertEquals(matrix.getIdFromValue(8),3)

        val ij = Mth.Matrix<Int>(2,1)
        ij[1,1] = 2
        ij[2,1] = 1
        Assertions.assertEquals(matrix.getIJFromValue(8),ij)
    }
}