package world.anhgelus.msmp.mazegenerator

import world.anhgelus.msmp.mazegenerator.area.MazePatterns

class MazeGenerator(val n: Int,val m: Int) {
    lateinit var patterns: MazePatterns
        private set
    var walls: List<Int>? = null
        private set

    fun generate() {
        //val maze = Maze(n,m)
        //maze.setPatterns(patterns)
        //walls = maze.getWallPosition()
    }
    fun setPatterns(p: MazePatterns) {
        if (!p.isDataValid()) throw IllegalArgumentException("The maze patterns is not valid")
        patterns = p
    }
}