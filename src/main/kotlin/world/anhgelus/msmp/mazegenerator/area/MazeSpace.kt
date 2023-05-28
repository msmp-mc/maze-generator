package world.anhgelus.msmp.mazegenerator.area

import world.anhgelus.msmp.mazegenerator.utils.Mth
import world.anhgelus.msmp.mazegenerator.utils.SimpleLocation
import kotlin.math.abs

class MazeSpace(private var corner1: SimpleLocation, private var corner2: SimpleLocation) {
    private val excluded: MutableList<MazeExcluded> = ArrayList()
    private val area = MazeArea(0,0, SimpleLocation.DEFAULT_CENTER)

    /**
     * Add an excluded part
     */
    fun addExcludedPart(ex: MazeExcluded) {
        excluded.add(ex)
        simplifyExcluded()
    }

    /**
     * Generate the area without caring about the excluded part
     */
    fun generateArea(): MazeArea {
        if (!(area.n == 0 || area.m == 0)) {
            return area
        }
        area.n = abs(corner1.x - corner2.x)
        area.m = abs(corner1.z - corner2.z)
        return area
    }

    /**
     * Generate the surface of the maze
     */
    fun getSurface(): Int {
        val area = generateArea()
        return area.getSurface() //TODO: remove the surface of excluded part
    }
    
    fun getExcluded(): List<MazeExcluded> {
        return excluded
    }

    /**
     * Simplify the excluded part
     */
    private fun simplifyExcluded() {
        //TODO: finish this part
        return
        /*
         * for each excluded part (a)
         *      check if the excluded part contains a part (b) include in other excluded part
         *      -> (x1a < x1b < x2a) || (x1a < x2b < x2a) || (x1a > x1b > x2a) || (x1a > x2b > x2a)
         *      -> && (z1a < z1b < z2a) || (z1a < z2b < z2a) || (z1a > z1b > z2a) || (z1a > z2b > z2a)
         *      if not continue
         *      A := biggest excluded part between a and b ; if a is as big as b => A = a
         *      B := the other part
         *      sA := area of A
         *      sB := area of B
         *      get the corner.s of B inside A
         *      -> if (x1a < x1b < x2a) || (x1a < x2b < x2a)
         */
        excluded.forEach { a: MazeExcluded ->
            for (b in excluded) {
                if (a == b) {
                    continue
                }
                if (!a.isIncludeIn(b)) {
                    continue
                }
                val sa = a.toArea().getSurface()
                val sb = b.toArea().getSurface()
                val m = if (sa >= sb) a else b
                val nm = if (sa >= sb) b else a

                val x1a = m.corner1.x
                val x2a = m.corner2.x

                val border1 = SimpleLocation.Mutable(0,0)

                if (Mth.inIntervalWithoutSens(nm.corner1.x, m.corner1.x, m.corner2.x)) {
                    border1.x = nm.corner1.x
                } else {
                    border1.x = nm.corner2.x
                }
            }
        }
    }
}