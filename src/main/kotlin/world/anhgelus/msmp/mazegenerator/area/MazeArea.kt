package world.anhgelus.msmp.mazegenerator.area

import world.anhgelus.msmp.mazegenerator.utils.Mth
import world.anhgelus.msmp.mazegenerator.utils.SimpleLocation

class MazeArea(val corners: Mth.Matrix<SimpleLocation>) {
    val n: Int
    val m: Int
    var center = DEFAULT_LOCATION

    init {
        if (isAreaValid()) {
            throw IllegalArgumentException("Invalid area")
        }
        val leftTop = corners[1,1]
        val rightBottom = corners[2,2]
        n = kotlin.math.abs(leftTop.x - rightBottom.x)
        m = kotlin.math.abs(leftTop.z - rightBottom.z)
    }

    constructor(corners: Mth.Matrix<SimpleLocation>, center: SimpleLocation) : this(corners) {
        this.center = center
    }

    private fun isAreaValid(): Boolean {
        return corners.n != 2 && corners.m != 2
    }

    companion object {
        fun fromLeftTopAndRightBottom(leftTop: SimpleLocation, rightBottom: SimpleLocation): MazeArea {
            val rightTop = SimpleLocation(leftTop.x-rightBottom.x,leftTop.z)
            val leftBottom = SimpleLocation(rightBottom.x,leftTop.z-rightBottom.z)
            val matrix = Mth.Matrix<SimpleLocation>(2,2)
            matrix[1,1] = leftTop
            matrix[1,2] = rightTop
            matrix[2,1] = leftBottom
            matrix[2,2] = rightBottom
            return MazeArea(matrix)
        }

        val DEFAULT_LOCATION = SimpleLocation(0,0)
    }
}
