package area

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.anhgelus.msmp.mazegenerator.area.MazeArea
import world.anhgelus.msmp.mazegenerator.utils.Mth
import world.anhgelus.msmp.mazegenerator.utils.SimpleLocation

class Simplifier {
    private lateinit var mazeArea: MazeArea
    private lateinit var excluded: List<MazeArea>

    @BeforeEach
    fun init() {
        val corners = Mth.Matrix<SimpleLocation>(2,2)
        corners.setContents(mutableListOf(
            SimpleLocation(-10,10), SimpleLocation(10,10),
            SimpleLocation(-10,-10), SimpleLocation(10,-10)
        ))
        mazeArea = MazeArea(corners)

        val cornersE = Mth.Matrix<SimpleLocation>(2,2)
        cornersE.setContents(mutableListOf(
            SimpleLocation(-5,7), SimpleLocation(3,7),
            SimpleLocation(-5,-4), SimpleLocation(3,-4),
        ))
        val excluded1 = MazeArea(cornersE)

        val cornersE2 = Mth.Matrix<SimpleLocation>(2,2)
        cornersE2.setContents(mutableListOf(
            SimpleLocation(-5,3), SimpleLocation(3,3),
            SimpleLocation(-5,-8), SimpleLocation(3,-8),
        ))
        val excluded2 = MazeArea(cornersE2)

        excluded = mutableListOf(excluded1, excluded2)
        mazeArea.addExcludedArea(excluded1)
        mazeArea.addExcludedArea(excluded2)
    }

    @Test
    fun `Hard simplification test`() {
        val areas = mazeArea.getExcludedArea()
        areas.forEach {
            println("Has the corner ${it.corners[1,1].x};${it.corners[1,1].z} and has the surface ${it.getSurface()}")
        }
        val cond = excluded[0].getSurface() >= excluded[1].getSurface()
        val big = if (cond) excluded[0] else excluded[1]
        val small = if (cond) excluded[1] else excluded[0]
        println("Big has the corner ${big.corners[1,1].x};${big.corners[1,1].z}")
        println("Small has the corner ${small.corners[1,1].x};${small.corners[1,1].z}")
        Assertions.assertTrue(areas[0].getSurface() == big.getSurface())
        Assertions.assertFalse(areas[1].getSurface() == small.getSurface())
    }
}