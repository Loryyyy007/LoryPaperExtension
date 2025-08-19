package me.loryyyy.betterArsenal.core.domain.config

import org.bukkit.Location
import org.bukkit.World

data class UnspecifiedWorldLocation(
    val x: Double,
    val y: Double,
    val z: Double,
    val pitch: Float,
    val yaw: Float,
) {

    fun toLocation(world: World?): Location = Location(
        world,
        this.x,
        this.y,
        this.z,
        this.pitch,
        this.yaw,
    )

    fun toMap(): Map<String, Any> {
        return mapOf(
            "x" to x,
            "y" to y,
            "z" to z,
            "pitch" to pitch,
            "yaw" to yaw,
        )
    }

    companion object {
        fun fromLocation(location: Location) = UnspecifiedWorldLocation(
            x = location.x,
            y = location.y,
            z = location.z,
            pitch = location.pitch,
            yaw = location.yaw
        )
    }
}