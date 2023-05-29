package world.anhgelus.msmp.mazegenerator.area

import org.bukkit.Material
import world.anhgelus.msmp.mazegenerator.utils.Mth

class MazePatterns(val floorPattern: FloorPattern, val size: MazePatternSize) {
    data class FloorPattern(val pattern: Mth.Matrix<Material>)
    data class MazePatternSize(val wall: WallSize, val floor: FloorSize) {
        data class WallSize(val length: Int, val thickness: Int)
        data class FloorSize(val length: Int, val thickness: Int)
    }

    /**
     * Check if the data entered is valid
     */
    fun isDataValid(): Boolean {
        val floor = floorPattern.pattern.n != size.floor.length && floorPattern.pattern.m != size.floor.thickness
        val wall = size.wall.length % size.floor.length == 0 || size.floor.length % size.wall.length == 0
        return floor && wall
    }
}