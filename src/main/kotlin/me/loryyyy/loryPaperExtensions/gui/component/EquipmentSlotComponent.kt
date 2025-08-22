package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.gui.Gui
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class EquipmentSlotComponent(
    private var item: ItemStack?,
    override val state: MutableMap<String, Any>,
    private val onRightClick: (InventoryClickEvent, ItemStack?) -> Unit,
    private val onMove: (fromSlot: Int, toSlot: Int, ItemStack?) -> Unit,
    private val gui: Gui // riferimento alla gui che lo contiene
) : GuiComponent {

    override fun render() = item?: ItemStack(Material.AIR)

    override fun onClick(e: InventoryClickEvent): Boolean {
        val clickedSlot = e.slot

        if (e.click.isRightClick) {
            onRightClick(e, item)
            return true
        }

        if (e.click.isLeftClick) {
            val inv = e.inventory
            val cursor = e.cursor

            if (cursor.type != Material.AIR) {
                // metto l’item nello slot cliccato
                item = cursor.clone()
                inv.setItem(clickedSlot, item)
                // se hai preso da un altro slot → rimappa
                if (e.hotbarButton != -1) {
                    gui.moveComponent(e.hotbarButton, clickedSlot)
                }
                onMove(e.rawSlot, clickedSlot, item)
                return false
            } else {
                // raccogli l’item
                e.whoClicked.setItemOnCursor(item)
                item = null
                inv.setItem(clickedSlot, null)
                return false
            }
        }

        return true
    }
}