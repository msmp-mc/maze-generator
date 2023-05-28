package world.anhgelus.msmp.mazegenerator.area

import world.anhgelus.msmp.mazegenerator.utils.SimpleLocation

data class MazeArea(var n: Int,
    var m: Int,
    var center: SimpleLocation) {

    /**
     * Generate the surface of the maze
     */
    fun getSurface(): Int {
        return n * m
    }
}