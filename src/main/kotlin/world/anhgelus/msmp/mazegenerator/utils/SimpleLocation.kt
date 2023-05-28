package world.anhgelus.msmp.mazegenerator.utils

import org.bukkit.Location
import org.bukkit.World

data class SimpleLocation(val x: Int, val z: Int) {
    fun toLocation(world: World, y: Int): Location {
        return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    }
    fun toLocation(world: World): Location {
        return Location(world, x.toDouble(), .0, z.toDouble())
    }
    fun toMutable(): Mutable {
        return Mutable(x,z)
    }

    companion object {
        var DEFAULT_CENTER = SimpleLocation(0,0)

        fun fromLocation(location: Location): SimpleLocation {
            return SimpleLocation(location.x.toInt(), location.z.toInt())
        }
    }

    data class Mutable(var x: Int, var z: Int) {
        fun toSimpleLocation(): SimpleLocation {
            return SimpleLocation(x,z)
        }
    }
}
