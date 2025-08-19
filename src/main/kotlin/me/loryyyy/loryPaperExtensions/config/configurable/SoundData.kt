package me.loryyyy.betterArsenal.core.domain.config

import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.Sound
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

data class SoundData(
    val enabled: Boolean,
    val sound: Sound,
    val volume: Float,
    val pitch: Float
) {

    enum class Path(val path: String) {
        ENABLED("enabled"),
        SOUND("sound"),
        VOLUME("volume"),
        PITCH("pitch");
    }

    fun playIfEnabled(listener: Player) {
        if (!enabled) return
        listener.playSound(listener.location, sound, volume, pitch)
    }

    companion object {
        fun of(section: ConfigurationSection): SoundData {
            val enabled: Boolean = section.getBoolean(Path.ENABLED.path)
            val soundName: String = checkNotNull(section.getString(Path.SOUND.path))
            val sound =
                Registry.SOUNDS.getOrThrow(NamespacedKey(NamespacedKey.MINECRAFT_NAMESPACE, soundName.uppercase()))

            val volume = section.getDouble(Path.VOLUME.path).toFloat()
            val pitch = section.getDouble(Path.PITCH.path).toFloat()

            return SoundData(enabled, sound, volume, pitch)
        }
    }

}
