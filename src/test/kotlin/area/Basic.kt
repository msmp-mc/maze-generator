package area

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.anhgelus.msmp.mazegenerator.area.MazeArea
import world.anhgelus.msmp.mazegenerator.utils.Mth
import world.anhgelus.msmp.mazegenerator.utils.SimpleLocation

class Basic {
    private lateinit var mazeArea: MazeArea
    private lateinit var excluded: List<MazeArea>

    @BeforeEach
    fun init() {
        val corners = Mth.Matrix<SimpleLocation>(2,2)
        corners.setContents(mutableListOf(SimpleLocation(-10,10), SimpleLocation(10,10),
            SimpleLocation(-10,-10), SimpleLocation(10,-10)))
        mazeArea = MazeArea(corners)

        corners.setContents(mutableListOf(SimpleLocation(-5,3), SimpleLocation(2,3),
            SimpleLocation(-5,-3), SimpleLocation(2,-3)))
        val excluded1 = MazeArea(corners)

        corners.setContents(mutableListOf(SimpleLocation(-2,3),SimpleLocation(2,3),
            SimpleLocation(-2,-3), SimpleLocation(2,-3)))
        val excluded2 = MazeArea(corners)

        excluded = listOf(excluded1, excluded2)
        mazeArea.addExcludedArea(excluded1)
        mazeArea.addExcludedArea(excluded2)
    }

    @Test
    fun `Basic property test`() {
        Assertions.assertEquals(400, mazeArea.getSurface())
        Assertions.assertEquals(20, mazeArea.n)
        Assertions.assertEquals(20, mazeArea.m)
        Assertions.assertEquals(7,excluded[0].n)
        Assertions.assertEquals(6,excluded[0].m)
        Assertions.assertEquals(4,excluded[1].n)
        Assertions.assertEquals(6, excluded[1].m)
    }

    @Test
    fun `Simplify test`() {
        val areas = mazeArea.getExcludedArea()
        Assertions.assertEquals(1, areas.size)
        Assertions.assertEquals(excluded[1], areas[0])
    }

}