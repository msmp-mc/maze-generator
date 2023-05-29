package world.anhgelus.msmp.mazegenerator.area

import world.anhgelus.msmp.mazegenerator.utils.Mth
import world.anhgelus.msmp.mazegenerator.utils.SimpleLocation

class MazeArea(val corners: Mth.Matrix<SimpleLocation>) {
    val n: Int
    val m: Int
    var center = DEFAULT_LOCATION
    private val excluded = mutableListOf<MazeArea>()

    init {
        if (isInitValid()) {
            throw IllegalArgumentException("Invalid area")
        }
        val leftTop = corners[1,1]
        val rightBottom = corners[2,2]
        n = kotlin.math.abs(leftTop.x) + kotlin.math.abs(rightBottom.x)
        m = kotlin.math.abs(leftTop.z) + kotlin.math.abs(rightBottom.z)
    }

    constructor(corners: Mth.Matrix<SimpleLocation>, center: SimpleLocation) : this(corners) {
        this.center = center
    }

    fun getExcludedArea(): List<MazeArea> {
        return excluded
    }

    fun addExcludedArea(area: MazeArea) {
        excluded.add(area)
        simplifyExcluded()
    }

    fun getXInterval(): IntRange {
        return center.x - n/2 .. center.x + n/2
    }

    fun getZInterval(): IntRange {
        return center.z - m/2 .. center.z + m/2
    }

    fun getSurface(): Int {
        return n*m
    }

    private fun isInitValid(): Boolean {
        return corners.n != 2 && corners.m != 2
    }

    /**
     * Simplify the excluded areas
     */
    private fun simplifyExcluded() {
        for (a in excluded) {
            for (b in excluded) {
                if (a == b || !a.hasAPartIncludeIn(b)) {
                    continue
                }
                val cond = a.getSurface() >= b.getSurface()
                val big = if (cond) a else b
                val small = if (cond) b else a
                val cornersInside = mutableListOf<SimpleLocation>()
                val cornersOutside = mutableListOf<SimpleLocation>()
                if (big.isLocationIncludeIn(small.corners[1,1])) cornersInside.add(small.corners[1,1]) else cornersOutside.add(small.corners[1,1])
                if (big.isLocationIncludeIn(small.corners[1,2])) cornersInside.add(small.corners[1,2]) else cornersOutside.add(small.corners[1,2])
                if (big.isLocationIncludeIn(small.corners[2,1])) cornersInside.add(small.corners[2,1]) else cornersOutside.add(small.corners[2,1])
                if (big.isLocationIncludeIn(small.corners[2,2])) cornersInside.add(small.corners[2,2]) else cornersOutside.add(small.corners[2,2])

                if (cornersInside.size == 4) {
                    // The small is completely include in big
                    excluded.remove(small)
                    break
                }
                // not possible
                if (cornersInside.size == 3) throw ArithmeticException("Only 3 corners of the small is inside the big area")
                if (cornersInside.size == 2) {
                    // if two corners are in the big
                    excluded.remove(small)
                    val c1 = cornersInside[0]
                    val c1coords = small.corners.getIJFromValue(c1)!!
                    val mut1 = c1.toMutable()
                    val c2 = cornersInside[1]
                    val c2coords = small.corners.getIJFromValue(c2)!!
                    val mut2 = c2.toMutable()
                    val c3 = cornersOutside[0]
                    val c3coords = small.corners.getIJFromValue(c3)!!
                    val c4 = cornersOutside[1]
                    val c4coords = small.corners.getIJFromValue(c4)!!
                    val corners = Mth.Matrix<SimpleLocation>(2,2)
                    var x = 0
                    var z = 0
                    val j = c1coords[1,2]
                    val i = c1coords[1,1]
                    // handle every position of the first corner
                    if (c1.x == c2.x) {
                        // we must move the x
                        x = if (j == 1) {
                            // c1 is left
                            c1.x - big.corners[i,2].x
                        } else {
                            // c1 is not left
                            c1.x - big.corners[i,1].x
                        }
                        mut1.x = x
                        mut2.x = x
                    } else {
                        // we must move the z
                        z = if (i == 1) {
                            // c1 is top
                            c1.z - big.corners[2,j].z
                        } else {
                            // c1 is not top
                            c1.z - big.corners[1,j].z
                        }
                        mut1.z = z
                        mut2.z = z
                    }

                    corners[i,j] = mut1.toSimpleLocation()
                    corners[c2coords[1,1],c2coords[1,2]] = mut2.toSimpleLocation()
                    corners[c3coords[1,1],c3coords[1,2]] = c3
                    corners[c4coords[1,1],c4coords[1,2]] = c4
                    excluded.add(MazeArea(corners))
                }
            }
        }
    }

    /**
     * Check if an area as a part include in other area
     */
    fun hasAPartIncludeIn(area: MazeArea): Boolean {
        var valid = false
        for (i in getXInterval()) {
            if (i in area.getXInterval()) {
                valid = true
                break
            }
        }
        if (!valid) {
            return false
        }
        valid = false
        for (i in getZInterval()) {
            if (i in area.getZInterval()) {
                valid = true
                break
            }
        }
        return valid
    }

    fun isLocationIncludeIn(loc: SimpleLocation): Boolean {
        return loc.x in getXInterval() && loc.z in getZInterval()
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
