package me.loryyyy.betterArsenal.utils.paper

import me.loryyyy.loryPaperExtensions.item_actions.ItemAction
import me.loryyyy.loryPaperExtensions.item_actions.ItemActionsRegistry
import me.loryyyy.loryPaperExtensions.debug.Logger.logWarning
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

fun Player.createItem(
    material: Material,
    displayName: String,
    action: ItemAction? = null,
    lore: List<Component>? = null,
    index: Int = -1,
    amount: Int = 1
) = createItem(
    material = material,
    displayName = displayName,
    action = action,
    lore = lore,
    inventory = this.inventory,
    index = index,
    amount = amount
)

fun createItem(
    material: Material,
    displayName: String,
    action: ItemAction? = null,
    lore: List<Component>? = null,
    inventory: Inventory,
    index: Int = -1,
    amount: Int = 1
) {
    val item = ItemStack(material, amount)
    val meta = item.itemMeta

    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
    meta.displayName(Component.text(displayName))
    action?.let {
        meta.persistentDataContainer.set(ItemActionsRegistry.ITEM_TAG_KEY, PersistentDataType.STRING, action.key)
    }
    meta.lore(lore ?: emptyList())
    item.setItemMeta(meta)

    if (index == -1) {
        inventory.addItem(item)
    } else {
        inventory.setItem(index, item)
    }
}

fun getHeadOfPlayer(name: String): ItemStack {
    val playerHead = ItemStack(Material.PLAYER_HEAD)

    val meta = playerHead.itemMeta as SkullMeta?
    if (meta == null) {
        return playerHead
    }
    meta.owningPlayer = Bukkit.getOfflinePlayer(name)

    playerHead.setItemMeta(meta)

    return playerHead
}

fun loadWorld(worldName: String): World? {
    return try {
        // First check if the world is already loaded
        Bukkit.getWorld(worldName)?.let { return it }

        // If not loaded, try to load it using WorldCreator
        val worldCreator = WorldCreator.name(worldName)
        Bukkit.createWorld(worldCreator)
    } catch (e: Exception) {
        // Log the error and return null if loading fails
        logWarning("Failed to load world '$worldName': ${e.message}")
        null
    }
}

fun Location.toPrettyString(): String {
    return "(%.2f, %.2f, %.2f) in %s".format(x, y, z, world?.name ?: "Unknown World")
}

fun JavaPlugin.launchTask(
    delay: Long = 0L,
    period: Long,
    totalCycles: Int = -1,
    sync: Boolean = true,
    endBlock: () -> Unit = {},
    block: (BukkitRunnable) -> Unit
): BukkitTask {
    var runCount = 0

    val task = object : BukkitRunnable() {
        override fun run() {
            if (totalCycles != -1 && runCount >= totalCycles) {
                cancel()
                endBlock()
                return
            }
            runCount++
            block(this)
        }
    }

    return if (period <= 0L) {
        if (sync) {
            task.runTaskLater(this, delay)
        } else {
            task.runTaskLaterAsynchronously(this, delay)
        }
    } else {
        if (sync) {
            task.runTaskTimer(this, delay, period)
        } else {
            task.runTaskTimerAsynchronously(this, delay, period)
        }
    }
}
