package world.anhgelus.msmp.mazegenerator.area

import world.anhgelus.msmp.mazegenerator.utils.Mth
import world.anhgelus.msmp.mazegenerator.utils.SimpleLocation
import kotlin.math.abs

data class MazeExcluded(var corner1: SimpleLocation, var corner2: SimpleLocation) {
    fun toArea(): MazeArea {
        val n = abs(corner1.x - corner2.x)
        val m = abs(corner1.z - corner2.z)
        return MazeArea(n,m, SimpleLocation.DEFAULT_CENTER)
    }

    fun isIncludeIn(i: MazeExcluded): Boolean {
        /*
         * -> (x1a < x1b < x2a) || (x1a < x2b < x2a) || (x1a > x1b > x2a) || (x1a > x2b > x2a)
         * -> && (z1a < z1b < z2a) || (z1a < z2b < z2a) || (z1a > z1b > z2a) || (z1a > z2b > z2a)
         */
        val x1 = i.corner1.x
        val x2 = i.corner2.x
        val z1 = i.corner1.z
        val z2 = i.corner2.z
        val xIncluded = Mth.inIntervalWithoutSens(corner1.x,x1,x2) || Mth.inIntervalWithoutSens(corner2.x,x1,x2)
        val yIncluded = Mth.inIntervalWithoutSens(corner1.z,z1,z2) || Mth.inIntervalWithoutSens(corner2.z,z1,z2)
        return xIncluded && yIncluded
    }
}